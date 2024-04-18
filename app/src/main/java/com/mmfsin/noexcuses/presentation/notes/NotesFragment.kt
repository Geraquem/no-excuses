package com.mmfsin.noexcuses.presentation.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentNotesBinding
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.presentation.notes.NotesFragmentDirections.Companion.actionNotesToNoteDetail
import com.mmfsin.noexcuses.presentation.notes.adapter.NotesAdapter
import com.mmfsin.noexcuses.presentation.notes.dialogs.DeleteNoteDialog
import com.mmfsin.noexcuses.presentation.notes.interfaces.INotesListener
import com.mmfsin.noexcuses.utils.NO_ID_NOTE
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding, NotesViewModel>(), INotesListener {

    override val viewModel: NotesViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentNotesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.notes_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddNote.setOnClickListener { navigateToDetail(NO_ID_NOTE) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is NotesEvent.GetNotes -> setUpNotes(event.notes)
                is NotesEvent.SWW -> error()
            }
        }
    }

    private fun setUpNotes(notes: List<Note>) {
        binding.apply {
            rvNotes.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = NotesAdapter(notes, this@NotesFragment)
            }
            rvNotes.isVisible = notes.isNotEmpty()
            tvEmpty.isVisible = notes.isEmpty()
        }
    }

    override fun onNoteClick(id: String) = navigateToDetail(id)

    override fun onNoteLongClick(id: String) {
        activity?.showFragmentDialog(DeleteNoteDialog.newInstance(id, this@NotesFragment))
    }

    private fun navigateToDetail(id: String) {
        findNavController().navigate(actionNotesToNoteDetail(id))
    }

    override fun deletedComplete() {
        viewModel.getNotes()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}