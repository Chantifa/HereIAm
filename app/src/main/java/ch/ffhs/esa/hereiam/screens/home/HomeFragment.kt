package ch.ffhs.esa.hereiam.screens.home


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback


const val PERMISSION_ID = 42



class HomeFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var mFusedLocationClient: FusedLocationProviderClient

//    private val mLocation = viewModel.getLastLocation()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val btnViewList = binding.btnAllEntries
        btnViewList.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_entryListFragment))

        val locationText = binding.locationEditText
        val wishedLocation = binding.wishedLocation
        // Current location isn't converting in Address right now
        //val yourLocation = binding.yourlocation
        //val input = viewModel.getLocationFromAddress(mLocation)
      // val currentLocation  = viewModel.getLocation(input)

        locationText.setOnKeyListener { _, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                viewModel.changeMapBasedOnUserInput(context, locationText.text.toString())
                wishedLocation.setText(locationText.text).toString()
            }
            false
        }
        viewModel.activity = requireActivity()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel.mFusedLocationClient = mFusedLocationClient

        // yourLocation.setText(currentLocation.toString()).toString()
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ID -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.getLastLocation()
                } else {
                    Toast.makeText(activity, "Aktiviere Standort!", Toast.LENGTH_SHORT).show()
                }
                return
            }

        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            viewModel.googleMap = it
            viewModel.getLastLocation()
        }
    }
}

