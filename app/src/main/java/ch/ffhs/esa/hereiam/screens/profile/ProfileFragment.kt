package ch.ffhs.esa.hereiam.screens.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    private val defaultImageUrl = "@drawable/avatar"

    private lateinit var imageUri: Uri
    private val requestImageCapture = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        binding.btnProfileSave.setOnClickListener {
            val currentUser = HereIAmApplication.currentUser
            val photo = when {
                ::imageUri.isInitialized -> imageUri
                currentUser?.photoUrl == null -> Uri.parse(defaultImageUrl)
                else -> currentUser.photoUrl
            }

            val name = binding.username.text.toString().trim()

            if (name.isEmpty()) {
                binding.username.error = getString(R.string.error_mandatory)
                binding.username.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()

            binding.progressbar.show()

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener { task ->
                    binding.progressbar.hide()
                    if (task.isSuccessful) {
                        context?.toast(getString(R.string.message_profile_changed))
                    } else {
                        context?.toast(task.exception?.message!!)
                    }
                }
        }

        binding.profileAvatar.setOnClickListener {
            takePictureIntent()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUser()
    }

    private fun loadUser() {
        HereIAmApplication.currentUser?.let { user ->
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
                startActivityForResult(pictureIntent, requestImageCapture)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestImageCapture && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        binding.progressbarPic.visibility = View.VISIBLE

        CoroutineScope(IO).launch {
            try {
                viewModel.uploadImage(bitmap)
            } catch (e: Exception) {
                val msg = "Error while uploading image. Reason: ${e.message}"
                Timber.e(msg)
                withContext(Main) {
                    activity?.toast(msg)
                }
            }
            withContext(Main) {
                activity?.toast(getString(R.string.profile_picture_successfully_saved))
                binding.profileAvatar.setImageBitmap(bitmap)
                binding.progressbarPic.visibility = View.GONE
            }
        }
    }
}
