package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService

class RegistrationFormViewModel : ViewModel() {
    fun registerUser(email: String, password: String) {
        AuthenticationService.registerUser(email, password)
    }
}