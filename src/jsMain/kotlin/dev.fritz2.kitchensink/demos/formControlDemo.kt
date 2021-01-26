package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.Store
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.states
import dev.fritz2.dom.values
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

// extend ControlComponent in order to override or extend functions for controls
// and for setting up other renderers
class MyFormControlComponent : FormControlComponent() {

    //  custom implementation of radio group within custom form control
    fun myRadioGroup(
        styling: BasicParams.() -> Unit ={},
        store: Store<String>,
        baseClass: StyleClass? = null,
        id: String? = null,
        prefix: String = "myradiogroup",
        build: RadioGroupComponent<String>.() -> Unit
    ) {
        val returnStore = object : RootStore<String>("") {
            val syncHandlerSelect = handleAndEmit<String, String> { _, new ->
                if (new == "custom") ""
                else {
                    emit("")
                    new
                }
            }

            val selectedStore = storeOf("")

            val inputStore = object : RootStore<String>("") {

                val syncInput = handleAndEmit<String, String> { _, new ->
                    if (selectedStore.current == "custom") {
                        emit(new)
                    }
                    new
                }

            }

            init {
                selectedStore.syncBy(syncHandlerSelect)
                inputStore.syncInput handledBy update
                syncHandlerSelect handledBy inputStore.update
                this.data handledBy store.update
            }
        }

        control.set(Companion.ControlNames.radioGroup)
        {
            lineUp {
                items {
                    radioGroup(styling, returnStore.selectedStore, baseClass, id, prefix) {
                        build()
                        direction { row }
                        items ( items.values.map { it + "custom" })
                        //items { items.map { it + "custom" } }
                    }
                    inputField {
                        size { small }
                        disabled(returnStore.selectedStore.data.map { it != "custom" })
                        value(returnStore.inputStore.data)
                        placeholder("custom value")
                        events {
                            changes.values() handledBy returnStore.inputStore.syncInput
                        }

                    }
                }

            }
        }
    }

    // Define your own renderer
    class MyRadioRenderer(private val component: FormControlComponent) : ControlRenderer {
        override fun render(
            styling: BasicParams.() -> Unit,
            baseClass: StyleClass?,
            id: String?,
            prefix: String,
            renderContext: RenderContext,
            control: RenderContext.() -> Unit
        ) {
            renderContext.lineUp({
                verticalAlign { top }
                alignItems { start }
                styling()
            }, baseClass, id, prefix) {
                items {
                    (::p.styled {
                        alignItems { start }
                        textAlign { right }
                        minHeight { full }
                        height { full }
                    }){ component.label }

                    stackUp({
                        width { full }
                        alignItems { start }
                        borders {
                            left {
                                color { light }
                                width { fat }
                            }
                        }
                        paddings {
                            left {
                                normal
                            }
                        }
                    }) {
                        spacing { tiny }
                        items {
                            fieldset {
                                control(this)
                            }
                            component.renderHelperText(this)
                            component.renderErrorMessage(this)
                        }
                    }
                }
            }
        }
    }

    init {
        // Overrule default strategy for ``multiSelectCheckbox``
        // You could also add a new *control* function with a corresponding renderer of course
        renderStrategies[Companion.ControlNames.radioGroup] = MyRadioRenderer(this)
    }
}

fun RenderContext.myFormControl(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass? = null,
    id: String? = null,
    prefix: String = "formControl",
    build: MyFormControlComponent.() -> Unit = {}
) {
    val component = MyFormControlComponent().apply(build)
    component.render(styling, baseClass, id, prefix, this)
}


