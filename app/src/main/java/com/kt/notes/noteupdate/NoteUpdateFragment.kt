package com.kt.notes.noteupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kt.notes.SharedViewModel
import com.kt.notes.databinding.NoteUpdateFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteUpdateFragment : Fragment() {
    private var _binding: NoteUpdateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteUpdateViewModel: NoteUpdateViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NoteUpdateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteUpdateViewModel = ViewModelProvider(this).get(NoteUpdateViewModel::class.java)
        sharedViewModel = ViewModelProvider(activity!!).get(SharedViewModel::class.java)
        setUpUi(savedInstanceState)
        setUpOnClickListeners()
    }

    private fun setUpUi(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.title.setText(sharedViewModel.getNote().title)
            binding.description.setText(sharedViewModel.getNote().description)
        } else {
            binding.title.setText(savedInstanceState.getString("title"))
            binding.description.setText(savedInstanceState.getString("description"))
        }
    }

    private fun setUpOnClickListeners() {
        binding.updateButton.setOnClickListener {
            val note = sharedViewModel.getNote()
            note.title = binding.title.text.toString()
            note.description = binding.description.text.toString()
            noteUpdateViewModel.updateNote(note)
            popFromBackStack()
        }

        binding.cancelButton.setOnClickListener {
            popFromBackStack()
        }
    }

    private fun popFromBackStack() {
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("title", binding.title.text.toString())
        outState.putString("description", binding.description.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}