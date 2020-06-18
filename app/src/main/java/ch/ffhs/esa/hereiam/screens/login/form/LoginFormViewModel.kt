package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginFormViewModel : ViewModel() {

    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    fun loginUser(email: String, password: String) {
        CoroutineScope(IO).launch {
            try {
                authenticationService.loginUser(email, password)
                Timber.e("Login successfully!")
            } catch (e: Exception) {
                Timber.e("Login error: ${e.message}")
            }
        }
    }
}