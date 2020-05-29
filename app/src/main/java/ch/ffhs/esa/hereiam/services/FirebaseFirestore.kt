package ch.ffhs.esa.hereiam.services

import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class FirebaseFirestore {
    companion object {

        private const val collection = "entriesV3"
        private val fbFirestore = FirebaseFirestore.getInstance().collection(collection)

        fun addEntry(heading: String, text: String) {
            fbFirestore.add(Entry(heading, text, "LocationName TODO", 0.0, 0.0))
                .addOnCompleteListener { task ->
                    // TODO: User feedback
                    if (task.isSuccessful) {
                        Timber.i("success")
                    } else {
                        Timber.i("error ${task.exception?.message!!}")
                    }
                }
        }
    }
}