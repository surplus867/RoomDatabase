package com.example.roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.roomdatabase.adapter.NoteAdapter
import com.example.roomdatabase.databinding.ActivityUpdateNoteBinding
import com.example.roomdatabase.db.NoteDatabase
import com.example.roomdatabase.db.NoteEntity
import com.example.roomdatabase.repository.DbRepository
import com.example.roomdatabase.utilis.Constants.BUNDLE_NOTE_ID
import com.example.roomdatabase.utilis.Constants.NOTE_DATABASE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding

    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var noteEntity: NoteEntity
    private var noteId = 0
    private var defaultTitle = ""
    private var defaultDesc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            noteId = it.getInt("NOTE_ID")
        }

        binding.apply {
            defaultTitle = repository.getNote(noteId).noteTitle
            defaultDesc =  repository.getNote(noteId).noteDesc

            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)

            btnDelete.setOnClickListener {
                noteEntity = NoteEntity(noteId, defaultTitle, defaultDesc)
                repository.deleteNote(noteEntity)
                finish()
            }

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    noteEntity = NoteEntity(noteId,title,desc)
                    repository.updateNote(noteEntity)
                    finish()
                }
                else {
                    Snackbar.make(it, "Title and Description cannot be Empty", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}