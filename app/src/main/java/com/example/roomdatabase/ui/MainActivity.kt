package com.example.roomdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomdatabase.adapter.NoteAdapter
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.NoteDatabase
import com.example.roomdatabase.db.NoteEntity
import com.example.roomdatabase.repository.DbRepository
import com.example.roomdatabase.utilis.Constants.NOTE_DATABASE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: DbRepository

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var noteEntity: NoteEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
        checkItem()
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if(repository.getAllNotes().isNotEmpty()) {
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                noteAdapter.differ.submitList(repository.getAllNotes())
                setupRecyclerView()

            } else {
                rvNoteList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }
}