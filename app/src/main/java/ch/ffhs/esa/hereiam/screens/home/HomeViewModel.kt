package ch.ffhs.esa.hereiam.screens.home

import android.app.Activity
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.MainActivity
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.services.LocationService
import ch.ffhs.esa.hereiam.util.getShortAddress
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

    private lateinit var locationService: LocationService
    var currentLocation = HereIAmApplication.currentLocation.getShortAddress()

    lateinit var googleMap: GoogleMap
    private lateinit var currentMarker: Marker


    private fun setLocationOnGoogleMaps(location: LatLng, title: String?) {
        if (this::currentMarker.isInitialized) {
            currentMarker.remove()
        }
        currentMarker =
            googleMap.addMarker(MarkerOptions().position(location).title(title))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    fun changeMapBasedOnUserInput(
        query: String
    ) {
        val coordinates = locationService.getCoordinatesFromLocationName(query)
            ?: throw Exception(Activity().getString(R.string.error_missing_location))
        val address = locationService.getAddressFromCoordinates(coordinates)
        HereIAmApplication.currentLocation = address
        updateMarkerOnMap()
    }

    fun updateMarkerOnMap() {
        val currentLocation = HereIAmApplication.currentLocation
        currentLocation?.let { adr ->
            setLocationOnGoogleMaps(LatLng(adr.latitude, adr.longitude), adr.getShortAddress())
        }
    }

    fun initLocationService(locationService: LocationService) {
        this.locationService = locationService
    }

    suspend fun resetLocation() {
        val location = locationService.getCurrentLocation()
            ?: throw Exception(MainActivity().getString(R.string.couldnt_get_location))
        val address = locationService.getAddressFromCoordinates(
            location.latitude,
            location.longitude
        )
        HereIAmApplication.currentLocation = address
        updateMarkerOnMap()
    }
}
