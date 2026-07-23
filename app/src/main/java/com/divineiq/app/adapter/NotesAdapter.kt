package com.divineiq.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.divineiq.app.model.Note
import com.divineiq.app.databinding.ItemNoteBinding
import java.text.DateFormat
import java.util.Date

/**
 * Displays the user's [Note]s, newest first, with an inline delete action.
 */
class NotesAdapter(
    private val onDeleteClick: (Note) -> Unit
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val onDeleteClick: (Note) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)

        fun bind(note: Note) {
            binding.textNoteTitle.text = note.title
            binding.textNoteContent.text = note.content
            binding.textNoteDate.text = dateFormat.format(Date(note.createdAtMillis))
            binding.buttonDeleteNote.setOnClickListener { onDeleteClick(note) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
        }
    }
}
