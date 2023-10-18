package com.mmfsin.noexcuses.presentation.myroutines.routines

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
import com.mmfsin.noexcuses.databinding.FragmentMyRoutinesBinding
import com.mmfsin.noexcuses.domain.models.MyRoutine
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.days.DaysDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.MyRoutinesFragmentDirections.Companion.actionRoutinesToDays
import com.mmfsin.noexcuses.presentation.myroutines.routines.adapter.MyRoutinesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs.AddMyRoutineDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs.DeleteMyRoutineDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs.EditMyRoutineDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs.InitInfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineDialogListener
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutinesFragment : BaseFragment<FragmentMyRoutinesBinding, MyRoutinesViewModel>(),
    IMyRoutineListener, IMyRoutineDialogListener {

    override val viewModel: MyRoutinesViewModel by viewModels()

    private lateinit var mContext: Context

    private var myRoutines = emptyList<MyRoutine>()

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMyRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFirstTime()
    }

    override fun onResume() {
        super.onResume()
        val routineOpened = (activity as MainActivity).routineOpened
        routineOpened?.let { onRoutineClick(it) }
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(
                title = getString(R.string.routines_toolbar),
                info = true
            )
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddRoutine.setOnClickListener {
                activity?.showFragmentDialog(AddMyRoutineDialog.newInstance(this@MyRoutinesFragment))
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyRoutinesEvent.IsFistTime -> firstTimeFlow(event.firstTime)
                is MyRoutinesEvent.GetMyRoutines -> {
                    myRoutines = event.myRoutines
                    setUpRoutines(myRoutines)
                }
                is MyRoutinesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun firstTimeFlow(firstTime: Boolean) {
        if (firstTime) activity?.showFragmentDialog(InitInfoDialog.newInstance())
        viewModel.getRoutines()
    }

    private fun setUpRoutines(myRoutines: List<MyRoutine>) {
        binding.apply {
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MyRoutinesAdapter(myRoutines, this@MyRoutinesFragment)
            }
            rvRoutines.isVisible = myRoutines.isNotEmpty()
            tvEmpty.isVisible = myRoutines.isEmpty()
        }
    }

    /** OPENS DAYS DIALOG WHEN CLICK ON ROUTINE */
    override fun onRoutineClick(id: String) {
        (activity as MainActivity).routineOpened = id
        activity?.showFragmentDialog(DaysDialog(id, this@MyRoutinesFragment))
    }

    /** EDIT ROUTINE ON LONG CLICK */
    override fun onRoutineLongClick(id: String) {
        activity?.showFragmentDialog(EditMyRoutineDialog.newInstance(id, this@MyRoutinesFragment))
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
        activity?.showFragmentDialog(DeleteMyRoutineDialog.newInstance(id, this@MyRoutinesFragment))
    }

    private fun updateUI() {
        myRoutines = emptyList()
        viewModel.getRoutines()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}