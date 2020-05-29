package ch.ffhs.esa.hereiam.screens.entry.form

import android.widget.EditText
import androidx.lifecycle.ViewModel

class EntryFormViewModel : ViewModel() {
    fun addEntry(
        heading: EditText,
        text: EditText
    ) {
        val headingValue = heading.text.toString()
        val textValue = text.text.toString()
        if (headingValue.isNotEmpty() && textValue.isNotEmpty()) {
            heading.text.clear()
            text.text.clear()
        }
    }
}