package ch.ffhs.esa.hereiam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    var entryId: Long = 0L,
    @ColumnInfo(name = "entry_title")
    var entryTitle: String = "",
    @ColumnInfo(name = "entry_content")
    var entryContent: String = "",
    @ColumnInfo(name = "entry_last_modified")
    var entryLastModified: Timestamp = Timestamp.now(),
    @ColumnInfo(name = "location_name")
    var locationName: String = "",
    @ColumnInfo(name = "location")
    var location: LatLng? = null
) {
}