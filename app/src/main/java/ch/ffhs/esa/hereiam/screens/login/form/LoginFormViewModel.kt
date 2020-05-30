package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService

class LoginFormViewModel : ViewModel() {
    fun loginUser(email: String, password: String) {
        AuthenticationService.loginUser(email, password)
    }

}