package com.dicoding.mynoteappsroom.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mynoteappsroom.database.Note
import com.dicoding.mynoteappsroom.databinding.ItemNoteBinding
import com.dicoding.mynoteappsroom.helper.NoteDiffCallback
import com.dicoding.mynoteappsroom.ui.insert.NoteAddUpdateActivity

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
  private val listNotes = ArrayList<Note>()

  fun setListNotes(listNotes: List<Note>) {
    val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    this.listNotes.clear()
    this.listNotes.addAll(listNotes)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return NoteViewHolder(binding)
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.bind(listNotes[position])
  }

  override fun getItemCount(): Int {
    return listNotes.size
  }

  inner class NoteViewHolder(private val binding: ItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
      with(binding) {
        tvItemTitle.text = note.title
        tvItemDate.text = note.date
        tvItemDescription.text = note.description

        cvItemNote.setOnClickListener {
          val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
          intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
          it.context.startActivity(intent)
        }
      }
    }
  }
}