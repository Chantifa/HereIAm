package ch.ffhs.esa.hereiam.screens.entry.form

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentEntryFormBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EntryFormFragment : Fragment() {

    private val viewModel: EntryFormViewModel by viewModels()
    private lateinit var binding: FragmentEntryFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryFormBinding.inflate(inflater)

        binding.viewModel = viewModel

        binding.btnAddPhoto.setOnClickListener {
            val img = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(img, 123)
        }

        binding.btnAddEntry.setOnClickListener {
            addEntry()
        }

        return binding.root
    }

    private fun addEntry() {
        val entryTitle = binding.inputHeadingEntry.text.toString().trim()
        val entryContent = binding.inputTextEntry.text.toString().trim()

        if (!validateUserInput(entryTitle, entryContent)) return

        binding.progressbar.visibility = View.VISIBLE
        // TODO upload image

        CoroutineScope(IO).launch {
            try {
                viewModel.addEntry(entryTitle, entryContent)
                withContext(Main) {
                    clearFields()
                    giveUserFeedback(getString(R.string.entry_successfully_saved))
                }
            } catch (e: Exception) {
                val msg = "Error while saving the entry. Reason: ${e.message}";
                Timber.e(msg)
                withContext(Main) {
                    giveUserFeedback(msg)
                }
            }
        }
    }

    private fun validateUserInput(
        entryTitle: String,
        entryContent: String
    ): Boolean {
        if (entryTitle.isEmpty()) {
            binding.inputHeadingEntry.error = getString(R.string.error_mandatory)
            binding.inputHeadingEntry.requestFocus()
            return false
        }

        if (entryContent.isEmpty()) {
            binding.inputTextEntry.error = getString(R.string.error_mandatory)
            binding.inputTextEntry.requestFocus()
            return false
        }
        return true
    }

    private fun giveUserFeedback(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun clearFields() {
        binding.inputHeadingEntry.text.clear()
        binding.inputTextEntry.text.clear()
        binding.entryPhoto.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            val bmp = data?.getParcelableExtra("data") as Bitmap
            binding.entryPhoto.setImageBitmap(bmp)
            binding.entryPhoto.visibility = View.VISIBLE
            binding.btnAddPhoto.visibility = View.GONE
        }
    }
}

