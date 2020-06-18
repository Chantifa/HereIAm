package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import timber.log.Timber

class LoginFormViewModel : ViewModel() {

    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    fun loginUser(email: String, password: String) {
        val result = liveData {
            emit(authenticationService.loginUser(email, password))
        }
        result.observeForever {
            Timber.e("Login finished: $it")
        }
    }
}