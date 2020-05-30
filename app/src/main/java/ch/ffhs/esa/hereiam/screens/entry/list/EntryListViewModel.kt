package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.DatabaseService

class EntryListViewModel : ViewModel() {

    val entries = MutableLiveData<List<Entry>>()

    init {
//        entries.value = arrayListOf(
//            Entry("Titel 1", "Some Content", "Luzern", 5.5, 6.6),
//            Entry("Titel 2", "Some Content", "Luzern", 5.5, 6.6),
//            Entry("Titel 3", "Some Content", "Luzern", 5.5, 6.6),
//            Entry("Titel 4", "Some Content", "Luzern", 5.5, 6.6)
//        )
        DatabaseService.getAllEntries(entries)
    }
}
