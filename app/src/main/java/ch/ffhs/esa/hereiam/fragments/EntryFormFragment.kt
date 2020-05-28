package ch.ffhs.esa.hereiam.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import ch.ffhs.esa.hereiam.R
import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_entry_form.*
import kotlinx.android.synthetic.main.fragment_entry_form.view.*
import kotlinx.android.synthetic.main.fragment_entry_form.view.entry_photo

class EntryFormFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_entry_form, container, false)

        val heading = view.input_heading_entry
        val text = view.input_text_entry

        view.entry_photo.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 123)
        }

        view.btn_add_entry.setOnClickListener {
            val headingValue = heading.text.toString()
            val textValue = text.text.toString()
            //val viewImage = entry_photo.imageAlpha.toDrawable()
            if (headingValue.isNotEmpty() && textValue.isNotEmpty()) {
                val timestamp = com.google.firebase.Timestamp.now()
                FirebaseFirestore.getInstance().collection(getString(R.string.firestore_collection_path))
                    .add(Entry(heading.text.toString(), text.text.toString(), timestamp))
                heading.text.clear()
                text.text.clear()
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123)
        {
            var bmp= data?.getParcelableExtra("data") as Bitmap
            entry_photo.setImageBitmap(bmp)

        }
    }
}
