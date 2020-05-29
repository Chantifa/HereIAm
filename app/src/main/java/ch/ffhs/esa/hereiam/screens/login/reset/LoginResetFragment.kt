package ch.ffhs.esa.hereiam.screens.login.reset

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                binding.username.error = "Email Required!"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.username.error = "Valid Email Required!"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            binding.progressbar.visibility = View.VISIBLE

            viewModel.resetPassword(email)

            // TODO: wait on reset
            binding.progressbar.visibility = View.GONE

            
        }

        return binding.root
    }

}