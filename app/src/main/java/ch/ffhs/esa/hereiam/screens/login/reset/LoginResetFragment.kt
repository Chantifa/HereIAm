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

class LoginResetFragment : Fragment() {

    private val viewModel: LoginResetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginResetBinding.inflate(inflater)
        
        binding.btnResetPassword.setOnClickListener {
            val email = binding.username.text.toString().trim()

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

            binding.progressbar.visibility = View.VISIBLE

            viewModel.resetPassword(email)

            // TODO: wait on reset
            binding.progressbar.visibility = View.GONE

            findNavController().navigate(R.id.nav_profile)
        }

        return binding.root
    }
}