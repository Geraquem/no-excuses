package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

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
import com.mmfsin.noexcuses.databinding.FragmentDfRoutinesBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.DefaultDaysSheet
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.DefaultRoutinesFragmentDirections.Companion.actionDefaultRoutinesToDefaultExercises
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.adapter.DefaultRoutinesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.dialogs.AddRoutineToMineDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener
import com.mmfsin.noexcuses.utils.DAYS
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import com.mmfsin.noexcuses.utils.updateMenuUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultRoutinesFragment :
    BaseFragment<FragmentDfRoutinesBinding, DefaultRoutinesViewModel>(),
    IDefaultRoutineListener {

    override val viewModel: DefaultRoutinesViewModel by viewModels()

    private var mAdapter: DefaultRoutinesAdapter? = null
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDfRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDefaultRoutines()
    }

    override fun onResume() {
        super.onResume()
        val routineOpened = (activity as BedRockActivity).routineOpened
        routineOpened?.let { onRoutineClick(it) }
    }

    override fun setUI() {
        binding.apply {
            loading.isVisible = true
            (activity as BedRockActivity).setUpToolbar(title = getString(R.string.routines_toolbar))
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DefaultRoutinesEvent.GetDefaultRoutines -> setUpRoutines(event.defaultRoutines)
                is DefaultRoutinesEvent.PushPinUpdated -> {
                    mAdapter?.updatePushPin(event.routineId)
                    activity?.updateMenuUI(mContext)
                }

                is DefaultRoutinesEvent.SWW -> error()
            }
        }
    }

    private fun setUpRoutines(defaultRoutines: List<Routine>) {
        binding.apply {
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                mAdapter = DefaultRoutinesAdapter(defaultRoutines, this@DefaultRoutinesFragment)
                adapter = mAdapter
            }
            loading.isVisible = false
        }
    }

    override fun onRoutineClick(routineId: String) {
        (activity as BedRockActivity).routineOpened = routineId
        val dialog = DefaultDaysSheet(routineId = routineId, this@DefaultRoutinesFragment)

        val exists = activity?.supportFragmentManager?.findFragmentByTag(DAYS)
        if (exists == null) activity?.let { dialog.show(it.supportFragmentManager, DAYS) }
    }

    override fun onRoutinePushPinClick(routineId: String) =
        viewModel.updateDefaultRoutinePushPin(routineId)

    override fun onDayClick(routineId: String, dayId: String) =
        findNavController().navigate(actionDefaultRoutinesToDefaultExercises(routineId, dayId))

    override fun addToMyRoutines(routineId: String, routineName: String) {
        activity?.showFragmentDialog(AddRoutineToMineDialog.newInstance(routineId, routineName))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}