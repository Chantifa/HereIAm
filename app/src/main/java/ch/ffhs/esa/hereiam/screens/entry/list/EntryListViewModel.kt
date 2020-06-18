package ch.ffhs.esa.hereiam.screens.entry.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.DatabaseService
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class EntryListViewModel : ViewModel() {

    private val databaseService: DatabaseService = DatabaseServiceFirestore()
    val entries = MutableLiveData<List<Entry>>()

    init {
        CoroutineScope(IO).launch {
            try {
                entries.value = databaseService.getAllEntries()
            } catch (e: Exception) {
                Timber.e("Error while loading all Entries: ${e.message}")
            }
        }
    }
}
