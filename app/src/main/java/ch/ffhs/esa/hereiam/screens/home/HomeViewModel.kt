package ch.ffhs.esa.hereiam.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.core.app.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.services.LocationService
import ch.ffhs.esa.hereiam.services.LocationServiceImplementation
import ch.ffhs.esa.hereiam.util.getShortAddress
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var activity: ComponentActivity
    var context: Context? = null
    private lateinit var locationService: LocationService
    val xyz = HereIAmApplication.currentLocation
    val xxx = xyz.getShortAddress()
    val currentLocation = MutableLiveData<LatLng>()

    val locationName = Transformations.map(currentLocation) { latLng ->
        locationService.getAddressFromCoordinates(latLng)?.thoroughfare
    }

    companion object Location {
        private val _locationName = MutableLiveData<String>()
        val locationName: LiveData<String>
            get() = _locationName

        private val _location = MutableLiveData<LatLng>()
        val location: LiveData<LatLng>
            get() = _location
    }

    lateinit var googleMap: GoogleMap
    private lateinit var currentMarker: Marker

    fun initContext(ctx: Context?) {
        context = ctx
        locationService = LocationServiceImplementation(Geocoder(ctx))
    }

    @SuppressLint("RestrictedApi")
    fun onActivity() {
        location.observe({ activity.lifecycle }) { latLng ->
            latLng?.let {
                val address =
                    locationService.getAddressFromCoordinates(latLng)

                _locationName.postValue(address?.thoroughfare)
            }
        }
    }

    private fun setLocationOnGoogleMaps(location: LatLng) {
        if (this::currentMarker.isInitialized) {
            currentMarker.remove()
        }
        currentMarker =
            googleMap.addMarker(MarkerOptions().position(location).title(locationName.value))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    fun changeMapBasedOnUserInput(
        query: String
    ) {
        val location = locationService.getCoordinatesFromLocationName(query)

        _location.postValue(location)

        location?.let {
            setLocationOnGoogleMaps(it)
        }
    }

    fun updateMarkerOnMap() {
        val currentLocation = HereIAmApplication.currentLocation
        currentLocation?.let { adr ->
            setLocationOnGoogleMaps(LatLng(adr.latitude, adr.longitude))
        }
    }
}
