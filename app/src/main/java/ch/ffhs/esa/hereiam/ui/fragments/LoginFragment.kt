package ch.ffhs.esa.hereiam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ch.ffhs.esa.hereiam.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val btnLogin = view.btn_login

        btnLogin.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_nav_profile_to_profileFragment))

        return view
    }

}
