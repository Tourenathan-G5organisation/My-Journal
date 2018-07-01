package com.toure.myjournal.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Toure Nathan on 6/30/2018.
 */

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY note_time DESC")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :noteId")
    List<Note> getNoteWithId(int noteId);

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    List<Note> getAllNotesWithIds(int[] noteIds);

    @Insert
    void insertAll(List<Note> notes);

    @Insert
    void insert(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Note note);

    @Delete
    void delete(Note note);
}
