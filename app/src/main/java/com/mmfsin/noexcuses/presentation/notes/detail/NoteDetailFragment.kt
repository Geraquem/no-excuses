package com.mmfsin.noexcuses.presentation.notes.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentNoteDetailBinding
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.utils.ID_NOTE
import com.mmfsin.noexcuses.utils.NO_ID_NOTE
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding, NoteDetailViewModel>() {

    override val viewModel: NoteDetailViewModel by viewModels()

    private lateinit var mContext: Context

    private var noteId: String? = null
    private var note: Note? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentNoteDetailBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { noteId = it.getString(ID_NOTE) } ?: run { error() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteId?.let { id -> if (id != NO_ID_NOTE) viewModel.getNoteById(id) }
    }

    override fun setUI() {
        setUpToolbar()
        binding.apply {
            note?.let {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
            }
        }
    }

    private fun setUpToolbar() {
        (activity as MainActivity).apply {
            setUpToolbar(title = getString(R.string.notes_toolbar))
            rightIconToolbar(isVisible = true,
                icon = R.drawable.ic_check,
                action = { addNote() })
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is NoteDetailEvent.GetNote -> {
                    note = event.note
                    setUI()
                }

                is NoteDetailEvent.NoteCreated -> activity?.onBackPressedDispatcher?.onBackPressed()
                is NoteDetailEvent.SWW -> error()
            }
        }
    }

    private fun addNote() {
        binding.apply {
            viewModel.addNote(
                noteId,
                etTitle.text.toString(),
                etDescription.text.toString(),
                System.currentTimeMillis()
            )
        }
    }

    override fun onStop() {
        addNote()
        super.onStop()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}