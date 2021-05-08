package com.dxn.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dxn.notes.R
import com.dxn.notes.models.entity.Note

class NotesRecyclerAdapter( val onClickListener : (Note) ->Unit ) : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {

    private var allNotes = ArrayList<Note>()

    fun setAllNotes(notes: List<Note>) {
        allNotes = notes as ArrayList<Note>
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.findViewById(R.id.note_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteText.text = allNotes[position].text
        holder.noteText.setOnClickListener { onClickListener(allNotes[position]) }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
}