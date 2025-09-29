package app.qn.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // CRUD: READ all notes
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    // CRUD: CREATE/UPDATE (using REPLACE strategy)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    // CRUD: UPDATE (explicit)
    @Update
    suspend fun update(note: Note)

    // CRUD: DELETE
    @Delete
    suspend fun delete(note: Note)

    // Read a single note by ID (for editing)
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
}