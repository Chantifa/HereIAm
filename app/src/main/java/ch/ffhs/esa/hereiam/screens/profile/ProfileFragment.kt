package ch.ffhs.esa.hereiam.screens.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentProfileBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

const val REQUEST_IMAGE_CAPTURE = 100

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        binding.btnProfileSave.setOnClickListener {
            saveEdits()
        }

        binding.profileAvatar.setOnClickListener {
            takePictureIntent()
        }

        return binding.root
    }

    private fun saveEdits() {

        val name = binding.username.text.toString().trim()

        if (!validateUserInput(name)) return

        binding.progressbar.show()

        CoroutineScope(IO).launch {
            try {
                viewModel.saveUserName(name)
                withContext(Main) {
                    activity?.toast(getString(R.string.username_successfully_saved))
                }
            } catch (e: Exception) {
                val msg =
                    getString(R.string.error_generic_msg) + getString(R.string.error_reason) + e.message
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

    private fun validateUserInput(name: String): Boolean {
        if (name.isEmpty()) {
            binding.username.error = getString(R.string.error_mandatory)
            binding.username.requestFocus()
            return false
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUser()
    }

    private fun loadUser() {
        HereIAmApplication.currentUser.value?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .into(binding.profileAvatar)
            binding.username.setText(user.displayName)
            binding.email.text = user.email
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        binding.progressbarPic.visibility = View.VISIBLE

        CoroutineScope(IO).launch {
            try {
                viewModel.uploadImage(bitmap)
                withContext(Main) {
                    activity?.toast(getString(R.string.profile_picture_successfully_saved))
                    binding.profileAvatar.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                val msg =
                    getString(R.string.error_while_uploading_image) + getString(R.string.error_reason) + e.message
                Timber.e(msg)
                withContext(Main) {
                    activity?.toast(msg)
                }
            }
            withContext(Main) {
                binding.progressbarPic.visibility = View.GONE
            }
        }
    }
}
