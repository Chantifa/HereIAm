package ch.ffhs.esa.hereiam.model

// Doesn't work, gets me a
// "A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptExecution"
// all the time

//@Database(entities = [Entry::class], version = 1, exportSchema = false)
//abstract class EntryDatabase : RoomDatabase() {
//    abstract val entryDatabaseDao: EntryDatabaseDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: EntryDatabase? = null
//
//        fun getInstance(context: Context): EntryDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        EntryDatabase::class.java,
//                        "entries"
//                    ).fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}