@ExperimentalCoroutinesApi
fun RenderContext.formControlDemo(): Div {
    return contentFrame {
        showcaseHeader("FormControl")
        paragraph {
            +"FormControls accept a "
            strong { +"single" }
            +" form element and take care of wrapping it with..."
            ul {
                li { +"a label" }
                li { +"an optional marker to indicate a required field" }
                li { +"an optional helper-text" }
                li { +"an error message and marker to indicate invalid input" }
            }
        }

        showcaseSection("Usage")
        paragraph {
            +"The following FormControl wraps an InputField. It marks the field as required, catches incorrect inputs "
            +" with a custom error message, and provides a helper-text:"
        }
        componentFrame {
            val solution = "fritz2"
            val framework = storeOf("")
            formControl {
                label { "Favorite web framework" }
                required(true)
                helperText { "Input the name of your favorite Kotlin based web framework." }
                errorMessage {
                    framework.data.map {
                        // any non-empty string will display as error message
                        if (it.isNotEmpty() && it.toLowerCase() != solution) {
                            "'$it' is completely wrong."
                        } else ""
                    }
                }
                // use the appropriate single element control with its specific API
                inputField(store = framework) {
                    placeholder("Your answer here")
                }
                // throws an exception: only one (and the first) control is accepted
                inputField {
                    placeholder("This control throws an exception because a form control may only contain one control.")
                }
            }
        }
        playground {
            source(
                """
                val solution = "fritz2"
                val framework = storeOf("")
                
                formControl {
                    label { "Favorite web framework" }
                    required ( true )
                    helperText { "Input the name of your favorite Kotlin based web framework." }
                    errorMessage {
                        framework.data.map {
                            // any non-empty string will display as error message
                            if (it.isNotEmpty() && it.toLowerCase() != solution) {
                                "'${'$'}it' is completely wrong."
                            } else ""
                        }
                    }
                    // embed the desired control with its form control specific API
                    inputField(store = framework) {
                        placeholder("Your answer here")
                    }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Supported Form Elements")
        paragraph {
            +"A FormControl can wrap different types of form elements."
            +" The following controls are currently supported out-of-the-box:"
            ul {
                li { +"InputField" }
                li { +"SelectField" }
                li { +"TextArea" }
                li { +"Switch" }
                li { +"Checkbox" }
                li { +"CheckboxGroup" }
                li { +"RadioGroup" }
            }
        }

        showcaseSubSection("FormControl for Switch")
        componentFrame {
            val favStore = storeOf(true)

            formControl {
                label { "Required input switch" }
                required(true)
                switch(store = favStore) {
                    /*checked ( favStore.data )
                    events {
                        changes.states() handledBy favStore.update
                    }*/
                }
                errorMessage {
                    favStore.data.map {
                        if (!it) {
                            "Please activate this switch."
                        } else ""
                    }
                }
            }
        }
        playground {
            source(
                """
            formControl {
                required(true)
                label { "Required input switch" }
                switch {
                    checked ( favStore.data )
                    events {
                        changes.states() handledBy favStore.update
                    }
                }
            }
            """.trimIndent()
            )
        }

        showcaseSubSection("FormControl for Single Checkbox")
        componentFrame {
            val favStore = storeOf(true)
            val labels = mapOf(
                true to "fritz2 is my favorite framework.",
                false to "fritz2 is my least favorite framework."
            )

            formControl {
                label { "How do you feel about fritz2?" }
                // embed a single checkbox using its specific API
                checkbox(store = favStore) {
                    label(favStore.data.map { labels[it]!! })
                    checked ( favStore.data )
                    events {
                        changes.states() handledBy favStore.update
                    }
                }
            }
        }
        playground {
            source(
                """
                val favStore = storeOf(true)
                val labels = mapOf(
                    true to "fritz2 is my favorite framework.",
                    false to "fritz2 is my least favorite framework."
                )
    
                formControl {
                label { "How do you feel about fritz2?" }
                // embed a single checkbox using its form control specific API
                checkbox {
                    label(favStore.data.map { labels[it]!! })
                    checked ( favStore.data )
                    events {
                        changes.states() handledBy favStore.update
                    }
                }
            }
            """.trimIndent()
            )
        }



        showcaseSubSection("FormControl for CheckboxGroup")

        val myItemList = "fritz2".toCharArray().map { it.toString() }
        val mySelectedItems = myItemList.take(2)
        val selectedItemsStore = storeOf(mySelectedItems)

        componentFrame {

            formControl {
                label { "A simple, labeled CheckboxGroup" }
                checkboxGroup(store = selectedItemsStore, items = myItemList) {
                    direction { row }
                }
                helperText { "The order of checking influences the selection." }
            }
        }

        storeContentBox {
            p {
                b { +"Selected: " }
                selectedItemsStore.data.render {
                    span {
                        +it.joinToString("")
                    }
                }
            }
        }

        playground {
            source(
                """
                val myItemList = "fritz2".toCharArray().map { it.toString() }
                val mySelectedItems = myItemList.take(2)
                val selectedItemsStore = storeOf(mySelectedItems)
                                    
                formControl {
                    label { "A simple, labeled CheckboxGroup:" }
                    checkboxGroup(store = selectedItemsStore, items = myItemList) {
                        direction { row }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("FormControl for TextArea")
        componentFrame {
            val textStore = storeOf("Please don't delete my text.")

            formControl {
                textArea {
                    value(textStore.data)
                    changes.values() handledBy textStore.update
                }
                required(true)
                label { "Type something in to make a FormControl very happy." }
                errorMessage {
                    textStore.data.map {
                        if (it.isEmpty()) {
                            "I can't believe you did that."
                        } else ""
                    }
                }
            }
        }
        playground {
            source(
                """
            formControl {
                textArea {
                    value(textStore.data)
                    changes.values() handledBy textStore.update
                }
                required(true)
                label { "Type something in to make a FormControl very happy." }
                errorMessage {
                    textStore.data.map {
                        if (it.isEmpty()) {
                            "I can't believe you did that."
                        } else ""
                    }
                }
            }
            
            """.trimIndent()
            )
        }

        showcaseSubSection("FormControl for SelectField")

        val selectList = "-fritz2".toCharArray().map { it.toString() }
        val selected = storeOf(selectList[0])

        componentFrame {

            formControl {
                label { "Single selection form control with validation" }
                selectField(store = selected) {
                    items(selectList)
                }
                required(true)
                errorMessage {
                    selected.data.map {
                        // any non-empty string will display as error message
                        if (it == "-") {
                            "Please select a character."
                        } else ""
                    }
                }
            }
        }

        storeContentBox {
            p {
                b { +"Selected: " }
                selected.data.render {
                    span {
                        +it
                    }
                }
            }
        }

        playground {
            source(
                """
                val selectList = "-fritz2".toCharArray().map { it.toString() }
                val selected = storeOf(selectList[0])
                
                formControl {
                    label { "Single selection form control with validation" }
                    selectField(store = selected) {
                        items(selectList)
                    }
                    required(true)
                    errorMessage {
                        selected.data.map {
                            if (it == "-") {
                                "Please select a character."
                            } else ""
                        }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Embed Custom Controls: RadioGroup example")
        paragraph {
            +"Write custom FormControls for your components. You can either write your own functions which"
            +" simply extend "
            c( "FormControlComponent")
            +", or you can override the default functions for the supported components. The following example"
            +" is created as an extension and uses Strings as items."

        }
        val selectedRadio = storeOf(selectList[2])
        componentFrame {
            myFormControl {
                label { "myFormControl wraps myRadioGroup" }
                myRadioGroup(store = selectedRadio) {
                    items(myItemList)
                    direction { column }
                }
            }
        }
        storeContentBox {
            p {
                b { +"Selected: " }
                selectedRadio.data.render {
                    span {
                        +it
                    }
                }
            }
        }

        playground {
            source(
                """
                /* Please note that all styling was omitted to shorten the source example. */
                
                // Extend ControlComponent in order to override or extend functions for controls
                // and for setting up other renderers.
                class MyFormControlComponent : FormControlComponent() {

                //  custom implementation of radio group within custom form control
                fun myRadioGroup(
                    styling: BasicParams.() -> Unit ={},
                    store: Store<String>,
                    baseClass: StyleClass? = null,
                    id: String? = null,
                    prefix: String = "myradiogroup",
                    build: RadioGroupComponent<String>.() -> Unit
                ) {
                        val returnStore = object : RootStore<String>("") {
                            val syncHandlerSelect = handleAndEmit<String, String> { _, new ->
                                if (new == "custom") ""
                                else {
                                    emit("")
                                    new
                                }
                            }

                            val selectedStore = storeOf("")

                            val inputStore = object : RootStore<String>("") {
                                val syncInput = handleAndEmit<String, String> { _, new ->
                                    if (selectedStore.current == "custom") {
                                        emit(new)
                                    }
                                    new
                                }
                            }

                            init {
                                selectedStore.syncBy(syncHandlerSelect)
                                inputStore.syncInput handledBy update
                                syncHandlerSelect handledBy inputStore.update
                                this.data handledBy store.update
                            }
                        }

                        control.set(Companion.ControlNames.radioGroup)
                        {
                            lineUp {
                                items {
                                    radioGroup(styling, returnStore.selectedStore, baseClass, id, prefix) {
                                        build()
                                        direction { row }
                                        items {
                                            items.map { it + "custom" }
                                        }
                                    }
                                    inputField {
                                        base {
                                            disabled(returnStore.selectedStore.data.map { it != "custom" })
                                            changes.values() handledBy returnStore.inputStore.syncInput
                                            value(returnStore.inputStore.data)
                                            placeholder("custom value")
                                        }
                                    }
                                }

                            }
                        }
                    }

                    // Define your own renderer for your RadioGroup
                    class MyRadioRenderer(private val component: FormControlComponent) : ControlRenderer {
                        override fun render(
                            styling: BasicParams.() -> Unit,
                            baseClass: StyleClass?,
                            id: String?,
                            prefix: String,
                            renderContext: RenderContext,
                            control: RenderContext.() -> Unit
                        ) {
                            renderContext.lineUp({
                                styling()
                            }, baseClass, id, prefix) {
                                items {
                                    p { +component.label }
                                    stackUp {
                                        items {
                                            fieldset { control(this) }
                                            component.renderHelperText(this)
                                            component.renderErrorMessage(this)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    init {
                        // Overrule default strategy for ``radioGroup``.
                        // You could also add a new control function with a corresponding renderer of course.
                        renderStrategies[Companion.ControlNames.radioGroup] = MyRadioRenderer(this)
                    }
                }

                fun RenderContext.myFormControl(
                    styling: BasicParams.() -> Unit = {},
                    baseClass: StyleClass? = null,
                    id: String? = null,
                    prefix: String = "formControl",
                    build: MyFormControlComponent.() -> Unit = {}
                ) {
                    val component = MyFormControlComponent().apply(build)
                    component.render(styling, baseClass, id, prefix, this)
                }
                
           
                // In your renderContext, call the custom FormControl.
                myFormControl {
                    label { "myFormControl wraps myRadioGroup"  }
                    radioGroup(store = selectedRadio) {
                        items(myItemList)
                        direction { column }
                    }
                }

            """.trimIndent()
            )
        }
    }
}