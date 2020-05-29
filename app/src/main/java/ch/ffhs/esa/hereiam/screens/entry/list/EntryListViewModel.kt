package ch.ffhs.esa.hereiam.screens.entry.list

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.R

class EntryListViewModel : ViewModel() {

    var listView: ListView? = null
    private var entries: ArrayList<String> = arrayListOf("Entry1", "Entry2", "Entry3")

    fun getEntries(collection_path: String, listView: ListView) {
        this.listView = listView

//        FirebaseFirestore.getInstance()
//            .collection(collection_path)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val entry = document.toObject(Entry::class.java)
//                    entries.add(entry.heading)
//                }
//            }
    }

    fun addAdadpter(ctx: Context) {
        val adapter = ArrayAdapter(
            ctx,
            R.layout.entry_list_item,
            entries
        )
        listView?.adapter = adapter
    }

}