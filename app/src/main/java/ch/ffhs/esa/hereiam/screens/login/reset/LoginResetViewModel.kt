package ch.ffhs.esa.hereiam.screens.login.reset

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuthImplementation

class LoginResetViewModel: ViewModel() {
    private val authenticationService = AuthenticationServiceFirebaseAuthImplementation()

    fun resetPassword(email: String) {
        authenticationService.resetPassword(email)
    }
}