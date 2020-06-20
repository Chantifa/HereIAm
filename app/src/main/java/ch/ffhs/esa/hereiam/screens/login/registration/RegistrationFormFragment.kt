package ch.ffhs.esa.hereiam.screens.login.registration

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentRegistrationFormBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class RegistrationFormFragment : Fragment() {

    private val viewModel: RegistrationFormViewModel by viewModels()
    private lateinit var binding: FragmentRegistrationFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationFormBinding.inflate(inflater)

        binding.textBackLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationFormFragment_to_nav_profile))

        binding.btnCreateUser.setOnClickListener {
            createUser()
        }

        return binding.root
    }

    private fun createUser() {
        val email = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (!validateUserInput(email, binding, password)) return

        binding.progressbar.show()

        CoroutineScope(IO).launch {
            try {
                viewModel.registerUser(email, password)
                withContext(Main) {
                    activity?.toast(getString(R.string.registration_successfully))
                    findNavController().navigate(R.id.nav_profile)
                }
            } catch (e: Exception) {
                val msg =
                    getString(R.string.error_while_register_user) + getString(R.string.error_reason) + e.message
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
        email: String,
        binding: FragmentRegistrationFormBinding,
        password: String
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

        if (password.isEmpty() || password.length < 6) {
            binding.password.error = getString(R.string.error_min_char_count)
            binding.password.requestFocus()
            return false
        }
        return true
    }
}