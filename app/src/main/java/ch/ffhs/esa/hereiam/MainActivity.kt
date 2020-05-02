package ch.ffhs.esa.hereiam

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.ui.fragments.EntryFormFragment
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
                    replaceFragment(EntryFormFragment())
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

        bottom_navigation_menu.setOnNavigationItemSelectedListener(navListener)
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

    fun createEntry(view: View) {}
}
