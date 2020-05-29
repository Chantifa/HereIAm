package ch.ffhs.esa.hereiam.screens.login.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentLoginFormBinding

class LoginFormFragment : Fragment() {

    private val formViewModel: LoginFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginFormBinding.inflate(inflater)

        binding.btnLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_profileFragment))
        binding.textResetPassword.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_loginResetFragment))
        binding.textRegistrationLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_registrationFormFragment))

        return binding.root
    }

}
