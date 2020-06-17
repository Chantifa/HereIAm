package ch.ffhs.esa.hereiam.screens.login.reset

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth

class LoginResetViewModel: ViewModel() {
    private val authenticationService = AuthenticationServiceFirebaseAuth()

    fun resetPassword(email: String) {
        authenticationService.resetPassword(email)
    }
}