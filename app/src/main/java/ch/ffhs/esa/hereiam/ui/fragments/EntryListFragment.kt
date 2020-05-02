package ch.ffhs.esa.hereiam.ui.fragments

import android.content.Context
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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


class EntryListFragment : Fragment() {

    private var entries: ArrayList<Entry> = ArrayList<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_entry_list_all, container, false)


        FirebaseFirestore.getInstance()
            .collection(getString(R.string.firestore_collection_path))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val entry = document.toObject(Entry::class.java)
                    entries.add(entry)
                }
            }
        val mListView = view.findViewById<ListView>(R.id.entry_list)
        val context: Context? = activity
        val arrayAdapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.entry_list_item, entries
            )
        }

        mListView.adapter = arrayAdapter
        return view
    }
}

