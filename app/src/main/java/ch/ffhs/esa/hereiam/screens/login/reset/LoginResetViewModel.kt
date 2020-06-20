package ch.ffhs.esa.hereiam.screens.login.reset

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth

class LoginResetViewModel : ViewModel() {
    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    suspend fun resetPassword(email: String) {
        authenticationService.resetPassword(email)
    }
}
