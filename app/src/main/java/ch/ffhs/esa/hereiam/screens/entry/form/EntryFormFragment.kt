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
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel

class EntryFormFragment : Fragment() {

    private val viewModel: EntryFormViewModel by viewModels()
    private lateinit var binding: FragmentEntryFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryFormBinding.inflate(inflater)

        binding.btnAddPhoto.setOnClickListener {
            val img = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(img, 123)
        }

        val location = binding.locationEntry
        location.setText(HomeViewModel.locationName.toString()).toString()

        binding.btnAddEntry.setOnClickListener {
            val entryTitle = binding.inputHeadingEntry.text.toString().trim()
            val entryContent = binding.inputTextEntry.text.toString().trim()

            if (entryTitle.isEmpty()) {
                binding.inputHeadingEntry.error = getString(R.string.error_mandatory)
                binding.inputHeadingEntry.requestFocus()
                return@setOnClickListener
            }

            if (entryContent.isEmpty()) {
                binding.inputTextEntry.error = getString(R.string.error_mandatory)
                binding.inputTextEntry.requestFocus()
                return@setOnClickListener
            }

            binding.progressbar.visibility = View.VISIBLE

            // TODO upload image
            viewModel.addEntry(entryTitle, entryContent)
            Toast.makeText(activity, "Beitrag wurde erfolgreich hinzugefügt!", Toast.LENGTH_LONG).show()


            // TODO: wait on save
            binding.progressbar.visibility = View.GONE
        }

        return binding.root
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

