package ch.ffhs.esa.hereiam.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ch.ffhs.esa.hereiam.MainActivity

import ch.ffhs.esa.hereiam.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        val btnLogin: Button = view.findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val mainActivity: MainActivity = activity as MainActivity
            mainActivity.replaceFragment(ProfileFragment())
        }

        return view
    }

}
