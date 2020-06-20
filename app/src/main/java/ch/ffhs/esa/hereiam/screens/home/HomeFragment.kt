package ch.ffhs.esa.hereiam.screens.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentHomeBinding
import ch.ffhs.esa.hereiam.util.toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import timber.log.Timber

class HomeFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

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
        viewModel.initContext(context)
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnAllEntries.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_entryListFragment))

        val locationText = binding.locationEditText
        locationText.setOnKeyListener { _, keyCode, event ->
            // If the event is a key-down event and the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                try {
                    viewModel.changeMapBasedOnUserInput(locationText.text.toString())
                    binding.yourLocation.text = locationText.text.toString()
                    locationText.text.clear()
                } catch (e: Exception) {
                    val msg = "Location not found. Reason: ${e.message}";
                    Timber.e(msg)
                    activity?.toast(msg)
                }
            }
            false
        }
        viewModel.activity = requireActivity()
        return binding.root
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            viewModel.googleMap = it
            viewModel.updateMarkerOnMap()
        }
    }
}

