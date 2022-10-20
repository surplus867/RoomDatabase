package com.example.roomdatabase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.NoteAdapter
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.NoteDatabase
import com.example.roomdatabase.utilis.Constants.NOTE_DATABASE

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val noteDB : NoteDatabase by lazy {
        Room.databaseBuilder(this,NoteDatabase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val noteAdapter by lazy { NoteAdapter() }

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
            if(noteDB.doa().getAllNotes().isNotEmpty()) {
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                noteAdapter.differ.submitList(noteDB.doa().getAllNotes())
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