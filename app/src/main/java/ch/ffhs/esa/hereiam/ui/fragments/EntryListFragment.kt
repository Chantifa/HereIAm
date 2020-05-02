package ch.ffhs.esa.hereiam.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore


class EntryListFragment : Fragment() {

    private var listView: ListView? = null
    private var entries: ArrayList<String> = arrayListOf("Entry1", "Entry2", "Entry3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_entry_list_all, container, false)
        listView = view.findViewById<ListView>(R.id.entry_list)

        FirebaseFirestore.getInstance()
            .collection(getString(R.string.firestore_collection_path))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val entry = document.toObject(Entry::class.java)
                    entries.add(entry.heading)
                    Log.d("Eintrag", entry.toString())
                }

            }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.entry_list_item,
            entries
        )
        listView?.adapter = adapter
    }
}

