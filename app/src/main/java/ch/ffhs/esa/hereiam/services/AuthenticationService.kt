package ch.ffhs.esa.hereiam.services

import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class AuthenticationService {
    companion object {
        private val fbAuth = FirebaseAuth.getInstance()
        val currentUser = fbAuth.currentUser
        fun resetPassword(
            email: String
        ) {
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

        fun loginUser(email: String, password: String) {
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

        fun registerUser(email: String, password: String) {
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

        fun signOut() {
            fbAuth.signOut()
        }

    }
}