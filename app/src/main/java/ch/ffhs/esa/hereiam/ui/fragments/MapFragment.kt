package ch.ffhs.esa.hereiam.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.ffhs.esa.hereiam.models.FragementMapViewModel
import ch.ffhs.esa.hereiam.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*


class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: FragementMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            map_view.onCreate(savedInstanceState)
            map_view.onResume()
            map_view.getMapAsync(this)
        
        viewModel = ViewModelProviders.of(this).get(FragementMapViewModel::class.java)
        // TODO: Use the ViewModel
    }



    override fun onMapReady(map: GoogleMap?) {
        map?.let{

        }
        googleMap.isMyLocationEnabled = true
        val location = LatLng(46.948162, 7.436944)
        googleMap.addMarker(MarkerOptions().position(location).title("FFHS Bern Welle 7"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10f))

    }

}
