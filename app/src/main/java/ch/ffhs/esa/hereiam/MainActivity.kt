package ch.ffhs.esa.hereiam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ch.ffhs.esa.hereiam.services.FirebaseAuth
import ch.ffhs.esa.hereiam.util.logout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        // Set up bottom navigation
        val navController = findNavController(R.id.fragment_container)
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

                    FirebaseAuth.signOut()
                    this.context.logout()

                }
                setNegativeButton(getString(R.string.logout_abort)) { _, _ ->
                }
            }.create().show()

        }
        return true
    }
}
