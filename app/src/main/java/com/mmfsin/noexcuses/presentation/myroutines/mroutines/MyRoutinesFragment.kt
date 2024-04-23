package com.mmfsin.noexcuses.presentation.myroutines.mroutines

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMyRoutinesBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.mdays.DaysDialog
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.MyRoutinesFragmentDirections.Companion.actionRoutinesToMexercises
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.adapter.MyRoutinesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs.MyRoutineAddDialog
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs.MyRoutineDeleteDialog
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs.MyRoutineEditDialog
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.BEDROCK_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import com.mmfsin.noexcuses.utils.updateMenuUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutinesFragment : BaseFragment<FragmentMyRoutinesBinding, MyRoutinesViewModel>(),
    IMyRoutineListener {

    override val viewModel: MyRoutinesViewModel by viewModels()

    private lateinit var mContext: Context

    private var routines = emptyList<Routine>()
    private var openNewRoutineDialog: String? = null

    override fun getBundleArgs() {
        openNewRoutineDialog = activity?.intent?.getStringExtra(BEDROCK_ARGS)
    }

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMyRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFirstTime()
    }

    override fun onResume() {
        super.onResume()
        val routineOpened = (activity as BedRockActivity).routineOpened
        routineOpened?.let { onRoutineClick(it) }
    }

    override fun setUI() {
        binding.apply {
            (activity as BedRockActivity).apply {
                setUpToolbar(title = getString(R.string.my_routines_toolbar))
                rightIconToolbar(isVisible = true,
                    icon = R.drawable.ic_info,
                    action = { supportFragmentManager.showFragmentDialog(InfoDialog()) })
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddRoutine.setOnClickListener { createNewRoutineDialog() }
        }
    }

    private fun createNewRoutineDialog() =
        activity?.showFragmentDialog(MyRoutineAddDialog.newInstance(this@MyRoutinesFragment))

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyRoutinesEvent.IsFistTime -> {
                    firstTimeFlow(event.firstTime)
                    viewModel.getRoutines()
                }

                is MyRoutinesEvent.GetMyRoutines -> {
                    routines = event.routines
                    setUpRoutines(routines)

                    openNewRoutineDialog?.let {
                        createNewRoutineDialog()
                        openNewRoutineDialog = null
                    }
                }

                is MyRoutinesEvent.PushPinUpdated -> {
                    viewModel.getRoutines()
                    activity?.updateMenuUI(mContext)
                }

                is MyRoutinesEvent.SWW -> error()
            }
        }
    }

    private fun firstTimeFlow(firstTime: Boolean) {
        if (firstTime) activity?.showFragmentDialog(InfoDialog())
    }

    private fun setUpRoutines(routines: List<Routine>) {
        binding.apply {
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MyRoutinesAdapter(routines, this@MyRoutinesFragment)
            }
            rvRoutines.isVisible = routines.isNotEmpty()
            tvEmpty.isVisible = routines.isEmpty()
        }
    }

    override fun onRoutineClick(id: String) {
        (activity as BedRockActivity).routineOpened = id
        val dialog = DaysDialog(routineId = id, this@MyRoutinesFragment)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun onRoutineLongClick(id: String) {
        activity?.showFragmentDialog(MyRoutineEditDialog.newInstance(id, this@MyRoutinesFragment))
    }

    override fun onRoutinePushPinClick(id: String) = viewModel.updateMyRoutinePushPin(id)

    override fun dayAddedToRoutine() = updateUI()

    override fun onDayClick(routineId: String, dayId: String) {
        findNavController().navigate(actionRoutinesToMexercises(IdGroup(routineId, dayId)))
    }

    override fun flowCompleted() = updateUI()

    override fun deleteRoutine(id: String) {
        activity?.showFragmentDialog(MyRoutineDeleteDialog.newInstance(id, this@MyRoutinesFragment))
    }

    private fun updateUI() {
        routines = emptyList()
        viewModel.getRoutines()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}