package com.mmfsin.noexcuses.presentation.menu

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColorStateList
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMenuBinding
import com.mmfsin.noexcuses.domain.models.ActualData
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.menu.adapter.MenuMuscGroupsAdapter
import com.mmfsin.noexcuses.presentation.menu.dialogs.MenuDaysSheet
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_NOTE
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_ROUTINE
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener
import com.mmfsin.noexcuses.utils.LOCAL_BROADCAST_FILTER
import com.mmfsin.noexcuses.utils.animateY
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    private var pinnedRoutineId: String? = null
    private var pinnedNoteId: String? = null
    private var bodyImage: Boolean = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).apply {
            if (firstTimeAccess) {
                registerLocalBroadcast()
                viewModel.checkVersion()
                firstTimeAccess = false
            } else viewModel.secondCallPinnedRoutine()
        }
    }

    private fun registerLocalBroadcast() {
        val filter = IntentFilter(LOCAL_BROADCAST_FILTER)
        activity?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(myBroadcastReceiver, filter)
        }
    }

    private val myBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.secondCallPinnedRoutine()
        }
    }

    override fun setUI() {
        binding.apply {
            llMyActualRoutine.visibility = View.INVISIBLE
            pinnedNote.root.visibility = View.GONE
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {
        binding.apply {
            actualRoutine.ivPushpin.setOnClickListener {
                showUnpinDialog(UNPIN_ROUTINE)
            }
            pinnedNote.ivPushpin.setOnClickListener {
                showUnpinDialog(UNPIN_NOTE)
            }

            btnDefaultRoutines.setOnClickListener { navigateTo(R.navigation.nav_graph_default_routines) }
            btnMyRoutines.setOnClickListener { navigateTo(R.navigation.nav_graph_my_routines) }
            btnNewRoutine.setOnClickListener {
                navigateTo(
                    navGraph = R.navigation.nav_graph_my_routines,
                    booleanArgs = true
                )
            }
            btnExercises.setOnClickListener { navigateTo(R.navigation.nav_graph_exercises) }
            btnFavs.setOnClickListener { navigateTo(R.navigation.nav_graph_fav_exercises) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.VersionCompleted -> viewModel.getMyActualRoutine()

                is MenuEvent.ActualRoutine -> {
                    pinnedRoutineId = event.routine?.id
                    setActualRoutineFirstTime(event.routine)
                    viewModel.getPinnedNote()
                }

                is MenuEvent.PinnedNote -> {
                    pinnedNoteId = event.note?.id
                    setUpPinnedNote(event.note)
                    viewModel.getBodyImage()
                }

                is MenuEvent.BodyImage -> {
                    bodyImage = event.isWomanImage
                    viewModel.getMuscularGroups()
                }

                is MenuEvent.GetMuscularGroups -> setUpMuscularGroups(event.mGroups)

                is MenuEvent.SecondCallPinnedRoutine -> {
                    setActualRoutineSecondTime(event.routine)
                    viewModel.secondCallPinnedNote()
                }

                is MenuEvent.SecondCallPinnedNote -> {
                    setUpPinnedNote(event.note)
                    viewModel.getMuscularGroups()
                }

                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun showUnpinDialog(type: UnpinType) {
        val id = when (type) {
            UNPIN_ROUTINE -> pinnedRoutineId
            UNPIN_NOTE -> pinnedNoteId
        }
        id?.let { activity?.showFragmentDialog(UnpinDataDialog.newInstance(id, type)) } ?: run {}
    }

    private fun setActualRoutineFirstTime(routine: Routine?) {
        binding.apply {
            routine?.let {
                llMyActualRoutine.animateY(-500f, 1)
                setPinnedRoutine(it)
                countDown(350) {
                    llMyActualRoutine.visibility = View.VISIBLE
                    llMyActualRoutine.animateY(0f, 350).setListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}

                        override fun onAnimationEnd(animation: Animator) {
                            llNonePinned.visibility = View.INVISIBLE
                        }
                    })
                }
            } ?: run {
                llNonePinned.visibility = View.VISIBLE
            }
        }
    }

    private fun setActualRoutineSecondTime(routine: Routine?) {
        binding.apply {
            routine?.let {
                setPinnedRoutine(it)
                llMyActualRoutine.visibility = View.VISIBLE
                llNonePinned.visibility = View.INVISIBLE
            } ?: run {
                llMyActualRoutine.visibility = View.INVISIBLE
                llNonePinned.visibility = View.VISIBLE
            }
        }
    }

    private fun setPinnedRoutine(routine: Routine) {
        binding.apply {
            actualRoutine.apply {
                val color = if (routine.createdByUser) R.color.blue else R.color.dark_green
                image.llMain.backgroundTintList = getColorStateList(mContext, color)
                image.tvNumOfDays.text = routine.days.toString()

                tvTitle.text = routine.name
                val description = routine.description?.let { routine.description }
                    ?: run { getString(R.string.my_routines_no_description) }
                tvDescription.text = description
                ivPushpin.setImageResource(R.drawable.ic_pushpin)
                root.setOnClickListener {
                    val dialog = MenuDaysSheet(
                        routineId = routine.id,
                        createdByUser = routine.createdByUser,
                        this@MenuFragment
                    )
                    activity?.let { dialog.show(it.supportFragmentManager, "") }
                }
            }
        }
    }

    override fun onMenuDayClick(routineId: String, dayId: String, createdByUser: Boolean) {
        (activity as MainActivity).openBedRockActivity(
            navGraph = R.navigation.nav_graph_my_actual_exercises,
            parcelable = ActualData(routineId, dayId, createdByUser)
        )
    }

    private fun setUpMuscularGroups(mGroups: List<MuscularGroup>) {
        binding.apply {
            rvMuscularGroups.apply {
                layoutManager = LinearLayoutManager(mContext, HORIZONTAL, false)
                adapter = MenuMuscGroupsAdapter(mGroups, bodyImage, this@MenuFragment)
            }
            loading.root.visibility = View.GONE
        }
    }

    override fun onMenuMuscGroupClick(muscularGroupId: String) =
        navigateTo(R.navigation.nav_graph_exercises, strArgs = muscularGroupId)

    private fun setUpPinnedNote(note: Note?) {
        binding.apply {
            note?.let {
                pinnedNote.apply {
                    tvTitle.text = note.title
                    tvDescription.text = note.description
                    tvDate.text = getString(R.string.notes_date, note.date)
                    ivPushpin.setImageResource(R.drawable.ic_pushpin)
                    root.visibility = View.VISIBLE
                    root.setOnClickListener {
                        navigateTo(
                            navGraph = R.navigation.nav_graph_note_detail,
                            strArgs = note.id
                        )
                    }
                }
            } ?: run { pinnedNote.root.visibility = View.GONE }
        }
    }

    private fun navigateTo(navGraph: Int, strArgs: String? = null, booleanArgs: Boolean? = null) =
        (activity as MainActivity).openBedRockActivity(
            navGraph = navGraph,
            strArgs = strArgs,
            booleanArgs = booleanArgs
        )

    private fun error() = activity?.showErrorDialog(goBack = false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(myBroadcastReceiver)
    }
}