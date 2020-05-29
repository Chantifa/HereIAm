package ch.ffhs.esa.hereiam.screens.entry.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentEntryFormBinding

class EntryFormFragment : Fragment() {

    private val viewModel: EntryFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEntryFormBinding.inflate(inflater)

        val heading = binding.inputHeadingEntry
        val text = binding.inputTextEntry
        val collectionPath = getString(R.string.firestore_collection_path)

        binding.btnAddEntry.setOnClickListener {
            viewModel.addEntry(heading, text, collectionPath)
        }
        return binding.root
    }
}
