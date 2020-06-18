package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth

class RegistrationFormViewModel : ViewModel() {
    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    fun registerUser(email: String, password: String) {
        authenticationService.registerUser(email, password)
    }
}