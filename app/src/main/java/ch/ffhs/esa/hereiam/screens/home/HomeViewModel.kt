package ch.ffhs.esa.hereiam.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.services.LocationService
import ch.ffhs.esa.hereiam.services.LocationServiceImplementation
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

class HomeViewModel : ViewModel() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var activity: ComponentActivity
    var context: Context? = null
    private lateinit var locationService: LocationService
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

    private fun loadDefaultLocation() {

        val latitude = 46.948162
        val longitude = 7.436944
        // Location of Bern
        _locationName.value = "FFHS Bern Welle 7"

        setLocationOnGoogleMaps(LatLng(latitude, longitude))
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

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    val location: android.location.Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val latLng = LatLng(latitude, longitude)
                        _location.postValue(latLng)
                        currentLocation.postValue(latLng)
                        setLocationOnGoogleMaps(latLng)
                    }
                }
            } else {
                val toast =
                    makeText(context, context?.getString(R.string.please_activate_access_on_location), Toast.LENGTH_SHORT)
                toast.show()
                loadDefaultLocation()
            }
        } else {
            requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
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
            val latLng = LatLng(latitude, longitude)
            _location.postValue(latLng)
            currentLocation.postValue(latLng)
            setLocationOnGoogleMaps(latLng)
        }
    }
}
