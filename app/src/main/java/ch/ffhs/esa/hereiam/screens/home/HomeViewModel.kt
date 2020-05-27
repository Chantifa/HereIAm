package ch.ffhs.esa.hereiam.screens.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeViewModel : ViewModel() {

    lateinit var googleMap: GoogleMap

    fun getLocationFromAddress(
        context: Context?,
        strAddress: String?
    ): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.getLatitude(), location.getLongitude())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return p1
    }

    fun setLocationOnGoogleMaps(latitude: Double, longitude: Double, marker: String) {
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title(marker))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }
}