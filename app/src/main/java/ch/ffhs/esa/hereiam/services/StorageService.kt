package ch.ffhs.esa.hereiam.services

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import kotlin.random.Random

interface StorageService {
    fun uploadImage(
        bitmap: Bitmap,
        folder: String,
        fileName: String = getRandomString(20)
    ): LiveData<Uri>

    private fun getRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}

class StorageServiceFirebase : StorageService {
    private val fbStorage = FirebaseStorage.getInstance()

    override fun uploadImage(
        bitmap: Bitmap,
        folder: String,
        fileName: String
    ): MutableLiveData<Uri> {
        val storageReference = fbStorage.reference.child("$folder/$fileName.jpg")
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        val uploadTask = storageReference.putBytes(byteArray)

        val url = MutableLiveData<Uri>()
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            url.value = task.result
        }
        return url
    }
}