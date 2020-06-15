package ch.ffhs.esa.hereiam.screens.login.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService
import com.google.firebase.auth.FirebaseUser

class LoginFormViewModel : ViewModel() {

    val currentUser = MutableLiveData<FirebaseUser>()

    init {
        AuthenticationService.getCurrentUser(currentUser)
    }

    fun loginUser(email: String, password: String) {
        AuthenticationService.loginUser(email, password)
    }

}