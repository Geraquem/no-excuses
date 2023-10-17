package com.mmfsin.noexcuses.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemNoteBinding
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.presentation.notes.interfaces.INotesListener

class NotesAdapter(
    private val notes: List<Note>, private val listener: INotesListener
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNoteBinding.bind(view)
        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.itemView.setOnClickListener { listener.onNoteClick(notes[position].id) }
        holder.itemView.setOnLongClickListener {
            listener.onNoteLongClick(notes[position].id)
            true
        }
    }

    override fun getItemCount(): Int = notes.size
}