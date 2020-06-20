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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

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
        // Current location isn't converting in Address right now

        locationText.setOnKeyListener { _, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                viewModel.changeMapBasedOnUserInput(locationText.text.toString())
            }
            false
        }
        viewModel.activity = requireActivity()
        viewModel.onActivity()
        return binding.root
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            viewModel.googleMap = it
            viewModel.updateMarkerOnMap()
        }
    }
}

