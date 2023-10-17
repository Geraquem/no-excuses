package com.mmfsin.noexcuses.presentation.routines.days

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
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentDaysBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.routines.days.DaysFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.routines.days.adapter.DayExercisesAdapter
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDayExerciseListener
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysFragment : BaseFragment<FragmentDaysBinding, DaysViewModel>(), IDayExerciseListener {

    override val viewModel: DaysViewModel by viewModels()

    private lateinit var mContext: Context

    private var idGroup: IdGroup? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDaysBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { idGroup = it.getParcelable(ID_GROUP) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idGroup?.let { viewModel.getDay(it.dayId) } ?: run { error() }
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            btnAddExercise.setOnClickListener {
                idGroup?.let { findNavController().navigate(actionMGroupsToExercises(it)) }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DaysEvent.GetDays -> {}
                is DaysEvent.GetDay -> {
                    (activity as MainActivity).setUpToolbar(title = event.day.title, info = true)
                    viewModel.getDayExercises(event.day.id)
                }
                is DaysEvent.GetDayExercises -> setUpDays(event.exercises)
                is DaysEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDays(exercises: List<CompactExercise>) {
        binding.apply {
            rvExercises.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = DayExercisesAdapter(exercises, this@DaysFragment)
            }
            rvExercises.isVisible = exercises.isNotEmpty()
            llEmpty.isVisible = exercises.isEmpty()
        }
    }

    override fun onExerciseClick(chExerciseId: String) {
        activity?.showFragmentDialog(ChExerciseDialog.newInstance(chExerciseId))
    }

    override fun onExerciseLongClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            EditChExerciseDialog.newInstance(chExerciseId, this@DaysFragment)
        )
    }

    override fun deleteExerciseFromDay(chExerciseId: String) {
        activity?.showFragmentDialog(
            DeleteChExerciseDialog.newInstance(chExerciseId, this@DaysFragment)
        )
    }

    override fun updateView() {
        idGroup?.let { viewModel.getDayExercises(it.dayId) } ?: run { error() }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}