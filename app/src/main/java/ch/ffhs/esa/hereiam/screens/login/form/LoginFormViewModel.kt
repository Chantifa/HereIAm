package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth

class LoginFormViewModel : ViewModel() {

    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    fun loginUser(email: String, password: String) {
        authenticationService.loginUser(email, password)
    }
}