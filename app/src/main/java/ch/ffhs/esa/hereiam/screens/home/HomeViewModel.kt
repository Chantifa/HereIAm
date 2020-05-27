package ch.ffhs.esa.hereiam.screens.home

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

    lateinit var googleMap: GoogleMap

    private fun getLocationFromAddress(
        context: Context?,
        locationName: String?
    ): LatLng? {
        val coder = Geocoder(context)
        try {
            val address = coder.getFromLocationName(locationName, 5)
            return address?.let {
                val location = it[0]
                LatLng(location.latitude, location.longitude)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun setLocationOnGoogleMaps(location: LatLng, locationName: String) {
        googleMap.addMarker(MarkerOptions().position(location).title(locationName))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    fun loadDefaultLocation() {
        // Location of Bern
        val latitude = 46.948162
        val longitude = 7.436944
        val locationName = "FFHS Bern Welle 7"

        setLocationOnGoogleMaps(LatLng(latitude, longitude), locationName)
    }

    fun changeMapBasedOnUserInput(ctx: Context?, locationName: String) {
        val location =
            getLocationFromAddress(ctx, locationName)

        location?.let {
            setLocationOnGoogleMaps(it, locationName)
        }
    }
}