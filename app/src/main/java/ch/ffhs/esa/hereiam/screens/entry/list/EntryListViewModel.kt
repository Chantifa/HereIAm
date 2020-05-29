package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry

class EntryListViewModel : ViewModel() {

    //    var listView: ListView? = null
    val entries = MutableLiveData<List<Entry>>()

    init {
        entries.value = arrayListOf(
            Entry("Titel 1", "Some Content", "Luzern", 5.5, 6.6),
            Entry("Titel 2", "Some Content", "Luzern", 5.5, 6.6),
            Entry("Titel 3", "Some Content", "Luzern", 5.5, 6.6),
            Entry("Titel 4", "Some Content", "Luzern", 5.5, 6.6)
        )
    }


//    fun getEntries(collection_path: String, listView: ListView) {
//        this.listView = listView
//
////        FirebaseFirestore.getInstance()
////            .collection(collection_path)
////            .get()
////            .addOnSuccessListener { result ->
////                for (document in result) {
////                    val entry = document.toObject(Entry::class.java)
////                    entries.add(entry.heading)
////                }
////            }
//    }

//    fun addAdadpter(ctx: Context) {
//        val adapter = ArrayAdapter(
//            ctx,
//            R.layout.entry_list_item,
//            entries
//        )
//        listView?.adapter = adapter
//    }

}