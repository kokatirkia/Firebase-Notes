package com.kt.notes.updatenote.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kt.notes.common.model.Note
import com.kt.notes.updatenote.model.UpdateNoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _updateNoteState: MutableLiveData<UpdateNoteState> =
        MutableLiveData(UpdateNoteState())
    val updateNoteState: LiveData<UpdateNoteState> = _updateNoteState

    fun onTitleTextFieldValueChanged(newValue: String) {
        _updateNoteState.value = _updateNoteState.value!!.copy(title = newValue)
    }

    fun onDescriptionTextFieldValueChanged(newValue: String) {
        _updateNoteState.value = _updateNoteState.value!!.copy(description = newValue)
    }

    fun setInitialTextFieldValues(note: Note) {
        if (_updateNoteState.value!!.title.isEmpty())
            _updateNoteState.value = _updateNoteState.value!!.copy(title = note.title)
        if (_updateNoteState.value!!.description.isEmpty())
            _updateNoteState.value = _updateNoteState.value!!.copy(description = note.description)
    }

    fun updateNote(noteId: String) {
        firestore.collection("notes")
            .document(noteId)
            .update(
                "title", _updateNoteState.value!!.title,
                "description", _updateNoteState.value!!.description
            )
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    fun deleteNote(noteId: String) {
        firestore.collection("notes")
            .document(noteId)
            .delete()
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }
}