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
    private val notes: List<Note>,
    private val listener: INotesListener
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNoteBinding.bind(view)
        private val c = binding.root.context
        fun bind(note: Note, listener: INotesListener) {
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvDate.text = c.getString(R.string.notes_date, note.date)

                val pushPinIcon = if (note.pinned) R.drawable.ic_pushpin
                else R.drawable.ic_pushpin_off
                ivPushpin.setImageResource(pushPinIcon)

                ivPushpin.setOnClickListener { listener.updatePinnedNote(note.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, listener)
        holder.itemView.setOnClickListener { listener.onNoteClick(note.id) }
        holder.itemView.setOnLongClickListener {
            listener.onNoteLongClick(note.id)
            true
        }
    }

    override fun getItemCount(): Int = notes.size
}