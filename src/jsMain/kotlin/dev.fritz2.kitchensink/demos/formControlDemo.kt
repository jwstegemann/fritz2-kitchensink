package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.SimpleHandler
import dev.fritz2.binding.Store
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.validation.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.formControl.*
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.p
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

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
                li { +"an optional helper-text" }
                li { +"some validation messages and marker to indicate invalid input." }
            }
        }
        paragraph {
            +"Like the controls they wrap, FormControls can handle events automatically. Additionally, they"
            +" process field validation without additional programming effort."
        }

        showcaseSection("Complete Form Example")
        paragraph {
            +"Here's a comprehensible but complete FormControl example"
            +" featuring fritz2 core concepts like data classes and lenses, "
            +" stores with substores, and field validation."
        }

        val accountStore = object : RootStore<Account>(
            Account("", "", emptyList(), false)
        ), WithValidator<Account, AccountCreationPhase> {
            override val validator = AccountValidator

            init {
                validate(AccountCreationPhase.Input)
            }

            val register = handle<SimpleHandler<Unit>> { model, handler ->
                if (validator.isValid(model, AccountCreationPhase.Registration)) {
                    handler(Unit)
                }
                model
            }
        }

        val nameStore = accountStore.sub(L.Account.username)
        val passphraseStore = accountStore.sub(L.Account.passphrase)
        val interestStore = accountStore.sub(L.Account.interests)
        val confirmationStore = accountStore.sub(L.Account.confirmation)

        val registerSuccessDialog = modal { close ->
            size { small }
            hasCloseButton(false)
            content {
                flexBox({
                    justifyContent { spaceEvenly }
                    alignItems { center }
                    direction { column }
                    height { "100" }
                }) {
                    p { +"Registration successful!" }
                    clickButton { text("Continue") } handledBy close
                }
            }
        }

        componentFrame {
            h3 { +"Create your user account" }
            br {}
            stackUp {
                spacing { large }
                items {
                    formControl {
                        label("Username")
                        inputField(value = nameStore) {
                            placeholder("Choose a username")
                        }
                    }
                    formControl {
                        label("Passphrase")
                        helperText("Remember: the longer, the better!")
                        inputField(value = passphraseStore) {
                            placeholder("Enter a secure passphrase")
                            type("password")
                        }
                    }
                    formControl {
                        label("Choose up to three interests:")

                        checkboxGroup(
                            items = listOf("Kotlin", "fritz2", "Html", "CSS", "Design", "Open Source"),
                            values = interestStore
                        ) {
                            orientation { vertical }
                        }
                    }
                    formControl {
                        switch(value = confirmationStore) {
                            label("I accept the terms of the MIT license.")
                        }
                    }
                    lineUp({
                        justifyContent { flexEnd }
                        width { full }
                    }) {
                        items {
                            clickButton {
                                text("Register")
                            }.map { registerSuccessDialog } handledBy accountStore.register
                        }
                    }
                }
            }
        }

        paragraph {
            +"Depending on your input, the validation messages change type and appearance - some are only warnings or"
            +" information and do not prevent the completion of the registration process. Each required field shows a"
            +" success icon if the input is valid, even if it has warning- or information type messages."
            +" The red error messages indicate hard errors which reject registration when clicking the button."
        }

        showcaseSubSection("The Model")
        paragraph {
            +"The example above uses a data class in order to model an account object."
            +" We annotate it with "
            c("@Lenses")
            +" to easily derive substores from the data later."
        }
        highlight {
            source(
                """
                @Lenses
                data class Account(
                    val username: String,
                    val passphrase: String,
                    val interests: List<String>,
                    val confirmation: Boolean
                )
                """
            )
        }

        showcaseSubSection("Initial Store")
        paragraph {
            +"Next, a "
            c("RootStore")
            +" is created to hold the account we want to create from the user's input."
            +" We then derive substores for each account property which will be used for the input fields."
        }
        highlight {
            source(
                """
                val accountStore = object : RootStore<Account>(
                    Account("", "", emptyList(), false)
                )

                val nameStore = accountStore.sub(L.Account.username)
                val passphraseStore = accountStore.sub(L.Account.passphrase)
                val interestStore = accountStore.sub(L.Account.interests)
                val confirmationStore = accountStore.sub(L.Account.confirmation)
                """
            )
        }

        showcaseSubSection("Assembling The Form")
        paragraph {
            +"For each FormControl for this account example, do the following: "
            ul {
                li { +"declare a label for the field" }
                li { +"declare the field itself, using one of the substores" }
                li { +"make additional customizations (see below for more details)" }
            }
        }
        paragraph {
            +"The syntax for declaring a control inside a form control is exactly the same as for standalone controls."
            +" Preferably pass a store to it in order to benefit from the automatic "
            +" validation and messages. Of course you can customize the control in every way the underlying component "
            +" supports, like changing styling, orientation, input type, and "
            +" other aspects."
        }
        highlight {
            source(
                """
                // surrounding layout (``stackUp``) omitted for better readability 
                formControl {
                    label("Username")
                    inputField(value = nameStore) {
                        placeholder("Choose a username")
                    }
                }
                formControl {
                    label("Passphrase")
                    helperText("Remember: the longer, the better!")
                    inputField(value = passphraseStore) {
                        placeholder("Enter a secure passphrase")
                        type("password")
                    }
                }
                formControl {
                    label("Choose up to three interests:")

                    checkboxGroup(
                        items = listOf(
                            "Kotlin", "fritz2", "Html", 
                            "CSS", "Design", "Open Source"
                        ),
                        values = interestStore
                    ) {
                        orientation { vertical }
                    }
                }
                formControl {
                    switch(value = confirmationStore) {
                        label("I accept the terms of the MIT license.")
                    }
                }
                """
            )
        }

        showcaseSubSection("Validation")

        coloredBox(Theme().colors.info) {
            +"The validation code does not rely on anything UI specific so it's"
            +" perfect for unit testing."
        }

        paragraph {
            +"fritz2 aims to implement the separation of concern principle,"
            +" so your validation must be placed inside the "
            c("commonMain")
            +" folder of your project."
        }
        paragraph {
            +"To use validation for your store, implement the "
            c("ComponentValidator<D, T>")
            +" interface by using the "
            c("validate")
            +" method as starting point."
            +"The generic types refer to these aspects:"
            ul {
                li {
                    c("D")
                    +": the model to be validated"
                }
                li {
                    c("T")
                    +": meta data for additional information (Unit if not needed)"
                }
            }
        }
        paragraph {
            +"The whole model is validated at once. This validation process generates a list of "
            c("ComponentValidationMessage")
            +" instances, each of which includes the id of the field it refers to (the substore's id)."
            +" The formControl component filters the list for messages with its own control's id and displays them."
        }

        highlight {
            source(
                """
                // Simplified first implementation
                object AccountValidator : ComponentValidator<Account, Unit>() {
                    override fun validate(data: Account, metadata: Unit): List<ComponentValidationMessage> {
                        val messages = mutableListOf<ComponentValidationMessage>()
                
                        // create an inspector for "ad hoc" model inspection
                        val inspector = inspect(data)
                        val username = inspector.sub(L.Account.username)
                        val passphrase = inspector.sub(L.Account.passphrase)
                
                        if (username.data.isBlank()) {
                            // bind the id of the lens to the message for filtering in the form control
                            messages.add(errorMessage(username.id, "Please choose a username."))
                        }
                        if (passphrase.data.isBlank()) {
                            messages.add(errorMessage(passphrase.id, "Please specify a passphrase."))
                        } else if (passphrase.data.length < 16) {
                            messages.add(warningMessage(passphrase.id, "We recommend a passphrase with at least 16 characters."))
                        }
                
                        // return all the generated messages - can be empty of course
                        return messages
                    }
                }                    
                """
            )
        }
        showcaseSubSection("Using Metadata")
        paragraph {
            +"If more context information is needed to process the validation, or if there are multiple validation modes, "
            +" the addtional metadata type T of "
            c("ComponentValidator<D, T>")
            +" comes in handy. In the example above, metadata wasn't used, therefore it was "
            c("Unit")
            +". The following example uses metadata to add validation while the user is typing the input."
        }
        paragraph {
            +"Here, the registration button still validates the input before further processing"
            +" (typically sending the data to a backend service). But when trying validate the content of the fields"
            +" immediately on change, this method would mark all blank fields as errors before the user"
            +" even has a change to provide correct input."
        }
        paragraph {
            +"In order to solve this problem, the following enum type will be used as metadata"
            +" for our validator. It defines two phases: "
        }
        highlight {
            source(
                """
                enum class AccountCreationPhase {
                    Input,
                    Registration
                }                    
                """
            )
        }
        paragraph {
            +"For immediate validation, "
            c("Input")
            +" is passed as metadata value. For the final validation on button click, "
            c("Registration")
            +" is passed. This allows the validator to reflect the current phase and process blank "
            +" data after the button click only. To keep it short, the following example shows only an excerpt of "
            +"the real source code."
        }

        highlight {
            source(
                """
                object AccountValidator : ComponentValidator<Account, AccountCreationPhase>() {
                    override fun validate(data: Account, metadata: AccountCreationPhase): List<ComponentValidationMessage> {
                        val inspector = inspect(data)
                        return validateUsername(inspector, metadata) +
                                validatePassphrase(inspector, metadata) +
                                validateInterests(inspector) +
                                validateConfirmation(inspector, metadata)
                    }
                
                    private fun addSuccessMessage(
                        messages: MutableList<ComponentValidationMessage>,
                        phase: AccountCreationPhase,
                        id: String
                    ) {
                        if (phase == AccountCreationPhase.Input && !messages.any { it.isError() }) {
                            messages.add(successMessage(id, ""))
                        }
                    }
                
                    private fun validateUsername(
                        inspector: RootInspector<Account>,
                        phase: AccountCreationPhase
                    ): List<ComponentValidationMessage> {
                        val messages = mutableListOf<ComponentValidationMessage>()
                        val username = inspector.sub(L.Account.username)
                        if (username.data.isBlank() && phase == AccountCreationPhase.Registration) {
                            messages.add(errorMessage(username.id, "Please choose a username."))
                        } else if (username.data.isNotBlank()) {
                            if (username.data.contains(':')) {
                                messages.add(errorMessage(username.id, "Colon is not allowed in username."))
                            }
                            if (username.data.length < 3) {
                                messages.add(warningMessage(username.id, "We recommend a username with at least 3 characters."))
                            }
                            addSuccessMessage(messages, phase, username.id)
                        }
                        return messages
                    }
                    
                    // ``validatePassphrase``, ``validateInterests`` and ``validateConfirmation`` omitted!
                }
                """
            )
        }
        paragraph {
            +"To study the full code, hop over to "
            externalLink(
                "github",
                "https://github.com/jwstegemann/fritz2-kitchensink/blob/ec44eec27cbdaf98a24dac87bcbe05af2a449a1f/src/commonMain/kotlin/dev/fritz2/kitchensink/formControlModel.kt#L21-L121"
            )
            +"."
        }


        showcaseSubSection("Gluing it all together")

        paragraph {
            +"We have already connected our data instance via the store and its substores to the form itself, but"
            +" there is no connection between the validation code and the form yet."
        }
        paragraph {
            +"In order to validate any changes to our model, we can augment the store by"
            +" applying the interface named "
            c("WithValidator<D, T>")
            +", which adds validation to the store."
        }
        highlight {
            source(
                """
                val accountStore = object : RootStore<Account>(
                    Account("", "", emptyList(), false)
                ), 
                // model type and metadata type must match those for ``ComponentValidator<D, T>`` 
                WithValidator<Account, AccountCreationPhase> {
                    // set your validator (or inject) 
                    override val validator = AccountValidator
        
                    // sync every data change with a call to the built-in ``validate``-method
                    init {
                        // pass the appropriate metadata
                        validate(AccountCreationPhase.Input)
                    }
        
                    // to validate after a specific event, create a custom handler:
                    // - pass the appropriate metadata (see section about validation)
                    // - execute your action, for example a SimpleHandler<Unit> to continue
                    //   processing if validation is successful
                    val register = handle<SimpleHandler<Unit>> { model, handler ->
                        if (validator.isValid(model, AccountCreationPhase.Registration)) {
                            handler(Unit)
                        }
                        model
                    }
                }
                """.trimIndent()
            )
        }
        paragraph {
            +"Lastly, let's connect the registration button to the special handler:"
        }
        highlight {
            source(
                """
                // styling omitted for better readability
                val registerSuccessDialog = modal { close ->
                    hasCloseButton(false)
                    content {
                        flexBox {
                            p { +"You have been successfully registered!" }
                            clickButton { text("fritz:it up") } handledBy close
                        }
                    }
                }
                     
                clickButton {
                    text("Register")
                    // pass the handler to the validating handler
                }.map { registerSuccessDialog } handledBy accountStore.register
                """
            )
        }

        paragraph {
            +"All the code fragments we just went through make up the code of the first example on this page."
            +" Some styling and layouting code was omitted in the source examples to keep it short, but"
            +" the example is functional and otherwise complete."
        }
        paragraph {
            +"This example focuses on the combination of model, store, and validation with the"
            +" formControl component, rather than focusing on the component itself. We wanted"
            +" to showcase this complex component with a worthy example within a common applicable"
            +" context to enable our users to apply those patterns "
            +" by themselves."
        }
        paragraph {
            +"But the following sections will focus on aspects we have not shown so far, like "
            +" styling and customization techniques in a much simpler context."
        }

        showcaseSection("Usage")
        paragraph {
            +"The basic usage of a formControl is simple - just define your control inside its context and"
            +" add a label and, optionally, a helper text. Caution - a formControl can only wrap "
            strong { +"one" }
            +" control. If you want to embed more than one control, you have to customize this component as shown "
            +" in the customization section at the end of this guide."
        }
        componentFrame {
            formControl {
                label("Username")
                helperText("What should we call you?")
                // use the appropriate single element control with its specific API
                inputField(value = nameStore) {
                    placeholder("Fritz")
                }
                // throws an exception: only one (the first) control is accepted
                inputField {
                    placeholder(
                        "This input throws an exception because a form control may only contain one control."
                    )
                }
            }
        }

        highlight {
            source(
                """
                formControl {
                    label("Username")
                    helperText("What should we call you?")
                    // use the appropriate single element control with its specific API
                    inputField(value = nameStore) {
                        placeholder("Fritz")
                    }
                    // throws an exception: only one (the first) control is accepted
                    inputField {
                        placeholder(
                            "This input throws an exception because a form control may only contain one control."
                        )
                    }
                }                    
                """
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
                li { +"Slider" }
                li { +"CheckboxGroup" }
                li { +"RadioGroup" }
            }
        }

        showcaseSection("Styling")
        paragraph {
            +"You can change the appearance of a formControl in many different ways."
            +" The example below changes the following aspects:"
            ul {
                li { +"size" }
                li { +"label text style" }
                li { +"helper text style" }
            }
        }
        componentFrame {
            stackUp {
                spacing { large }
                items {
                    sequenceOf(
                        Pair(FormControlComponent.FormSizeContext.small, "Small"),
                        Pair(FormControlComponent.FormSizeContext.normal, "Normal (default)"),
                        Pair(FormControlComponent.FormSizeContext.large, "Large")
                    ).forEach {
                        formControl {
                            size { it.first }
                            label("${it.second} passphrase size")
                            labelStyle {
                                // Always apply the default first and only modify remains
                                Theme().formControl.label()
                                color { info.main }
                            }
                            helperText("Remember: the longer, the better!")
                            helperTextStyle {
                                Theme().formControl.helperText()
                                fontStyle { italic }
                                color { secondary.main }
                            }
                            inputField(value = passphraseStore) {
                                placeholder("Enter a secure passphrase")
                                type("password")
                            }
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                formControl {
                    size { small }
                    label("Small passphrase size")
                    labelStyle {
                        // It is good advice to apply the default first and only modify remains
                        Theme().formControl.label()
                        color { info.main }
                    }
                    helperText("Remember: the longer, the better!")
                    helperTextStyle {
                        Theme().formControl.helperText()
                        fontStyle { italic }
                        color { secondary.main }
                    }
                    inputField(value = passphraseStore) {
                        placeholder("Enter a secure passphrase")
                        type("password")
                    }
                }             
                
                // The following snippets only show changes for better readability
                       
                formControl {
                    label("Normal (default) passphrase size")
                    // ...
                }
                                         
                formControl {
                    size { large } 
                    label("Large passphrase size")
                    // ... 
                }                                                                  
                """
            )
        }

        showcaseSection("Custom Validation Messages")
        paragraph {
            +"Although it is recommended to use the validation approach with a store as shown in the first example,"
            +" formControl allows you to define validation messages ad hoc as well."
        }
        paragraph {
            +" The following control defines a single validation message using the function "
            c("validationMessage")
            +". It then applies some styling to the"
            +" message rendering: an Alert component is used to display a large, bright message. It is set up for"
            +" different message severities which change color and icon of the Alert. Of course, all options of the"
            +" Alert component like size, stacking, etc, could be used here as well."
        }
        componentFrame {
            formControl {
                label("Username")
                inputField(value = nameStore)
                // create one single validation message
                validationMessage {
                    nameStore.data.map {
                        if (it.length < 4) {
                            errorMessage(nameStore.id, "The username must be at least 4 characters long.")
                        } else null
                    }
                }

                validationMessageRendering { message ->
                    alert {
                        severity {
                            when (message.severity) {
                                Severity.Info -> info
                                Severity.Success -> success
                                Severity.Warning -> warning
                                Severity.Error -> error
                            }
                        }
                        variant { solid }
                        size { large }
                        content(message.message)
                    }
                }
            }
        }
        highlight {
            source(
                """
                formControl {
                    label("Username")
                    inputField(value = nameStore)
                    // create one single validation message
                    validationMessage {
                        nameStore.data.map {
                            if (it.length < 4) {
                                errorMessage(nameStore.id, "The username must be at least 4 characters long.")
                            } else null
                        }
                    }
                    // Change the rendering of your message: use the alert component
                    validationMessageRendering { message ->
                        alert {
                            severity {
                                when (message.severity) {
                                    Severity.Info -> info
                                    Severity.Success -> success
                                    Severity.Warning -> warning
                                    Severity.Error -> error
                                }
                            }
                            variant { solid }
                            size { large }
                            content(message.message)
                        }
                    }
                }
                """
            )
        }
        paragraph {
            +"If you need more than one validation message, use this function to add multiple messages: "
            c("validationMessages")
            +". The changes made to the rendering in the above example work the same in both cases."
        }
        componentFrame {
            formControl {
                label("Username")
                inputField(value = nameStore)
                validationMessages {
                    nameStore.data.map {
                        val messages = mutableListOf<ComponentValidationMessage>()
                        if (it.length < 4) {
                            messages.add(
                                errorMessage(nameStore.id, "The username must be at least 4 characters long.")
                            )
                        } else if (it.length > 16) {
                            messages.add(
                                warningMessage(nameStore.id, "Isn't more than 16 characters a little long?")
                            )
                        }
                        if (it.toCharArray().toHashSet().size == 1) {
                            messages.add(
                                warningMessage(
                                    nameStore.id,
                                    "Using only one char in your name is not very expressive."
                                )
                            )
                        }
                        if (it.contains("fritz2")) {
                            messages.add(infoMessage(nameStore.id, "What a great username!"))
                        }
                        messages
                    }
                }
                validationMessageRendering { message ->
                    alert({
                        margins { vertical { tiny } }
                    }) {
                        severity {
                            when (message.severity) {
                                Severity.Info -> info
                                Severity.Success -> success
                                Severity.Warning -> warning
                                Severity.Error -> error
                            }
                        }
                        variant { leftAccent }
                        size { small }
                        stacking { separated }
                        content(message.message)
                    }
                }
            }
        }
        highlight {
            source(
                """
                formControl {
                    label("Username")
                    inputField(value = nameStore) {
                    }
                    // create an arbitrary amount of messages
                    validationMessages {
                        nameStore.data.map {
                            val messages = mutableListOf<ComponentValidationMessage>()
                            if (it.length < 4) {
                                messages.add(
                                    errorMessage(nameStore.id, "The username must be at least 4 characters long.")
                                )
                            } else if (it.length > 16) {
                                messages.add(
                                    warningMessage(nameStore.id, "Isn't 16 characters a little long?")
                                )
                            }
                            if (it.toCharArray().toHashSet().size == 1) {
                                messages.add(
                                    warningMessage(
                                        nameStore.id,
                                        "Using only one char in your name is not very expressive."
                                    )
                                )
                            }
                            if (it.contains("fritz2")) {
                                messages.add(infoMessage(nameStore.id, "What a great username!"))
                            }
                            messages
                        }
                    } 
                    // Change rendering: Use an alert component for validation message
                    validationMessageRendering { message ->
                        alert({
                                margins { vertical { tiny } }
                            }) {
                            severity {
                                when (message.severity) {
                                    Severity.Info -> info
                                    Severity.Success -> success
                                    Severity.Warning -> warning
                                    Severity.Error -> error
                                }
                            }
                            variant { leftAccent }
                            size { small }
                            stacking { separated }
                            content(message.message)
                        }
                    }
                }
                """
            )
        }

        showcaseSection("Deep Customization")

        /*
         * Complex Customization Example
         *
         * Extend ControlComponent in order to override or extend functions for controls and for setting up
         * other renderers.
         *
         */
        class ExtendedFormControlComponent : FormControlComponent() {

            inner class Selection(val value: String, val mode: String)

            private fun createStores(clientStore: Store<String>) =
                object : RootStore<Selection>(Selection(clientStore.current, "inputDisabled")) {
                    val self = this

                    private val inputActivated = "inputActivated"
                    private val inputActive = "inputActive"
                    val inputDisabled = "inputDisabled"
                    val custom = "custom:"

                    val selectedStore = object : RootStore<String>(current.value) {
                        override val update = handle<String> { old, value ->
                            if (old == custom) {
                                inputStore.reset(Unit)
                            }
                            self.select(Selection(value, if (value == custom) inputActivated else inputDisabled))
                            value
                        }
                    }

                    val inputStore = object : RootStore<String>("") {
                        override val update = handle<String> { _, value ->
                            self.select(Selection(value, inputActive))
                            value
                        }

                        val reset: SimpleHandler<Unit> = handle { _ -> "" }
                    }

                    val select = handleAndEmit<Selection, String> { old, new ->
                        if (new.mode == inputActivated) {
                            emit(old.value)
                        } else {
                            emit(new.value)
                        }
                        new
                    }

                    init {
                        select handledBy clientStore.update
                    }
                }

            //  custom implementation of a radio group variant within custom form control
            fun radioGroupWithInput(
                styling: BasicParams.() -> Unit = {},
                items: List<String>,
                value: Store<String>,
                baseClass: StyleClass = StyleClass.None,
                id: String? = null,
                prefix: String = "radioGroupWithInput",
                build: RadioGroupComponent<String>.() -> Unit
            ) {
                val innerStore = createStores(value)
                val validationMessagesBuilder = ValidationResult.builderOf(this, value)
                registerControl("radioGroupWithInput", {
                    radioGroup(
                        styling,
                        items = items + innerStore.custom,
                        innerStore.selectedStore,
                        baseClass,
                        id,
                        prefix
                    ) {
                        size { this@ExtendedFormControlComponent.sizeBuilder(this) }
                        severity(validationMessagesBuilder().hasSeverity)
                        build()
                        orientation { vertical }
                    }
                    inputField({
                        margins { top { tiny } }
                    }, value = innerStore.inputStore) {
                        size { this@ExtendedFormControlComponent.sizeBuilder(this) }
                        severity(validationMessagesBuilder().hasSeverity)
                        disabled(innerStore.data.map { it.mode == innerStore.inputDisabled })
                        value(innerStore.inputStore.data)
                        placeholder("custom value")
                    }
                }, {
                    this@ExtendedFormControlComponent.validationMessagesBuilder = validationMessagesBuilder
                })
            }

            // Define your own renderer
            inner class VerticalRenderer(private val component: FormControlComponent) : ControlRenderer {
                override fun render(
                    styling: BoxParams.() -> Unit,
                    baseClass: StyleClass,
                    id: String?,
                    prefix: String,
                    renderContext: RenderContext,
                    control: RenderContext.() -> Unit
                ) {
                    renderContext.stackUp({
                        alignItems { start }
                        component.ownSize()()
                        styling(this as BoxParams)
                    }, baseClass, id, prefix) {
                        spacing { tiny }
                        items {
                            p({
                                textAlign { right }
                                borders {
                                    bottom {
                                        color { gray200 }
                                        width { fat }
                                    }
                                }
                            }) { +component.label.value }
                            component.renderHelperText(this)
                            fieldset {
                                control(this)
                            }
                            component.renderValidationMessages(this)
                        }
                    }
                }
            }

            init {
                // apply the new renderer to the new form
                registerRenderStrategy("radioGroupWithInput", VerticalRenderer(this))
            }
        }

        fun RenderContext.extendedFormControl(
            styling: BasicParams.() -> Unit = {},
            baseClass: StyleClass = StyleClass.None,
            id: String? = null,
            prefix: String = "extendedFormControl",
            build: ExtendedFormControlComponent.() -> Unit = {}
        ) {
            ExtendedFormControlComponent().apply(build).render(this, styling, baseClass, id, prefix)
        }

        val favoriteFrameworks = listOf("fritz2", "Ktor", "EXPOSED", "Spring", "patternfly-fritz2")

        paragraph {
            +"You can create custom formControls for your components. You can either write your own functions which"
            +" simply extend "
            c("FormControlComponent")
            +", or you can override the default functions for the supported components."
        }
        paragraph {
            +"If modifying the control itself is not sufficient, you can even implement your own renderer,"
            +" which means adopting the entire process and structure of the rendered formControl."
        }
        paragraph {
            +"But remember: "
        }
        coloredBox(Theme().colors.warning) {
            +"With great power comes great responsibility."
        }
        paragraph {
            +"In order to conserve the functionality, you must process all properties appropriately. It's very helpful"
            +" to study the sources of the two built-in renderers."
        }
        paragraph {
            +"The following example adds a wrapping function and also a custom renderer for a complete picture."
        }
        paragraph {
            +"In this use case, the user can choose between some predefined values, but can alternatively"
            +" pass a custom value. This can be realized by combining a "
            c("radioGroup")
            +" with an "
            c("inputField")
            +"."
            +" Both standalone forms must be merged into one control. Additionally, the rendering of this control will be adjusted"
            +" to show the helper text right below the label and to change some other minor aspects."
        }
        paragraph {
            +"Have a look at the result and the usage of the newly created "
            c("extendedFormControl")
            +" component function and its new "
            c("radioGroupWithInput")
            +" control:"
        }

        val selectedFramework = storeOf(favoriteFrameworks.first())
        componentFrame {
            extendedFormControl {
                label("Select one framework")
                helperText("Note: Framework name cannot be empty.")
                radioGroupWithInput(items = favoriteFrameworks, value = selectedFramework) {
                }
                validationMessage {
                    selectedFramework.data.map {
                        if (it.isBlank()) {
                            errorMessage(selectedFramework.id, "Please enter a name for your custom selection.")
                        } else null
                    }
                }
            }
        }
        storeContentBox("Selected") {
            selectedFramework.data.render {
                span { +it }
            }
        }

        highlight {
            source(
                """
                val favoriteFrameworks = listOf("fritz2", "Ktor", "EXPOSED", "Spring", "patternfly-fritz2")
                val selectedFramework = storeOf(favoriteFrameworks.first())

                // use the new factory method to have access to the new wrapped control
                extendedFormControl {
                    label("Select one framework")
                    helperText("Note: Framework name cannot be empty.")
                    radioGroupWithInput(items = favoriteFrameworks, value = selectedFramework) {
                    }
                    validationMessage {
                        selectedFramework.data.map {
                            if (it.isBlank()) {
                                errorMessage(selectedFramework.id, "Please enter a name for your custom selection.")
                            } else null
                        }
                    }
                }                  
                """
            )
        }

        paragraph {
            +"Let's walk through the design process step by step."
        }

        showcaseSubSection("Custom Component Setup")
        paragraph {
            +"The first thing you need to do is derive from the "
            c("FormControlComponent")
            +" class in order to extend its functionality and to mimic the original factory function."
        }
        highlight {
            source(
                """
                class ExtendedFormControlComponent : FormControlComponent() {

                    // more code will be added here 
                    
                    init {
                        // some setup will happen here
                    }
                }
                
                // new factory function for the new component
                fun RenderContext.extendedFormControl(
                    styling: BasicParams.() -> Unit = {},
                    baseClass: StyleClass = StyleClass.None,
                    id: String? = null,
                    prefix: String = "extendedFormControl",
                    build: ExtendedFormControlComponent.() -> Unit = {}
                ) {
                    val component = ExtendedFormControlComponent().apply(build)
                    component.render(styling, baseClass, id, prefix, this)
                }                
                """
            )
        }

        showcaseSubSection("Internal Store Concept")
        paragraph {
            +"In order to handle the user input correctly, some internal stores are needed:"
            ul {
                li {
                    +"First is the main store which is responsible for emitting the new values to the external"
                    +" client side store."
                }
                li {
                    +"Another store is dedicated to managing the "
                    c("radioGroup")
                    +" selection updates. It will propagate its state to the main store."
                }
                li {
                    +"A final store is created for the "
                    c("inputField")
                    +" component. It will propagate its changes to the main store as well."
                }
            }
        }
        paragraph {
            +"When combining two input components, it must be decided which component holds the current value to emit to the client."
            +"One solution is to tie some state"
            +" information to the value in the internal stores. We used "
            c("String")
            +" in this example, which is sufficient for this use case."
        }
        paragraph {
            +"Three different states must be stored:"
            ul {
                li { +"inputField is not active, so use radioGroup" }
                li {
                    +"inputField was just now activated by selecting the "
                    strong { +"custom" }
                    +" option"
                }
                li { +"inputField is active, so don't use radioGroup" }
            }
        }
        paragraph {
            +"Armed with this state information, we can.."
            ul {
                li { +"reset the inputField if the radioGroup is active" }
                li {
                    +"avoid sending an empty value to the client if the user activates the inputField by"
                    +" selecting the custom option"
                }
            }
        }
        highlight {
            source(
                """
                // combine current value and internal state in a class                   
                inner class Selection(val value: String, val mode: String)
    
                private fun createStores(clientStore: Store<String>) =
                    object : RootStore<Selection>(Selection(clientStore.current, "inputDisabled")) {
                        val self = this
    
                        // the three states
                        private val inputActivated = "inputActivated"
                        private val inputActive = "inputActive"
                        val inputDisabled = "inputDisabled"
                        
                        val custom = "custom:"
    
                        val selectedStore = object : RootStore<String>(current.value) {
                            override val update = handle<String> { old, value ->
                                // reset the inputField when the user switches back to a predefined value
                                if (old == custom) {
                                    inputStore.reset(Unit)
                                }
                                // attach the appropriate state to the selected value
                                self.select(Selection(value, if (value == custom) inputActivated else inputDisabled))
                                value
                            }
                        }
    
                        val inputStore = object : RootStore<String>("") {
                            override val update = handle<String> { _, value ->
                                self.select(Selection(value, inputActive))
                                value
                            }
    
                            val reset: SimpleHandler<Unit> = handle { _ -> "" }
                        }
    
                        // emit the value to the client  
                        val select = handleAndEmit<Selection, String> { old, new ->
                            // the user just now activated the input, so do not send its value yet!
                            if (new.mode == inputActivated) {
                                emit(old.value)
                            } else {
                                emit(new.value)
                            }
                            new
                        }
    
                        init {
                            // propagate the value to the client side store
                            select handledBy clientStore.update
                        }
                    }
                """
            )
        }

        showcaseSubSection("Wrapping Function")
        paragraph {
            +"Now we can create the wrapping function which will be the entry point for the new control."
            +" To keep this example simple, we will not create a stand alone control (as you could, of course), but"
            +" create a combination of radioGroup and inputField instead. You could also override an existing wrapping function in order to replace its behaviour."
        }

        paragraph {
            +"Also note that some internal functions need to be called, and therefore have to be manually"
            +" integrated into your custom code in order to achieve a complete integration with"
            +" formControl's functionalities like automatic validation messages, styling aspects and so on."
        }
        highlight {
            source(
                """                                    
                fun radioGroupWithInput(
                    styling: BasicParams.() -> Unit = {},
                    items: List<String>,
                    store: Store<String>,
                    baseClass: StyleClass = StyleClass.None,
                    id: String? = null,
                    prefix: String = "radioGroupWithInput",
                    build: RadioGroupComponent<String>.() -> Unit
                ) {
                    val innerStore = createStores(store)
                    val validationMessagesBuilder = ValidationResult.builderOf(this, store)
                    // Important: Choose a unique key - if you override a function, use the appropriate
                    //            key from the predefined ones in ``FormControlComponent.ControlNames``
                    registerControl("radioGroupWithInput", {
                        // Place all your components inside this render context.
                        // For our example, we need the following two:                     
                        radioGroup(
                            styling,
                            items = items + innerStore.custom,
                            innerStore.selectedStore,
                            baseClass,
                            id,
                            prefix
                        ) {
                            size { this@ExtendedFormControlComponent.sizeBuilder(this) }
                            severity(validationMessagesBuilder().hasSeverity)
                            build()
                            orientation { vertical }
                        }
                        inputField({
                            margins { top { tiny } }
                        }, value = innerStore.inputStore) {
                            size { this@ExtendedFormControlComponent.sizeBuilder(this) }
                            severity(validationMessagesBuilder().hasSeverity)
                            // rely on the internal state to manage the activation 
                            disabled(innerStore.data.map { it.mode == innerStore.inputDisabled })
                            value(innerStore.inputStore.data)
                            placeholder("custom value")
                        }
                    }, {
                        this@ExtendedFormControlComponent.validationMessagesBuilder = validationMessagesBuilder
                    })
                }
                """
            )
        }
        paragraph {
            +"The final step is to bind our control to a fitting renderer."
            +" fritz2 offers two built-ins for our standard wrapped controls:"
            ul {
                li {
                    c("SingleControlRenderer")
                    +"is used to render forms with a single control, like an inputField for example."
                }
                li {
                    c("ControlGroupRenderer")
                    +"is used to render groups of controls, like checkBoxGroups and radioGroups."
                }
            }
            +"You can also write your own custom renderer, as we will explore in the next section."
        }
        highlight {
            source(
                """
                class ExtendedFormControlComponent : FormControlComponent() {
                
                    // non-essential code was omitted to keep it short
                    
                    // It's recommended to register your render strategy within the init block
                    init {
                        registerRenderStrategy("radioGroupWithInput", ControlGroupRenderer(this))
                    }
                }
                """
            )
        }

        coloredBox(Theme().colors.warning) {
            +"If you don't provide a renderer for a control, then of course nothing is rendered at all."
            +" So if you don't see anything on your page, you probably forgot the registration or have a typo in it."
        }

        showcaseSubSection("Custom Renderer")
        paragraph {
            +"A custom renderer allows you to change the way the form is rendered. Here's an example which does the following:"
            ul {
                li { +"Separate the label from the form with a bar" }
                li { +"Move the helper text up directly underneath the label" }
            }
            +"To do this, just implement the "
            c("ControlRenderer")
            +" interface and register your custom control with the new renderer."
        }
        highlight {
            source(
                """
                // to simplify our code, we chose to embed the renderer into our new
                // component class - feel free to put it anywhere it fits for your use case.
                inner class VerticalRenderer(private val component: FormControlComponent) : ControlRenderer {
                    override fun render(
                        styling: BasicParams.() -> Unit,
                        baseClass: StyleClass?,
                        id: String?,
                        prefix: String,
                        renderContext: RenderContext,
                        control: RenderContext.() -> Unit
                    ) {
                        renderContext.stackUp({
                            alignItems { start }
                            component.ownSize()()
                            styling()
                        }, baseClass, id, prefix) {
                            spacing { tiny }
                            items {
                                p({
                                    textAlign { right }
                                    borders {
                                        bottom {
                                            color { gray200 }
                                            width { fat }
                                        }
                                    }
                                }){ +component.label.value }
                                component.renderHelperText(this)
                                fieldset {
                                    control(this)
                                }
                                component.renderValidationMessages(this)
                            }
                        }
                    }
                }                    
                """
            )
        }

        paragraph {
            +"Don't forget to register the new renderer with the control:"
        }
        highlight {
            source(
                """
                class ExtendedFormControlComponent : FormControlComponent() {
                
                    // ...
                    
                    // change the registration to use the newly created renderer
                    init {
                        registerRenderStrategy("radioGroupWithInput", VerticalRenderer(this))
                    }
                }
                """
            )
        }

        showcaseSubSection("Recap")
        paragraph {
            +"This example showcases the complete process with all relevant aspects for creating your own control and"
            +" renderer, including deep customizations."
            +" You learned how to..."
            ul {
                li { +"set up a new component and its factory function" }
                li { +"write a new wrapping function for a control" }
                li {
                    +"use internal stores to process state changes and "
                    +"communicate it back to the client side"
                }
                li { +"write your own custom renderer for the whole form" }
            }
        }
        paragraph {
            +"fritz2 offers form control customization because it is sometimes necessary to change or extend the"
            +" built-in components and their behavior. However, we recommend to make careful use of this"
            +" as not to clutter your UI with lots of different looking and behaving forms."
        }
    }
}