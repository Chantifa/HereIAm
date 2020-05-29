package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EntryListViewModel : ViewModel() {

    //    var listView: ListView? = null
    val entries = MutableLiveData<List<String>>()

    init {
        entries.value = arrayListOf("Entry1", "Entry2", "Entry3")
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