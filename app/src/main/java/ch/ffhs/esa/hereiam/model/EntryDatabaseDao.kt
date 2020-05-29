package ch.ffhs.esa.hereiam.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntryDatabaseDao {

    @Insert
    fun insert(entry: Entry)

    @Update
    fun update(entry: Entry)

    @Query("SELECT * FROM entries WHERE entryId = :key")
    fun get(key: Long): Entry

    @Query("DELETE FROM entries")
    fun deleteAll()

    @Query("SELECT * FROM entries ORDER BY entry_last_modified DESC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries ORDER BY entry_last_modified DESC LIMIT 1")
    fun getLatestEntry(): Entry?
}