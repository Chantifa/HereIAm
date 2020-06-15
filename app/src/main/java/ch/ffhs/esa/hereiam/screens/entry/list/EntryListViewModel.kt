package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.DatabaseService

class EntryListViewModel : ViewModel() {

    val entries = MutableLiveData<List<Entry>>()

    init {
        DatabaseService.getAllEntries(entries)
    }
}
