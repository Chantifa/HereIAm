package ch.ffhs.esa.hereiam.screens.login.reset

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService

class LoginResetViewModel: ViewModel() {
    fun resetPassword(email: String) {
        AuthenticationService.resetPassword(email)
    }
}