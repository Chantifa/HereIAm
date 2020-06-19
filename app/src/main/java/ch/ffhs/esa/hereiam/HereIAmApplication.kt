package ch.ffhs.esa.hereiam

import android.app.Application
import ch.ffhs.esa.hereiam.model.Location
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {

    companion object {
        var currentUser: FirebaseUser? = null

        fun userLoggedIn() = currentUser != null
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}