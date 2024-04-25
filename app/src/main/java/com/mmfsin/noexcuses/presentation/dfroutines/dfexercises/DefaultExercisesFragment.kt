package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentDefaultExercisesBinding
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter.DefaultExercisesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.dialogs.DfExerciseDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultExercisesFragment :
    BaseFragment<FragmentDefaultExercisesBinding, DefaultExercisesViewModel>(),
    IDefaultExerciseListener {

    override val viewModel: DefaultExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var dayId: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDefaultExercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { dayId = it.getString(DAY_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayId?.let { id -> viewModel.getDefaultDay(id) } ?: run { error() }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DefaultExercisesEvent.GetDefaultDay -> {
                    (activity as BedRockActivity).setUpToolbar(title = event.day.title)
                    viewModel.getDefaultDayExercises(event.day.id)
                }

                is DefaultExercisesEvent.GetDefaultDayExercises -> setUpExercises(event.exercises)
                is DefaultExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<DefaultExercise>) {
        binding.apply {
            rvExercises.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = DefaultExercisesAdapter(exercises, this@DefaultExercisesFragment)
            }
        }
    }

    override fun onDefaultExerciseClick(id: String) {
        activity?.showFragmentDialog(DfExerciseDialog.newInstance(id))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}