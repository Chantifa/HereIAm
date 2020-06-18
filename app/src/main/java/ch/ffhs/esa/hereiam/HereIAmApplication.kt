package ch.ffhs.esa.hereiam

import android.app.Application
import androidx.lifecycle.MutableLiveData
import ch.ffhs.esa.hereiam.model.Location
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {

    companion object {
        val currentLocation = MutableLiveData<Location>()
        val currentUser = MutableLiveData<FirebaseUser?>()

        fun userLoggedIn() = currentUser.value != null
    }

    override fun onCreate() {
        super.onCreate()

        // Activate 'Timber', a logging utility (https://github.com/JakeWharton/timber) for the project
        Timber.plant(Timber.DebugTree())
    }
}