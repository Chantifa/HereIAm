package ch.ffhs.esa.hereiam.services

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

interface AuthenticationService {
    fun resetPassword(email: String)
    fun loginUser(email: String, password: String)
    fun registerUser(email: String, password: String)
    fun signOut()
    fun getCurrentUser(currentUser: MutableLiveData<FirebaseUser>)
}

class AuthenticationServiceFirebaseAuthImplementation : AuthenticationService {
    private val fbAuth = FirebaseAuth.getInstance()
    override fun resetPassword(email: String) {
        fbAuth
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                // TODO: User feedback
                if (task.isSuccessful) {
                    Timber.i("success")
                } else {
                    Timber.i("error ${task.exception?.message!!}")
                }
            }
    }

    override fun loginUser(email: String, password: String) {
        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                // TODO: login
                if (task.isSuccessful) {
                    Timber.i("success")
                } else {
                    Timber.i("error ${task.exception?.message!!}")
                }
            }
    }

    override fun registerUser(email: String, password: String) {
        fbAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                // TODO: login
                if (task.isSuccessful) {
                    Timber.i("success")
                } else {
                    Timber.i("error ${task.exception?.message!!}")
                }
            }

    }

    override fun signOut() {
        fbAuth.signOut()
    }

    override fun getCurrentUser(currentUser: MutableLiveData<FirebaseUser>) {
        currentUser.value = fbAuth.currentUser
    }
}