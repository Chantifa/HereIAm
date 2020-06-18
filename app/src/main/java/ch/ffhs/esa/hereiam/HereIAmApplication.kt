package ch.ffhs.esa.hereiam

import android.app.Application
import ch.ffhs.esa.hereiam.model.Location
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {

    companion object {
        var currentLocation: Location? = null
        var currentUser: FirebaseUser? = null
        
        fun userLoggedIn() = currentUser != null
    }

    override fun onCreate() {
        super.onCreate()

        // Activate 'Timber', a logging utility (https://github.com/JakeWharton/timber) for the project
        Timber.plant(Timber.DebugTree())
    }
}