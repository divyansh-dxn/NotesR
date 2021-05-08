package com.dxn.notes.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dxn.notes.R
import com.dxn.notes.adapters.NotesRecyclerAdapter
import com.dxn.notes.models.entity.Note
import com.dxn.notes.viewmodel.NoteViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: NotesRecyclerAdapter
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var fab: FloatingActionButton

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Binding viewa
        recyclerView = findViewById(R.id.recyclerView)
        topAppBar = findViewById(R.id.topAppBar)
        fab = findViewById(R.id.fab)

//        Setting up viewModel with recyclerview
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        mAdapter = NotesRecyclerAdapter {
            MaterialAlertDialogBuilder(this)
                .setTitle("")
                .setMessage(it.text)
                .show()
        }
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        viewModel.allNotes.observe(this, { noteList ->
            Log.v(tag, "data set changed")
            mAdapter.setAllNotes(noteList)
        })

//        Bottom Navigation Bar
        topAppBar.setNavigationOnClickListener {
            Toast.makeText(applicationContext, "Menu button Clicked", Toast.LENGTH_SHORT).show()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    Toast.makeText(applicationContext, "Search button Clicked", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.more -> {
                    Toast.makeText(applicationContext, "More button Clicked", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }

//        Add new note
        fab.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.add_note))
                .setView(R.layout.new_note)
                .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
                .setPositiveButton(resources.getString(R.string.submit)) { dialog, which ->
                    val noteText = (dialog as AlertDialog).findViewById<TextInputEditText>(R.id.note_detail)?.text.toString()
                    if (noteText != "") {
                        viewModel.insertNote(Note(noteText))
                    }
                }
                .show()
        }

//        Delete note by Swipe
        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(applicationContext, "on Move", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val note = viewModel.allNotes.value?.get(pos)
                if (note != null) {
                    viewModel.deleteNote(note)
                    Toast.makeText(applicationContext, "Deleted ${note.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}