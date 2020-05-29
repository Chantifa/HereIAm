package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.FirebaseAuth

class RegistrationFormViewModel : ViewModel() {
    fun registerUser(email: String, password: String) {
        FirebaseAuth.registerUser(email, password)
    }
}