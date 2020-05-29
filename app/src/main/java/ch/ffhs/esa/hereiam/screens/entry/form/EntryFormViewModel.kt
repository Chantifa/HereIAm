package ch.ffhs.esa.hereiam.screens.entry.form

import android.widget.EditText
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.EntryDatabaseDao
import com.google.firebase.Timestamp

class EntryFormViewModel(private val database: EntryDatabaseDao) : ViewModel() {
    fun addEntry(
        heading: EditText,
        text: EditText,
        collectionPath: String
    ) {
        val headingValue = heading.text.toString()
        val textValue = text.text.toString()
        if (headingValue.isNotEmpty() && textValue.isNotEmpty()) {
            val timestamp = Timestamp.now()
//            FirebaseFirestore.getInstance()
//                .collection(collectionPath)
//                .add(Entry(heading.text.toString(), text.text.toString(), timestamp))
            heading.text.clear()
            text.text.clear()
        }
    }
}