package ch.ffhs.esa.hereiam.screens.home

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

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

    private fun getLocationFromAddress(
        context: Context?
    ): LatLng? {
        val coder = Geocoder(context)
        val address = coder.getFromLocationName(locationName.value, 5)
        return if (address.isNullOrEmpty()) null else address.let {
            val location = it[0]
            LatLng(location.latitude, location.longitude)
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

    fun loadDefaultLocation() {
        // Location of Bern
        val latitude = 46.948162
        val longitude = 7.436944
        _locationName.value = "FFHS Bern Welle 7"

        setLocationOnGoogleMaps(LatLng(latitude, longitude))
    }

    fun changeMapBasedOnUserInput(
        ctx: Context?,
        newLocationName: String
    ) {
//        Timber.i("Oliver $_locationName")
        _locationName.value = newLocationName
        val location =
            getLocationFromAddress(ctx)

        location?.let {
            setLocationOnGoogleMaps(it)
        }
    }
}