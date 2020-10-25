package com.kt.notes.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kt.notes.databinding.NotesRowBinding
import com.kt.notes.model.Note

class NotesRecAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Note, NotesRecAdapter.CustomViewHolder>(
        DiffCallback()
    ) {

    private class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.timeStamp == newItem.timeStamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            NotesRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val note = getItem(position)
        holder.binding.textViewTitle.text = note.title
        holder.binding.textViewDescription.text = note.description
    }

    fun getNoteAtIndex(position: Int): Note {
        return getItem(position)
    }

    inner class CustomViewHolder(val binding: NotesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onNoteItemClick(getItem(position))
                }
            }
            binding.deleteNote.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onNoteItemClick(note: Note)
        fun onDeleteClick(note: Note)
    }
}