package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth

class RegistrationFormViewModel : ViewModel() {
    private val authenticationService = AuthenticationServiceFirebaseAuth()

    fun registerUser(email: String, password: String) {
        authenticationService.registerUser(email, password)
    }
}