package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFormViewModel : ViewModel() {

    val currentUser = MutableLiveData<FirebaseUser>()
    private val authenticationService = AuthenticationServiceFirebaseAuth()

    init {
        authenticationService.getCurrentUser(currentUser)
    }

    fun loginUser(email: String, password: String) {
        authenticationService.loginUser(email, password)
    }

}