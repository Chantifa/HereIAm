package ch.ffhs.esa.hereiam

import android.app.Application
import android.location.Address
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {

    companion object {
        var currentUser = MutableLiveData<FirebaseUser?>()
        var currentLocation: Address? = null

        fun userLoggedIn() = currentUser.value != null
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}