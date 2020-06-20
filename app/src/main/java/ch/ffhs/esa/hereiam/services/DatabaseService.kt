package ch.ffhs.esa.hereiam.services

import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

interface DatabaseService {
    suspend fun getAllEntries(): List<Entry>
    suspend fun addEntry(entry: Entry)
}

class DatabaseServiceFirestore : DatabaseService {
    private val collection = "entriesV5"
    private val fbFirestore = FirebaseFirestore.getInstance().collection(collection)

    private val sortBy = "entryLastModified"
    private val sortDir = Query.Direction.DESCENDING
    private val limit = 20L

    override suspend fun addEntry(entry: Entry) {
        fbFirestore.add(entry).await()
    }

    override suspend fun getAllEntries(): List<Entry> {
        val list = ArrayList<Entry>()
        val entries = fbFirestore.orderBy(sortBy, sortDir).limit(limit).get().await()
        for (doc in entries) {
            list.add(doc.toObject(Entry::class.java))
        }
        return list
    }
}