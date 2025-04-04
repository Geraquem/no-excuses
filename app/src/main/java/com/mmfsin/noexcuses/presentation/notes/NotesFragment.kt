package com.mmfsin.noexcuses.presentation.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentNotesBinding
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.presentation.notes.adapter.NotesAdapter
import com.mmfsin.noexcuses.presentation.notes.dialogs.DeleteNoteDialog
import com.mmfsin.noexcuses.presentation.notes.interfaces.INotesListener
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.NO_ID_NOTE
import com.mmfsin.noexcuses.utils.ROOT_ACTIVITY_NAV_GRAPH
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import com.mmfsin.noexcuses.utils.updateMenuUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding, NotesViewModel>(), INotesListener {

    override val viewModel: NotesViewModel by viewModels()

    private var mAdapter: NotesAdapter? = null
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentNotesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
    }

    override fun setListeners() {
        binding.apply {
            btnAddNote.setOnClickListener { navigateToDetail(NO_ID_NOTE) }
            btnAddNoteEmpty.setOnClickListener { navigateToDetail(NO_ID_NOTE) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is NotesEvent.GetNotes -> setUpNotes(event.notes)
                is NotesEvent.UpdatePushPin -> {
                    mAdapter?.updatePushPin(event.noteId)
                    activity?.updateMenuUI(mContext)
                }

                is NotesEvent.SWW -> error()
            }
        }
    }

    private fun setUpNotes(notes: List<Note>) {
        binding.apply {
            rvNotes.apply {
                layoutManager = LinearLayoutManager(mContext)
                mAdapter = NotesAdapter(notes, this@NotesFragment)
                adapter = mAdapter
            }
            nsvNotes.isVisible = notes.isNotEmpty()
            tvTitle.isVisible = notes.isNotEmpty()
            btnAddNote.isVisible = notes.isNotEmpty()

            llEmpty.isVisible = notes.isEmpty()
        }
    }

    override fun onNoteClick(id: String) = navigateToDetail(id)

    override fun onNoteLongClick(id: String) {
        activity?.showFragmentDialog(DeleteNoteDialog.newInstance(id, this@NotesFragment))
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(mContext, BedRockActivity::class.java)
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, R.navigation.nav_graph_note_detail)
        intent.putExtra(BEDROCK_STR_ARGS, id)

        activityResultLauncher.launch(intent)
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            activity?.updateMenuUI(mContext)
            viewModel.getNotes()
        }
    }

    override fun updatePinnedNote(id: String) = viewModel.updatePinnedNote(id)

    override fun deletedComplete() {
        viewModel.getNotes()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}