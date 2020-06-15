package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore

class EntryListViewModel : ViewModel() {

    private val databaseService = DatabaseServiceFirestore()
    val entries = MutableLiveData<List<Entry>>()

    init {
        databaseService.getAllEntries(entries)
    }
}
