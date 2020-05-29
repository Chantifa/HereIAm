package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.FirebaseAuth

class LoginFormViewModel : ViewModel() {
    fun loginUser(email: String, password: String) {
        FirebaseAuth.loginUser(email, password)
    }

}