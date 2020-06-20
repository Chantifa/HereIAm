package ch.ffhs.esa.hereiam.services

import ch.ffhs.esa.hereiam.HereIAmApplication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

interface AuthenticationService {
    suspend fun loginUser(email: String, password: String)
    suspend fun registerUser(email: String, password: String)
    suspend fun resetPassword(email: String)
    fun signOut()
    fun updateCurrentUser()
}

class AuthenticationServiceFirebaseAuth : AuthenticationService {
    private val fbAuth = FirebaseAuth.getInstance()

    override suspend fun loginUser(email: String, password: String) {
        fbAuth.signInWithEmailAndPassword(email, password).await()
        updateCurrentUser()
    }

    override suspend fun registerUser(email: String, password: String) {
        fbAuth.createUserWithEmailAndPassword(email, password).await()
        updateCurrentUser()
    }

    override fun signOut() {
        fbAuth.signOut()
        updateCurrentUser()
    }

    override fun updateCurrentUser() {
        CoroutineScope(Main).launch {
            HereIAmApplication.currentUser.value = fbAuth.currentUser
        }
    }

    override suspend fun resetPassword(email: String) {
        fbAuth.sendPasswordResetEmail(email).await()
    }
}