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

class LoginFormFragment : Fragment() {

    private val viewModel: LoginFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginFormBinding.inflate(inflater)

        binding.textRegistrationLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_registrationFormFragment))

        binding.textResetPassword.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_loginResetFragment))

        binding.btnLogin.setOnClickListener {
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

            binding.progressbar.show()

            viewModel.loginUser(email, password)

            // TODO: wait on login
            binding.progressbar.hide()

            findNavController().navigate(R.id.profileFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (HereIAmApplication.userLoggedIn()) {
            findNavController().navigate(R.id.profileFragment)
        }
    }

}
