package ch.ffhs.esa.hereiam.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ch.ffhs.esa.hereiam.MainActivity

import ch.ffhs.esa.hereiam.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val btnLogin = view.btn_login

        btnLogin.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.replaceFragment(ProfileFragment())
        }

        return view
    }

}
