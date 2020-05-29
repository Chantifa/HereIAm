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

        binding.btnAddEntry.setOnClickListener {
            val entryTitle = binding.headingNewEntry.text.toString().trim()
            val entryContent = binding.inputTextEntry.text.toString().trim()

            if (entryTitle.isEmpty()) {
                binding.headingNewEntry.error = getString(R.string.error_mandatory)
                binding.headingNewEntry.requestFocus()
                return@setOnClickListener
            }

            if (entryContent.isEmpty()) {
                binding.inputTextEntry.error = getString(R.string.error_mandatory)
                binding.inputTextEntry.requestFocus()
                return@setOnClickListener
            }

            binding.progressbar.visibility = View.VISIBLE

            viewModel.addEntry(entryTitle, entryContent)

            // TODO: wait on save
            binding.progressbar.visibility = View.GONE
        }
        return binding.root
    }
}
