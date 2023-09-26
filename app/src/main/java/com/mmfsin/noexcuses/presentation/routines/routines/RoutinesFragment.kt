package com.mmfsin.noexcuses.presentation.routines.routines

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
import com.mmfsin.noexcuses.databinding.FragmentRoutinesBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.routines.days.DaysDialog
import com.mmfsin.noexcuses.presentation.routines.routines.RoutinesFragmentDirections.Companion.actionRoutinesToDays
import com.mmfsin.noexcuses.presentation.routines.routines.adapter.RoutinesAdapter
import com.mmfsin.noexcuses.presentation.routines.routines.dialogs.AddRoutineDialog
import com.mmfsin.noexcuses.presentation.routines.routines.dialogs.DeleteRoutineDialog
import com.mmfsin.noexcuses.presentation.routines.routines.dialogs.EditRoutineDialog
import com.mmfsin.noexcuses.presentation.routines.routines.interfaces.IRoutineDialogListener
import com.mmfsin.noexcuses.presentation.routines.routines.interfaces.IRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutinesFragment : BaseFragment<FragmentRoutinesBinding, RoutinesViewModel>(),
    IRoutineListener, IRoutineDialogListener {

    override val viewModel: RoutinesViewModel by viewModels()

    private lateinit var mContext: Context

    private var routines = emptyList<Routine>()

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRoutines()
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.routines_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddRoutine.setOnClickListener {
                activity?.showFragmentDialog(AddRoutineDialog.newInstance(this@RoutinesFragment))
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoutinesEvent.GetRoutines -> {
                    routines = event.routines
                    setUpRoutines(routines)
                }
                is RoutinesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpRoutines(routines: List<Routine>) {
        binding.apply {
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = RoutinesAdapter(routines, this@RoutinesFragment)
            }
            rvRoutines.isVisible = routines.isNotEmpty()
            tvEmpty.isVisible = routines.isEmpty()
        }
    }

    /** OPENS DAYS DIALOG WHEN CLICK ON ROUTINE */
    override fun onRoutineClick(id: String) {
        activity?.showFragmentDialog(DaysDialog(id, this@RoutinesFragment))
    }

    /** EDIT ROUTINE ON LONG CLICK */
    override fun onRoutineLongClick(id: String) {
        activity?.showFragmentDialog(EditRoutineDialog.newInstance(id, this@RoutinesFragment))
    }

    override fun dayAddedToRoutine() {
        updateUI()
    }

    override fun onDayClick(routineId: String, dayId: String) {
        findNavController().navigate(actionRoutinesToDays(IdGroup(routineId, dayId)))
    }

    /** WHEN ADD/EDIT/DELETE ROUTINE DIALOGS ENDS */
    override fun flowCompleted() {
        updateUI()
    }

    override fun deleteRoutine(id: String) {
        activity?.showFragmentDialog(DeleteRoutineDialog.newInstance(id, this@RoutinesFragment))
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