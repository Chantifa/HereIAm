package ch.ffhs.esa.hereiam

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import ch.ffhs.esa.hereiam.services.LocationService
import ch.ffhs.esa.hereiam.services.LocationServiceImplementation
import ch.ffhs.esa.hereiam.util.Connection
import ch.ffhs.esa.hereiam.util.toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val REQUEST_CODE = 1111

// Default location: Welle 7, Bern
val DEFAULT_COORDINATES = LatLng(46.948162, 7.436944)
const val DEFAULT_LABEL = "FFHS Bern Welle 7"

class MainActivity : AppCompatActivity() {
    private val authenticationService: AuthenticationService = AuthenticationServiceFirebaseAuth()
    private lateinit var locationService: LocationService
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Connection(this)

        locationService = LocationServiceImplementation(
            Geocoder(this),
            LocationServices.getFusedLocationProviderClient(this)
        )
        initiateCurrentLocation()
    }

    private fun initiateCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
            return
        }
        loadCurrentLocation()
    }

    override fun onStart() {
        super.onStart()
        authenticationService.updateCurrentUser()
        navController = findNavController(R.id.fragment_container)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_menu).setupWithNavController(
            navController
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadCurrentLocation()
                } else {
                    loadDefaultAddress()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadCurrentLocation() {
        CoroutineScope(IO).launch {
            val location = locationService.getCurrentLocation()
            if (location == null) {
                loadDefaultAddress()
                withContext(Main) {
                    this@MainActivity.toast(getString(R.string.couldnt_get_location))
                }
                return@launch
            }
            val address = locationService.getAddressFromCoordinates(
                location.latitude,
                location.longitude
            )
            HereIAmApplication.currentLocation = address
        }
    }

    private fun loadDefaultAddress() {
        val address = locationService.getAddressFromCoordinates(DEFAULT_COORDINATES)
        address?.apply {
            this.thoroughfare = DEFAULT_LABEL
            this.featureName = null
            HereIAmApplication.currentLocation = address
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        val item = menu?.findItem(R.id.action_logout)
        item?.isVisible = HereIAmApplication.userLoggedIn()

        HereIAmApplication.currentUser.observeForever {
            item?.isVisible = HereIAmApplication.userLoggedIn()
        }
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

