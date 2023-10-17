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
import com.mmfsin.noexcuses.utils.checkNotNulls
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
        arguments?.let { noteId = it.getString(ID_NOTE) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteId?.let { id -> viewModel.getNoteById(id) }
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.notes_toolbar))
            checkNotNulls(noteId, note) { id, note ->
                if (id != NO_ID_NOTE) {
                    etTitle.setText(note.title)
                    etDescription.setText(note.description)
                }
            }
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is NoteDetailEvent.GetNote -> {
                    note = event.note
                    setUI()
                }
                is NoteDetailEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}