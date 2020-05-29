package ch.ffhs.esa.hereiam.services

import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class FirebaseAuth {
    companion object {
        fun resetPassword(
            email: String
        ) {
            FirebaseAuth.getInstance()
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

    }
}