package ch.ffhs.esa.hereiam.screens.home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var viewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnViewList = view.btn_all_entries

        btnViewList.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_entryListFragment))

        var locationText = view.location_edit_text as EditText
        setOnKeyListenerOnLocationEditText(requireContext(), locationText)
        return view
    }

    private fun setOnKeyListenerOnLocationEditText(
        context: Context,
        locationText: EditText
    ) {
        locationText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    val latitudeLongitude =
                        viewModel.getLocationFromAddress(context, locationText.text.toString())
                    if (latitudeLongitude != null) {
                        viewModel.setLocationOnGoogleMaps(
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
        map?.let {
            viewModel.googleMap = it
            //location of Bern
            viewModel.setLocationOnGoogleMaps(46.948162, 7.436944, "FFHS Bern Welle 7")
        }
    }
}

