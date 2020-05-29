package ch.ffhs.esa.hereiam.screens.login.reset

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.FirebaseAuth

class LoginResetViewModel: ViewModel() {
    fun resetPassword(email: String) {
        FirebaseAuth.resetPassword(email)
    }
}