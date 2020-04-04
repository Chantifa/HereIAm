package ch.ffhs.esa.hereiam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.MainActivity
import ch.ffhs.esa.hereiam.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val listOfListeners: List<View> = listOf(
            view.findViewById(R.id.img_placeholder),
            view.findViewById(R.id.heading_placeholder),
            view.findViewById(R.id.text_placeholder)
        )
        val mainActivity: MainActivity = activity as MainActivity
        listOfListeners.forEach { elem ->
            elem.setOnClickListener {
                mainActivity.replaceFragment(EntryDetailFragment())
            }
        }
        return view
    }

}
