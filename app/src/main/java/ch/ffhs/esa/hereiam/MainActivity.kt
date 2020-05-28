package ch.ffhs.esa.hereiam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import ch.ffhs.esa.hereiam.fragments.EntryFormFragment
import ch.ffhs.esa.hereiam.fragments.HomeFragment
import ch.ffhs.esa.hereiam.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navListener =

        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_edit -> {
                    replaceFragment(EntryFormFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_menu.setOnNavigationItemSelectedListener(navListener)
        replaceFragment(HomeFragment())

        val navController = Navigation.findNavController(this, R.id.bottom_navigation_menu)
        NavigationUI.setupWithNavController(bottom_navigation_menu, navController)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController
        )
    }

    /**
     * Helper class to exchange fragments in the fragment container
     */
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_logout) {

            AlertDialog.Builder(this).apply {
                setTitle("Are you sure?")
                setPositiveButton("Yes") { _, _ ->

                    FirebaseAuth.getInstance().signOut()
                    logout()

                }
                setNegativeButton("Cancel") { _, _ ->
                }
            }.create().show()

        }
        return super.onOptionsItemSelected(item)
    }
}
