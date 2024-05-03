package com.mmfsin.noexcuses.presentation.notes.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemDeleteBinding
import com.mmfsin.noexcuses.presentation.notes.interfaces.INotesListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteNoteDialog(private val noteId: String, private val listener: INotesListener) :
    BaseDialog<DialogItemDeleteBinding>() {

    private val viewModel: DeleteNoteViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogItemDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getNoteById(noteId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvTitle.text = getString(R.string.notes_delete_top_text)
            tvAlert.visibility = View.GONE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnCancel.setOnClickListener { dismiss() }
            btnDelete.setOnClickListener { viewModel.deleteNote(noteId) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DeleteNoteEvent.GetNote -> {
                    binding.tvText.text = getString(R.string.notes_delete_text, event.note.title)
                }

                is DeleteNoteEvent.DeletedCompleted -> {
                    listener.deletedComplete()
                    dismiss()
                }

                is DeleteNoteEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(noteId: String, listener: INotesListener): DeleteNoteDialog {
            return DeleteNoteDialog(noteId, listener)
        }
    }
}