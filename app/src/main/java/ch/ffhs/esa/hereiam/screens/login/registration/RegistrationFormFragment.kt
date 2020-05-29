package ch.ffhs.esa.hereiam.screens.login.registration

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentRegistrationFormBinding

class RegistrationFormFragment : Fragment() {

    private val viewModel: RegistrationFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationFormBinding.inflate(inflater)

        binding.textBackLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationFormFragment_to_nav_profile))

        binding.btnCreateUser.setOnClickListener {
            val email = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty()) {
                binding.username.error = getString(R.string.error_mandatory)
                binding.username.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.username.error = getString(R.string.error_invalid_email)
                binding.username.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.password.error = getString(R.string.error_min_char_count)
                binding.password.requestFocus()
                return@setOnClickListener
            }

            binding.progressbar.visibility = View.VISIBLE

            viewModel.registerUser(email, password)

            // TODO: wait on registration
            binding.progressbar.visibility = View.GONE
        }

        return binding.root
    }
}