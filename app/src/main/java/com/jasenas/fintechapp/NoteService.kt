package com.jasenas.fintechapp

import android.content.Context
import kotlinx.coroutines.flow.Flow

class NoteService(context: Context) {
    private val dao = NoteDatabase.getDatabase(context).noteDao()

    fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes()

    fun validate(title: String, content: String): Int? = when {
        title.isBlank() -> R.string.error_empty_title
        content.isBlank() -> R.string.error_empty_content
        else -> null
    }

    suspend fun addNote(title: String, content: String) {
        dao.insert(Note(title = title, content = content))
    }

    suspend fun deleteNote(note: Note) {
        dao.deleteById(note.id)
    }
}
