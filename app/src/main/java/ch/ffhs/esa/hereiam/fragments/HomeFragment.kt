package ch.ffhs.esa.hereiam.fragments

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.MainActivity
import ch.ffhs.esa.hereiam.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), OnMapReadyCallback{
    private lateinit var locationText: EditText
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
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val btnViewList: Button = view.findViewById(R.id.btn_all_entries)

        btnViewList.setOnClickListener {
            val mainActivity: MainActivity = activity as MainActivity
            mainActivity.replaceFragment(EntryListFragment())
        }

        locationText = view.findViewById(R.id.location_edit_text) as EditText
        setOnKeyListenerOnLocationEditText(requireContext())
        return view
    }

    private fun setOnKeyListenerOnLocationEditText(context: Context) {
        locationText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    val latitudeLongitude =
                         getLocationFromAddress(context, locationText.text.toString())
                    if (latitudeLongitude != null) {
                        setLocationOnGoogleMaps(
                            latitudeLongitude.latitude,
                            latitudeLongitude.longitude,
                            locationText.text.toString()
                        )
                    }
                    return true
                }
                return false
            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let{
            googleMap = it
            //location of Bern
            setLocationOnGoogleMaps(46.948162, 7.436944, "FFHS Bern Welle 7")
        }
    }

    private fun setLocationOnGoogleMaps(latitude: Double, longitude: Double, marker: String) {
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title(marker))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }
    fun getLocationFromAddress(
        context: Context?,
        strAddress: String?
    ): LatLng? {
        val coder = Geocoder(context)
        val address: List<android.location.Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: android.location.Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.getLatitude(), location.getLongitude())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return p1
    }

}

