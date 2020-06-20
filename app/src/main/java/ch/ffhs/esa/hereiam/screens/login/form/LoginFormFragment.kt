package ch.ffhs.esa.hereiam.screens.login.form

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentLoginFormBinding
import ch.ffhs.esa.hereiam.util.hide
import ch.ffhs.esa.hereiam.util.show
import ch.ffhs.esa.hereiam.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginFormFragment : Fragment() {

    private val viewModel: LoginFormViewModel by viewModels()
    private lateinit var binding: FragmentLoginFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginFormBinding.inflate(inflater)

        binding.textRegistrationLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_registrationFormFragment))

        binding.textResetPassword.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_loginResetFragment))

        binding.btnLogin.setOnClickListener {
            login()
        }

        return binding.root
    }

    private fun login() {
        val email = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (!validateUserInput(email, password)) return

        binding.progressbar.show()

        CoroutineScope(IO).launch {
            try {
                viewModel.loginUser(email, password)
                withContext(Main) {
                    activity?.toast(getString(R.string.login_successfully))
                    findNavController().navigate(R.id.nav_profile)
                }
            } catch (e: Exception) {
                val msg =
                    getString(R.string.error_while_logging_in) + getString(R.string.error_reason) + e.message
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

    private fun validateUserInput(email: String, password: String): Boolean {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (HereIAmApplication.userLoggedIn()) {
            findNavController().navigate(R.id.profileFragment)
        }
    }
}
