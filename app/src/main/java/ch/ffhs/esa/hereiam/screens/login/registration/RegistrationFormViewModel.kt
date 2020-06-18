package ch.ffhs.esa.hereiam.screens.login.registration

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class RegistrationFormViewModel : ViewModel() {
    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()

    fun registerUser(email: String, password: String) {
        CoroutineScope(IO).launch {
            try {
                val result = authenticationService.registerUser(email, password)
                Timber.e("$result")
            } catch (e: Exception) {
                Timber.e("Error: ${e.message}")
            }
        }
    }
}