package dev.fritz2.kitchensink.formControl

import dev.fritz2.components.validation.*
import dev.fritz2.identification.Inspector
import dev.fritz2.lenses.Lenses

@Lenses
data class Account(
    val username: String = "",
    val passphrase: String = "",
    val interests: List<String> = emptyList(),
    val confirmation: Boolean = false
)

enum class AccountCreationPhase {
    Input,
    Registration
}

class AccountValidator : ComponentValidator<Account, AccountCreationPhase>() {
    override fun validate(inspector: Inspector<Account>, metadata: AccountCreationPhase): List<ComponentValidationMessage> {
        return validateUsername(inspector, metadata) +
                validatePassphrase(inspector, metadata) +
                validateInterests(inspector) +
                validateConfirmation(inspector, metadata)
    }

    private fun addSuccessMessage(
        messages: MutableList<ComponentValidationMessage>,
        phase: AccountCreationPhase,
        path: String
    ) {
        if (phase == AccountCreationPhase.Input && !messages.any { it.isError() }) {
            messages.add(successMessage(path, ""))
        }
    }

    private fun validateUsername(
        inspector: Inspector<Account>,
        phase: AccountCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val username = inspector.sub(L.Account.username)
        if (username.data.isBlank() && phase == AccountCreationPhase.Registration) {
            messages.add(username.errorMessage( "Please choose a username."))
        } else if (username.data.isNotBlank()) {
            if (username.data.contains(':')) {
                messages.add(username.errorMessage("Colon is not allowed in username."))
            }
            if (username.data.length < 3) {
                messages.add(username.warningMessage( "We recommend a username with at least 3 characters."))
            }
            addSuccessMessage(messages, phase, username.path)
        }
        return messages
    }

    private fun validatePassphrase(
        inspector: Inspector<Account>,
        phase: AccountCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val passphrase = inspector.sub(L.Account.passphrase)
        if (passphrase.data.isBlank() && phase == AccountCreationPhase.Registration) {
            messages.add(passphrase.errorMessage( "Please specify a passphrase."))
        } else if (passphrase.data.isNotBlank()) {
            if (passphrase.data.length < 16) {
                messages.add(passphrase.warningMessage( "We recommend a passphrase with at least 16 characters."))
            }
            if (passphrase.data.lowercase() == "fritz2") {
                messages.add(passphrase.warningMessage("'fritz2' is a great framework, but a poor choice of passphrase."))
            }
            addSuccessMessage(messages, phase, passphrase.path)
        }
        return messages
    }

    private fun validateInterests(
        inspector: Inspector<Account>,
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val interests = inspector.sub(L.Account.interests)
        if (interests.data.size > 3) {
            messages.add(
                interests.errorMessage("You have chosen ${interests.data.size} items, but only 3 items are allowed.")
            )
        }
        if (interests.data.contains("fritz2")) {
            messages.add(
                interests.infoMessage("Thank you for choosing fritz2 - we appreciate your interest \uD83D\uDE00")
            )
        }
        return messages
    }

    private fun validateConfirmation(
        inspector: Inspector<Account>,
        phase: AccountCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val confirmation = inspector.sub(L.Account.confirmation)
        if (!confirmation.data && phase == AccountCreationPhase.Registration) {
            messages.add(confirmation.errorMessage("You must accept the license terms to register."))
        } else if (confirmation.data) {
            addSuccessMessage(messages, phase, confirmation.path)
        }
        return messages
    }
}
