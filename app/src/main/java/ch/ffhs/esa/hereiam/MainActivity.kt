package ch.ffhs.esa.hereiam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.BuildConfig
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val authenticationService = AuthenticationServiceFirebaseAuth()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        authenticationService.updateCurrentUser()
        navController = findNavController(R.id.fragment_container)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_menu).setupWithNavController(
            navController
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            super.onOptionsItemSelected(item)
        }
        if (item?.itemId == R.id.action_logout) {

            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.logout_question))
                setPositiveButton(getString(R.string.logout_yes)) { _, _ ->
                    authenticationService.signOut()
                    navController.navigate(R.id.nav_home)
                }
                setNegativeButton(getString(R.string.logout_abort)) { _, _ ->
                }
            }.create().show()

        }
        return true
    }

}

