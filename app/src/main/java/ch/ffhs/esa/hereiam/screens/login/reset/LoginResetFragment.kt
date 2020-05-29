package ch.ffhs.esa.hereiam.screens.login.reset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.databinding.FragmentLoginResetBinding

class LoginResetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginResetBinding.inflate(inflater)

        return binding.root
    }

}