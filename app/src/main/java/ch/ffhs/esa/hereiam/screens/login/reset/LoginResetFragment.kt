package ch.ffhs.esa.hereiam.screens.login.reset

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentLoginResetBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginResetFragment : Fragment() {

    private val viewModel: LoginResetViewModel by viewModels()
    private lateinit var binding: FragmentLoginResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginResetBinding.inflate(inflater)

        binding.btnResetPassword.setOnClickListener {
            resetPassword()
        }

        return binding.root
    }

    private fun resetPassword() {
        val email = binding.username.text.toString().trim()

        if (!validateUserInput(email)) return

        binding.progressbar.show()

        CoroutineScope(IO).launch {
            try {
                viewModel.resetPassword(email)
                activity?.toast(getString(R.string.reset_password_successfully))
            } catch (e: Exception) {
                val msg = "Error while resetting your account. Reason: ${e.message}"
                Timber.e(msg)
                activity?.toast(msg)
            }

            withContext(Main) {
                binding.progressbar.hide()
                findNavController().navigate(R.id.nav_profile)
            }
        }
    }

    private fun validateUserInput(
        email: String
    ): Boolean {
        if (email.isEmpty()) {
            binding.username.error = getString(R.string.error_mandatory)
            binding.username.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.username.error = getString(R.string.error_invalid_email)
            binding.username.requestFocus()
            return false
        }
        return true
    }
}
