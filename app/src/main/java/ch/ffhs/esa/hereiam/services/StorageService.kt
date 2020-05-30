package ch.ffhs.esa.hereiam.services

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class StorageService {
    companion object {
        private val fbStorage = FirebaseStorage.getInstance()

        fun uploadImage(
            bitmap: Bitmap,
            baos: ByteArrayOutputStream,
            currentUser: FirebaseUser?
        ): Pair<StorageReference, UploadTask> {
            val storageRef = fbStorage
                .reference
                .child("pics/${currentUser?.uid}")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val image = baos.toByteArray()

            val upload = storageRef.putBytes(image)
            return Pair(storageRef, upload)
        }
    }
}