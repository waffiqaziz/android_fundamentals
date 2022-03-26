package com.dicoding.mynoteapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mynoteapps.Note
import com.dicoding.mynoteapps.R
import com.dicoding.mynoteapps.databinding.ItemNoteBinding
import java.util.*

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) :
  RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

  var listNotes = ArrayList<Note>()
    set(listNotes) {
      if (listNotes.size > 0) {
        this.listNotes.clear()
      }
      this.listNotes.addAll(listNotes)
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    return NoteViewHolder(view)
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.bind(listNotes[position])
  }

  override fun getItemCount() = this.listNotes.size

  inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemNoteBinding.bind(itemView)
    fun bind(note: Note) {
      binding.apply {
        tvItemTitle.text = note.title
        tvItemDate.text = note.date
        tvItemDescription.text = note.description

        // item note on click
        cvItemNote.setOnClickListener {
          onItemClickCallback.onItemClicked(note, adapterPosition)
        }
      }
    }
  }

  fun addItem(note: Note) {
    this.listNotes.add(note)
    notifyItemInserted(this.listNotes.size - 1)
  }

  fun updateItem(position: Int, note: Note) {
    this.listNotes[position] = note
    notifyItemChanged(position, note)
  }

  fun removeItem(position: Int) {
    this.listNotes.removeAt(position)
    notifyItemRemoved(position)
    notifyItemRangeChanged(position, this.listNotes.size)
  }

  interface OnItemClickCallback {
    fun onItemClicked(selectedNote: Note?, position: Int?)
  }
}