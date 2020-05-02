package ch.ffhs.esa.hereiam

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.ui.fragments.EntryFragment
import ch.ffhs.esa.hereiam.ui.fragments.HomeFragment
import ch.ffhs.esa.hereiam.ui.fragments.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                    replaceFragment(EntryFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(LoginFragment())
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // listen to clicks on the bottom navigation menu
        bottom_navigation_menu.setOnNavigationItemSelectedListener(navListener)

        // set home as default fragment
        replaceFragment(HomeFragment())
    }


    /**
     * Helper class to exchange fragments in the fragment container
     */
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
