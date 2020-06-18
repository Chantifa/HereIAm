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
            val result = authenticationService.loginUser(email, password)
            if (result == null) {
                Timber.e("Login error")
            } else {
                Timber.e("Login successfully: $result")
            }
        }
    }
}