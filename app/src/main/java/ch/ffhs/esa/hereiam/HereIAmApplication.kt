package ch.ffhs.esa.hereiam

import android.app.Application
import androidx.lifecycle.MutableLiveData
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber


class HereIAmApplication : Application() {
/*

    companion object{
        val currentLocation = HomeViewModel.locationName
        val currentUser = MutableLiveData<FirebaseUser>()

       fun getUser(): MutableLiveData<FirebaseUser> {
           return currentUser
        }

        fun getLocation(): String {
            return currentLocation.toString()
        }


    }

 */


    override fun onCreate() {
        super.onCreate()

        // Activate 'Timber', a logging utility (https://github.com/JakeWharton/timber) for the project
        Timber.plant(Timber.DebugTree())
    }
}