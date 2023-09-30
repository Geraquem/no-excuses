package com.mmfsin.noexcuses.presentation.routines.days

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentDaysBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.routines.days.DaysFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.routines.mgroups.intefaces.IChMGroupListener
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysFragment : BaseFragment<FragmentDaysBinding, DaysViewModel>(), IChMGroupListener {

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
                    (activity as MainActivity).setUpToolbar(title = event.day.title)
                    viewModel.getDayExercises(event.day.id)
                }
                is DaysEvent.GetDayExercises -> setUpDays(event.exercises)
                is DaysEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDays(exercises: List<CompactExercise>) {
        binding.apply {
//            rvMgroups.apply {
//                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
//                adapter = ChMGroupsAdapter(items, this@ChMGroupsFragment)
//            }
            val a = 2
            rvExercises.isVisible = exercises.isNotEmpty()
            tvEmpty.isVisible = exercises.isEmpty()
        }
    }

    override fun onMGroupClick(mGroup: String) {

    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}