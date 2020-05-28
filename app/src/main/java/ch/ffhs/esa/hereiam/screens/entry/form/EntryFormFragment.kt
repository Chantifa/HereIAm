package ch.ffhs.esa.hereiam.screens.entry.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.ffhs.esa.hereiam.R
import kotlinx.android.synthetic.main.fragment_entry_form.view.*

class EntryFormFragment : Fragment() {

    private val viewModel: EntryFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_form, container, false)

        val heading = view.input_heading_entry
        val text = view.input_text_entry
        val collection_path = getString(R.string.firestore_collection_path)

        view.btn_add_entry.setOnClickListener {
            viewModel.addEntry(heading, text, collection_path)
        }
        return view
    }
}
