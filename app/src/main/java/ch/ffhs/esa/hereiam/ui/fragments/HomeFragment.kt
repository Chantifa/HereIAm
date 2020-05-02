package ch.ffhs.esa.hereiam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.MainActivity
import ch.ffhs.esa.hereiam.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), OnMapReadyCallback{
    private lateinit var googleMap : GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val listOfListeners: List<View> = listOf(
            view.findViewById(R.id.map_view)
        )
        val mainActivity: MainActivity = activity as MainActivity
        listOfListeners.forEach { elem ->

            elem.setOnClickListener {
                mainActivity.replaceFragment(EntryDetailFragment())
            }
        }
        return view
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let{
        googleMap = it

        val location = com.google.android.gms.maps.model.LatLng(46.948162, 7.436944)
        googleMap.addMarker(MarkerOptions().position(location).title("FFHS Bern Welle 7"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }
    }
}

