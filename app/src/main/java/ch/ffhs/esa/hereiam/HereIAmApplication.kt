package ch.ffhs.esa.hereiam

import android.app.Application
import android.location.Address
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {

    companion object {
        var currentLocation: Address? = null
        var currentUser: FirebaseUser? = null

        fun userLoggedIn() = currentUser != null
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}