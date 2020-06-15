package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuthImplementation

class RegistrationFormViewModel : ViewModel() {
    private val authenticationService = AuthenticationServiceFirebaseAuthImplementation()

    fun registerUser(email: String, password: String) {
        authenticationService.registerUser(email, password)
    }
}