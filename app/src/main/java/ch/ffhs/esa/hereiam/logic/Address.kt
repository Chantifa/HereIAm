package ch.ffhs.esa.hereiam.logic

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

fun getLocationFromAddress(
    context: Context?,
    strAddress: String?
): LatLng? {
    val coder = Geocoder(context)
    val address: List<android.location.Address>?
    var p1: LatLng? = null
    try {
        address = coder.getFromLocationName(strAddress, 5)
        if (address == null) {
            return null
        }
        val location: android.location.Address = address[0]
        location.latitude
        location.longitude
        p1 = LatLng(location.getLatitude(), location.getLongitude())
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return p1
}
