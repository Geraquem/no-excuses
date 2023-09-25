package com.mmfsin.noexcuses.presentation.exercises.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.interfaces.IExercisesListener
import com.mmfsin.noexcuses.utils.MGROUP_ID
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesFragment : BaseFragment<FragmentExercisesBinding, ExercisesViewModel>(),
    IExercisesListener {

    override val viewModel: ExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroup: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentExercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { mGroup = it.getString(MGROUP_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGroup?.let { viewModel.getExercises(it) } ?: run { error() }
    }

    override fun setUI() {
        (activity as MainActivity).setUpToolbar(title = mGroup)
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ExercisesEvent.GetExercises -> setUpExercises(event.exercises)
                is ExercisesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Exercise>) {
        binding.apply {
            binding.rvExercises.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                adapter = ExercisesAdapter(exercises, this@ExercisesFragment)
            }
        }
    }

    override fun onExerciseClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog.newInstance(id))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}