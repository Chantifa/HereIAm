package ch.ffhs.esa.hereiam.services

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import kotlin.random.Random

interface StorageService {
    suspend fun uploadImageAndSaveUri(
        bitmap: Bitmap,
        folder: String,
        fileName: String = getRandomString(20)
    ): Uri?

    suspend fun uploadImageAndSaveRelativePath(
        bitmap: Bitmap,
        folder: String,
        fileName: String = getRandomString(20)
    ): String?

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

    override suspend fun uploadImageAndSaveUri(
        bitmap: Bitmap,
        folder: String,
        fileName: String
    ): Uri? {
        val fileReference = fbStorage.reference.child("$folder/$fileName.jpg")
        val byteArray = compressBitmapToByteArray(bitmap)
        fileReference.putBytes(byteArray).await()
        return fileReference.downloadUrl.await()
    }

    override suspend fun uploadImageAndSaveRelativePath(
        bitmap: Bitmap,
        folder: String,
        fileName: String
    ): String? {
        val fileReference = fbStorage.reference.child("$folder/$fileName.jpg")
        val byteArray = compressBitmapToByteArray(bitmap)
        fileReference.putBytes(byteArray).await()
        return fileReference.path
    }

    private fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
}