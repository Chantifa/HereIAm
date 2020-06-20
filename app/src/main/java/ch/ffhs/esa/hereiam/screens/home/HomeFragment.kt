package ch.ffhs.esa.hereiam.screens.home

import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentHomeBinding
import ch.ffhs.esa.hereiam.util.getShortAddress
import ch.ffhs.esa.hereiam.util.toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

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
        activity?.let {
            viewModel.initLocationService(
                Geocoder(it),
                LocationServices.getFusedLocationProviderClient(it)
            )
        }
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnAllEntries.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_entryListFragment))

        binding.btnResetPosition.setOnClickListener {
            CoroutineScope(Main).launch {
                try {
                    viewModel.resetLocation()
                    binding.yourLocation.text =
                        HereIAmApplication.currentLocation.getShortAddress()
                } catch (e: Exception) {
                    val msg = "Location not found. Reason: ${e.message}"
                    Timber.e(msg)
                    activity?.toast(msg)
                }
            }
            it.visibility = View.GONE
        }

        binding.locationEditText.setOnKeyListener { it, keyCode, event ->
            it as EditText

            // If the event is a key-down event and the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                try {
                    viewModel.changeMapBasedOnUserInput(it.text.toString())
                    binding.yourLocation.text = it.text.toString()
                    binding.btnResetPosition.visibility = View.VISIBLE
                    it.text.clear()
                } catch (e: Exception) {
                    val msg = "Location not found. Reason: ${e.message}"
                    Timber.e(msg)
                    activity?.toast(msg)
                }
            }
            false
        }
        return binding.root
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            viewModel.googleMap = it
            viewModel.updateMarkerOnMap()
        }
    }
}

