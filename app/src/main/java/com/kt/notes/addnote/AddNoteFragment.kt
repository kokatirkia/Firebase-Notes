package com.kt.notes.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kt.notes.databinding.AddNoteFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddNoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        setUpUi(savedInstanceState)
        setUpOnClickListener()
    }

    private fun setUpUi(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            binding.title.setText(savedInstanceState.getString("title"))
            binding.description.setText(savedInstanceState.getString("description"))
        }
    }

    private fun setUpOnClickListener() {
        binding.saveButton.setOnClickListener {
            addNoteViewModel.addNote(
                binding.title.text.toString(),
                binding.description.text.toString()
            )
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