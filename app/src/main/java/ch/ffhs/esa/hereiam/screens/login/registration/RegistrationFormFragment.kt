package ch.ffhs.esa.hereiam.screens.login.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.databinding.FragmentRegistrationFormBinding

class RegistrationFormFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationFormBinding.inflate(inflater)

        binding.textBackLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationFormFragment_to_nav_profile))

        return binding.root
    }
}