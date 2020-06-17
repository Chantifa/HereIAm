package ch.ffhs.esa.hereiam.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class HomeViewModel : ViewModel() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var activity: Activity
    var context : Context? = null

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

    fun getLocationFromAddress(
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

    fun getLocation(locationResult: LocationResult){
                val mLastLocation: android.location.Location = locationResult.lastLocation
                val latitude = mLastLocation.latitude
                val longitude = mLastLocation.longitude
            getCompleteAddress(latitude,longitude)
        }


    private fun getCompleteAddress(
        LATITUDE: Double,
        LONGITUDE: Double
    ): String? {
        var address = ""
        val geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                address = strReturnedAddress.toString()
                Log.w("Deine Adresse", strReturnedAddress.toString())
            } else {
                Log.w("Deine Adresse", "Keine Adresse!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w("Keine Adresse", "Keine Adresse")
        }
        return address
    }

    fun loadDefaultLocation() {

        val latitude = 46.948162
        val longitude = 7.436944
        // Location of Bern
        _locationName.value = "FFHS Bern Welle 7"

        setLocationOnGoogleMaps(LatLng(latitude, longitude))
    }

    fun changeMapBasedOnUserInput(
        ctx: Context?,
        newLocationName: String
    ) {
        _locationName.value = newLocationName
        val location =
            getLocationFromAddress(ctx)

        location?.let {
            setLocationOnGoogleMaps(it)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    val location: android.location.Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        setLocationOnGoogleMaps(LatLng(latitude, longitude))
                    }
                }
            } else {
                val toast = makeText(context, "Bitte Zugriff auf Standort aktivieren!", Toast.LENGTH_SHORT)
                toast.show()
                loadDefaultLocation()
            }
        } else {
            requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID)
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = getFusedLocationProviderClient(activity)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: android.location.Location = locationResult.lastLocation
            val latitude = mLastLocation.latitude
            val longitude = mLastLocation.longitude
            setLocationOnGoogleMaps(LatLng(latitude, longitude))
        }
    }

}
