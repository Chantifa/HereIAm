package ch.ffhs.esa.hereiam.services

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.tasks.await

interface LocationService {
    fun getCoordinatesFromLocationName(query: String): LatLng?

    fun getAddressFromCoordinates(lat: Double, long: Double): Address?

    fun getAddressFromCoordinates(coords: LatLng): Address? =
        getAddressFromCoordinates(coords.latitude, coords.longitude)

    suspend fun getCurrentLocation(): Location?
}

class LocationServiceImplementation(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationService {

    override fun getCoordinatesFromLocationName(query: String): LatLng? {
        val result = geocoder.getFromLocationName(query, 1)
        return if (result.isNullOrEmpty()) null else LatLng(result[0].latitude, result[0].longitude)
    }

    override fun getAddressFromCoordinates(lat: Double, long: Double): Address? {
        val result = geocoder.getFromLocation(lat, long, 1)
        return if (result.isNullOrEmpty()) null else result[0]
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        return fusedLocationClient.lastLocation.await()
    }
}