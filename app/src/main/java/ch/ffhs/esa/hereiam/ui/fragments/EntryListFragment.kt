package ch.ffhs.esa.hereiam.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ch.ffhs.esa.hereiam.R
import com.google.firebase.firestore.FirebaseFirestore


class EntryListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_entry_list_all, container, false)

        val entries = FirebaseFirestore.getInstance()
            .collection(getString(R.string.firestore_collection_path))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Eintrag", "${document.id} => ${document.data}")
                }
            }

        return view
    }
}

