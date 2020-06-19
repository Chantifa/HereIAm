package ch.ffhs.esa.hereiam.services

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

interface LocationService {
    val geocoder: Geocoder

    fun getCoordinatesFromLocationName(query: String): LatLng?

    fun getAddressFromCoordinates(lat: Double, long: Double): Address?

    fun getAddressFromCoordinates(coords: LatLng): Address? =
        getAddressFromCoordinates(coords.latitude, coords.longitude)
}

class LocationServiceImplementation(override val geocoder: Geocoder) : LocationService {

    override fun getCoordinatesFromLocationName(query: String): LatLng? {
        val result = geocoder.getFromLocationName(query, 1)
        return if (result.isNullOrEmpty()) null else LatLng(result[0].latitude, result[0].longitude)
    }

    override fun getAddressFromCoordinates(lat: Double, long: Double): Address? {
        val result = geocoder.getFromLocation(lat, long, 1)
        return if (result.isNullOrEmpty()) null else result[0]
    }
}