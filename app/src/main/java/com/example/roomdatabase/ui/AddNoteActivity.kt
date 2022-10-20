package com.example.roomdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.roomdatabase.databinding.ActivityAddNoteBinding
import com.example.roomdatabase.db.NoteDatabase
import com.example.roomdatabase.db.NoteEntity
import com.example.roomdatabase.repository.DbRepository
import com.example.roomdatabase.utilis.Constants.NOTE_DATABASE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNoteBinding

    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var noteEntity: NoteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    noteEntity = NoteEntity(0, title, desc)
                    repository.saveNote(noteEntity)
                    finish()
                }
                else {
                   Toast.makeText(this@AddNoteActivity, "Title and Desc cannot be Empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}