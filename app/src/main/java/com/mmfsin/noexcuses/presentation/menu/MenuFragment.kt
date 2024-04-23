package com.mmfsin.noexcuses.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMenuBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToExercises
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMuscularGroups
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyRoutines
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToNoteDetail
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToNotes
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToRoutines
import com.mmfsin.noexcuses.presentation.menu.adapter.MenuMGroupsAdapter
import com.mmfsin.noexcuses.presentation.menu.dialogs.MenuDaysDialog
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.countDown300
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener {

    override val viewModel: MenuViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkVersion()
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).apply {
                setUpToolbar(false)
                rightIconToolbar(isVisible = false)
                routineOpened = null
            }
            llMyActualRoutine.visibility = View.GONE
            pinnedNote.root.visibility = View.GONE
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnDefaultRoutines.setOnClickListener { navigateTo(actionMenuToRoutines()) }
            btnMyRoutines.setOnClickListener { navigateTo(actionMenuToMyRoutines()) }
            btnExercises.setOnClickListener { navigateTo(actionMenuToMuscularGroups()) }
            btnNotes.setOnClickListener { navigateTo(actionMenuToNotes()) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> viewModel.getMenuItems()
                is MenuEvent.ActualRoutine -> {
                    setUpActualRoutine(event.routine)
                    viewModel.getMuscularGroups()
                }

                is MenuEvent.GetMuscularGroups -> {
                    setUpMuscularGroups(event.mGroups)
                    viewModel.getPinnedNote()
                }

                is MenuEvent.PinnedNote -> setUpPinnedNote(event.note)
                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpActualRoutine(routine: Routine?) {
        binding.apply {
            routine?.let {
                actualRoutine.apply {
                    image.tvNumOfDays.text = routine.days.toString()
                    tvTitle.text = routine.title
                    tvDescription.text = routine.description
                    ivPushpin.setImageResource(R.drawable.ic_pushpin)
                    root.setOnClickListener {
                        val dialog = MenuDaysDialog(routineId = routine.id, this@MenuFragment)
                        activity?.let { dialog.show(it.supportFragmentManager, "") }
                    }
                }
                llMyActualRoutine.visibility = View.VISIBLE
            }
        }
    }

    override fun onMenuDayClick(id: String) {

    }

    private fun setUpMuscularGroups(mGroups: List<MuscularGroup>) {
        binding.rvMuscularGroups.apply {
            layoutManager = LinearLayoutManager(mContext, HORIZONTAL, false)
            adapter = MenuMGroupsAdapter(mGroups, this@MenuFragment)
        }
    }

    override fun onMenuMGroupClick(id: String) = navigateTo(actionMenuToExercises(id))

    private fun setUpPinnedNote(note: Note?) {
        binding.apply {
            note?.let {
                pinnedNote.apply {
                    tvTitle.text = note.title
                    tvDescription.text = note.description
                    tvDate.text = getString(R.string.notes_date, note.date)
                    ivPushpin.setImageResource(R.drawable.ic_pushpin)
                    root.visibility = View.VISIBLE
                    root.setOnClickListener { navigateTo(actionMenuToNoteDetail(note.id)) }
                }
            }
            countDown(750) { loading.root.visibility = View.GONE }
        }
    }

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}