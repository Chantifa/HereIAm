package ch.ffhs.esa.hereiam

import android.app.Application
import timber.log.Timber

class HereIAmApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Activate 'Timber', a logging utility (https://github.com/JakeWharton/timber) for the project
        Timber.plant(Timber.DebugTree())
    }
}