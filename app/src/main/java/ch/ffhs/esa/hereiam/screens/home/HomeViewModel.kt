package ch.ffhs.esa.hereiam.screens.home

import android.content.Context
import android.location.Geocoder
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.services.LocationService
import ch.ffhs.esa.hereiam.services.LocationServiceImplementation
import ch.ffhs.esa.hereiam.util.getShortAddress
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

    lateinit var activity: ComponentActivity
    var context: Context? = null
    private lateinit var locationService: LocationService
    var xxx = HereIAmApplication.currentLocation.getShortAddress()

    lateinit var googleMap: GoogleMap
    private lateinit var currentMarker: Marker

    fun initContext(ctx: Context?) {
        context = ctx
        locationService = LocationServiceImplementation(Geocoder(ctx))
    }

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
            ?: throw Exception("Couldn't find location.")
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
}
