package ch.ffhs.esa.hereiam.services

import ch.ffhs.esa.hereiam.HereIAmApplication
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import timber.log.Timber

interface AuthenticationService {
    fun resetPassword(email: String)
    suspend fun loginUser(email: String, password: String): AuthResult?
    fun registerUser(email: String, password: String)
    fun signOut()
    fun updateCurrentUser()
}

class AuthenticationServiceFirebaseAuth : AuthenticationService {
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

    override suspend fun loginUser(email: String, password: String): AuthResult? {
        return try {
            fbAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
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
        updateCurrentUser()
    }

    override fun updateCurrentUser() {
        HereIAmApplication.currentUser = fbAuth.currentUser
    }
}