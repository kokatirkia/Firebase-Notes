package com.kt.notes.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.notes.R
import com.kt.notes.SharedViewModel
import com.kt.notes.addnote.AddNoteFragment
import com.kt.notes.databinding.MainFragmentBinding
import com.kt.notes.login.LoginFragment
import com.kt.notes.model.Note
import com.kt.notes.noteupdate.NoteUpdateFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), NotesRecAdapter.OnItemClickListener {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesRecAdapter: NotesRecAdapter
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModel::class.java)
        notesRecAdapter = NotesRecAdapter(this)

        setUpRecyclerView()
        setUpOnClickListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        mainFragmentViewModel.getNotes().observe(viewLifecycleOwner, {
            it.let {
                notesRecAdapter.submitList(it)
            }
        })
    }

    private fun setUpOnClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            attachAddNoteFragment()
        }
    }

    private fun setUpRecyclerView() {
        binding.notesRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.notesRecyclerview.adapter = notesRecAdapter
        swipeToDeleteNote()
    }

    private fun swipeToDeleteNote() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainFragmentViewModel.deleteNote(notesRecAdapter.getNoteAtIndex(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(binding.notesRecyclerview)
    }

    private fun attachAddNoteFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, AddNoteFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun attachUpdateNoteFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, NoteUpdateFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun attachLoginFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, LoginFragment())
            ?.commit()
    }

    override fun onNoteItemClick(note: Note) {
        sharedViewModel.setNote(note)
        attachUpdateNoteFragment()
    }

    override fun onDeleteClick(note: Note) {
        AlertDialog.Builder(activity)
            .setTitle("Do you want to delete this note?")
            .setPositiveButton("delete") { _, _ ->
                mainFragmentViewModel.deleteNote(note)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out) {
            mainFragmentViewModel.signOut()
            attachLoginFragment()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}