package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.formControl
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.states
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/*
*
* Just leave this out of the demo for now.
* Needs to be reworked after 0.8 release!
* (It does work and looks quite nice, but no time for documenting this in an enlightening way for the
* demo application!)
*

// extend ControlComponent in order to override or extend functions for controls
// and for setting up other renderers!
class MyFormControlComponent : FormControlComponent() {

    // simple convenience function as we cannot provide default parameters for overridden functions!
    fun mySingleSelectComponent(
        styling: BasicParams.() -> Unit = {},
        store: Store<String>,
        baseClass: StyleClass? = null,
        id: String? = null,
        prefix: String = Companion.ControlNames.checkboxGroup,
        build: RadioGroupComponent<String>.() -> Unit
    ) {
        return radioGroup(styling, store, baseClass, id, prefix, build)
    }

    // override default implementation of a radio group within a form control
    fun radioGroup(
        styling: BasicParams.() -> Unit,
        store: Store<String>,
        baseClass: StyleClass?,
        id: String?,
        prefix: String,
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
                        size { small }
                        base {
                            disabled(returnStore.selectedStore.data.map { it != "custom" })
                            changes.values() handledBy returnStore.inputStore.syncInput
                            value(returnStore.inputStore.data)
                            placeholder("custom choice")
                        }
                    }
                }

            }
        }
    }

    // Define your own renderer
    class MySpecialRenderer(private val component: FormControlComponent) : ControlRenderer {
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
                    }){ +component.label }

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
        renderStrategies[Companion.ControlNames.radioGroup] = MySpecialRenderer(this)
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

 */

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
                label { "Please input the name of your favorite Kotlin based web framework." }
                required { true }
                helperText { "You shouldn't need a hint." }
                errorMessage {
                    framework.data.map {
                        // any non-empty string will display as error message
                        if (it.isNotEmpty() && it.toLowerCase() != solution) {
                            "Sorry, but '$it' is completely wrong."
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
                    label { "Please input the name of your favorite Kotlin based web framework." }
                    required { true }
                    helperText { "You shouldn't need a hint." }
                    errorMessage {
                        framework.data.map {
                            // any non-empty string will display as error message
                            if (it.isNotEmpty() && it.toLowerCase() != solution) {
                                "Sorry, but '"${'$'}"it' is completely wrong."
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
                li { +"Checkbox" }
                li { +"CheckboxGroup" }
                li { +"RadioGroup" }
            }
        }

        showcaseSubSection("FormControl for a single Checkbox")
        componentFrame {
            val favStore = storeOf(true)
            val labels = mapOf(
                true to "fritz2 is my favourite framework.",
                false to "fritz2 is my least favourite framework."
            )

            formControl {
                label { "How do you feel about fritz2?" }
                // embed a single checkbox using its specific API
                checkbox {
                    label(favStore.data.map { labels[it]!! })
                    checked { favStore.data }
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
                    true to "fritz2 is my favourite framework.",
                    false to "fritz2 is my least favourite framework."
                )
    
                formControl {
                label { "How do you feel about fritz2?" }
                // embed a single checkbox using its form control specific API
                checkbox {
                    label(favStore.data.map { labels[it]!! })
                    checked { favStore.data }
                    events {
                        changes.states() handledBy favStore.update
                    }
                }
            }
            """.trimIndent()
            )
        }
        showcaseSubSection("FormControl for a CheckboxGroup")

        val myItemList = "fritz2".toCharArray().map { it.toString() }
        val mySelectedItems = myItemList.take(2)
        val selectedItemsStore = storeOf(mySelectedItems)

        componentFrame {

            formControl {
                label { "A simple, labeled CheckboxGroup" }
                checkboxGroup(store = selectedItemsStore) {
                    items { flowOf(myItemList) }
                    direction { row }
                }
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
                    checkboxGroup(store = selectedItemsStore) {
                        items { flowOf(myItemList) }
                        direction { row }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Embed Custom Controls")

        // todo should this todo be visible in the demo?
        warningBox {
            p {
                strong { +"Todo:" }
                +" Add an example of a custom control and its integration into the FormControl including a custom"
                +" surrounding HTML structure."
            }
        }

        /*
        *
        * Needs to be reactivated after 0.8 release.
        * See top of this file for further information!
        *

        // use your own formControl! Pay attention to the derived component receiver.
        showcaseSection("Custom FormControl")
        paragraph {
            +"""This control has overridden the control function to implement a special control. 
                    |It is combined with a hand made renderer for the surrounding custom structure.""".trimMargin()
        }

        val customValueSelected = storeOf("some")
        componentFrame {
            myFormControl {
                label { "Label next to the control just to be different" }
                helperText { "Helper text below control" }
                mySingleSelectComponent(store = customValueSelected) {
                    items((listOf("some", "predefined", "options")))
                }
            }
        }
        (::div.styled {
            background {
                color { light }
            }
            margins {
                top { "1.25rem" }
            }
            paddings {
                left { "0.5rem" }
                right { "0.5rem" }
            }
            radius { "5%" }

        }) {
            h4 { +"Selected:" }
            customValueSelected.data.render { p { +it } }
        }
        playground {
            source(
                """
                componentFrame {
                    myFormControl {
                        label { "Label next to the control just to be different" }
                        helperText { "Helper text below control" }
                        mySingleSelectComponent(store = customValueSelected) {
                            items((listOf("some", "predefined", "options")))
                        }
                    }
                }
                (::div.styled {
                    background {
                        color { light }
                    }
                    margins {
                        top { "1.25rem" }
                    }
                    paddings {
                        left { "0.5rem" }
                        right { "0.5rem" }
                    }
                    radius { "5%" }
        
                }) {
                    h4 { +"Selected:" }
                    customValueSelected.data.render { p { +it } }
                }
            """.trimIndent()
            )
        }

         */
    }
}