package dev.fritz2.kitchensink

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
            messages.add(errorMessage(username.id, "Username should not be empty!"))
        } else if (username.data.isNotBlank()) {
            if (username.data.contains(':')) {
                messages.add(errorMessage(username.id, "Username should not contain a colon!"))
            }
            if (username.data.length < 3) {
                messages.add(warningMessage(username.id, "Consider a longer name!"))
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
            messages.add(errorMessage(passphrase.id, "Passphrase should not be empty!"))
        } else if (passphrase.data.isNotBlank()) {
            if (passphrase.data.length < 16) {
                messages.add(
                    warningMessage(passphrase.id, "Consider a passphrase with at least 16 characters")
                )
            }
            if (passphrase.data.toLowerCase() == "fritz2") {
                messages.add(
                    warningMessage(passphrase.id, "'fritz2' is a great framework, but a poor passphrase!")
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
                    "You have chosen ${interests.data.size} items, but only 3 items are allowed!"
                )
            )
        }
        if (interests.data.contains("fritz2")) {
            messages.add(
                infoMessage(
                    interests.id,
                    "Thank you for choosing fritz2! We appreciate your interest \uD83D\uDE00"
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
            messages.add(errorMessage(confirmation.id, "You must accept license conditions to register!"))
        } else if (confirmation.data) {
            addSuccessMessage(messages, phase, confirmation.id)
        }
        return messages
    }
}
