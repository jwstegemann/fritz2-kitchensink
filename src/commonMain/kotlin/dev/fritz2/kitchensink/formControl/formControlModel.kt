package dev.fritz2.kitchensink.formControl

import dev.fritz2.components.validation.*
import dev.fritz2.identification.RootInspector
import dev.fritz2.identification.inspect
import dev.fritz2.lenses.Lenses

@Lenses
data class Account(
    val username: String,
    val passphrase: String,
    val interests: List<String>,
    val confirmation: Boolean
)

enum class AccountCreationPhase {
    Input,
    Registration
}

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

    private fun validatePassphrase(
        inspector: RootInspector<Account>,
        phase: AccountCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val passphrase = inspector.sub(L.Account.passphrase)
        if (passphrase.data.isBlank() && phase == AccountCreationPhase.Registration) {
            messages.add(errorMessage(passphrase.id, "Please specify a passphrase."))
        } else if (passphrase.data.isNotBlank()) {
            if (passphrase.data.length < 16) {
                messages.add(
                    warningMessage(passphrase.id, "We recommend a passphrase with at least 16 characters.")
                )
            }
            if (passphrase.data.lowercase() == "fritz2") {
                messages.add(
                    warningMessage(passphrase.id, "'fritz2' is a great framework, but a poor choice of passphrase.")
                )
            }
            addSuccessMessage(messages, phase, passphrase.id)
        }
        return messages
    }

    private fun validateInterests(
        inspector: RootInspector<Account>,
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val interests = inspector.sub(L.Account.interests)
        if (interests.data.size > 3) {
            messages.add(
                errorMessage(
                    interests.id,
                    "You have chosen ${interests.data.size} items, but only 3 items are allowed."
                )
            )
        }
        if (interests.data.contains("fritz2")) {
            messages.add(
                infoMessage(
                    interests.id,
                    "Thank you for choosing fritz2 - we appreciate your interest \uD83D\uDE00"
                )
            )
        }
        return messages
    }

    private fun validateConfirmation(
        inspector: RootInspector<Account>,
        phase: AccountCreationPhase
    ): List<ComponentValidationMessage> {
        val messages = mutableListOf<ComponentValidationMessage>()
        val confirmation = inspector.sub(L.Account.confirmation)
        if (!confirmation.data && phase == AccountCreationPhase.Registration) {
            messages.add(errorMessage(confirmation.id, "You must accept the license terms to register."))
        } else if (confirmation.data) {
            addSuccessMessage(messages, phase, confirmation.id)
        }
        return messages
    }
}
