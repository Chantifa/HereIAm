package ch.ffhs.esa.hereiam.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import ch.ffhs.esa.hereiam.MainActivity
import com.firebase.ui.auth.AuthUI

import ch.ffhs.esa.hereiam.R
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    lateinit var providers : List<AuthUI.IdpConfig>
    val RC_SIGN_IN = 9000

    // Create and launch sign-in intent

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
         providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        showSignInOption()

        //Event
        btn_sign_out.setOnClickListener{
            //signout
            activity?.let { it1 ->
                AuthUI.getInstance().signOut(it1)
                    .addOnCompleteListener{}
                    .addOnFailureListener{ e -> Toast.makeText(activity, e.message,Toast.LENGTH_SHORT).show()
                    }
            }
        }
        return view
    }

    fun showSignInOption() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
                RC_SIGN_IN
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(activity, "" + user!!.email, Toast.LENGTH_SHORT).show()
                    btn_sign_out.isEnabled = true
            } else {
                Toast.makeText(activity,""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Choose authentication providers
}
