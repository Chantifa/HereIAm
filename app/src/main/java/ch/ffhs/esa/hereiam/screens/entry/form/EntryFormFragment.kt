package ch.ffhs.esa.hereiam.screens.entry.form

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentEntryFormBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val IMAGE_CAPTURE_REQUEST_CODE = 123

class EntryFormFragment : Fragment() {

    private val viewModel: EntryFormViewModel by viewModels()
    private lateinit var binding: FragmentEntryFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryFormBinding.inflate(inflater)

        binding.viewModel = viewModel

        if (viewModel.image.value != null) {
            showImage()
        }

        binding.btnAddPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE)
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

        binding.progressbar.show()

        CoroutineScope(IO).launch {
            try {
                viewModel.uploadImage()
                viewModel.addEntry(entryTitle, entryContent)
                withContext(Main) {
                    clearFields()
                    activity?.toast(getString(R.string.entry_successfully_saved))
                }
            } catch (e: Exception) {
                val msg =
                    getString(R.string.error_while_saving_entry) + getString(R.string.error_reason) + e.message
                Timber.e(msg)
                withContext(Main) {
                    activity?.toast(msg)
                }
            }

            withContext(Main) {
                binding.progressbar.hide()
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

    private fun clearFields() {
        binding.inputHeadingEntry.text.clear()
        binding.inputTextEntry.text.clear()
        binding.entryPhoto.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            viewModel.image.value = data?.getParcelableExtra("data") as Bitmap
            showImage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!HereIAmApplication.userLoggedIn()) {
            activity?.toast(getString(R.string.please_login_first))
            findNavController().navigate(R.id.action_nav_edit_to_nav_profile)
        }
    }

    private fun showImage() {
        binding.entryPhoto.setImageBitmap(viewModel.image.value)
        binding.entryPhoto.visibility = View.VISIBLE
        binding.btnAddPhoto.visibility = View.GONE
    }
}



