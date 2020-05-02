package ch.ffhs.esa.hereiam.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_entry_form.view.*

class EntryFormFragment : Fragment() {

    private lateinit var FIRESTORE_COLLECTION_PATH: String


    companion object {
        @JvmStatic
        fun newInstance(FIRESTORE_COLLECTION_PATH: String) = EntryFormFragment().apply {
            arguments = Bundle().apply {
                putString("FIRESTORE_COLLECTION_PATH", FIRESTORE_COLLECTION_PATH)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_entry_form, container, false)

        val heading = view.input_heading_entry
        val text = view.input_text_entry

        view.btn_add_entry.setOnClickListener {
            val headingValue = heading.text.toString()
            val textValue = text.text.toString()
            if (headingValue.isNotEmpty() && textValue.isNotEmpty()) {
                FirebaseFirestore.getInstance().collection(FIRESTORE_COLLECTION_PATH)
                    .add(Entry(heading.text.toString(), text.text.toString()))
                heading.text.clear()
                text.text.clear()
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("FIRESTORE_COLLECTION_PATH")?.let {
            FIRESTORE_COLLECTION_PATH = it
        }
    }


}
