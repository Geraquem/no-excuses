package com.mmfsin.noexcuses.presentation.myroutines.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.adapter.ChExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.AddChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.interfaces.IChExercisesListener
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.getBundleParcelableArgs
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChExercisesFragment : BaseFragment<FragmentExercisesBinding, ChExercisesViewModel>(),
    IChExercisesListener {

    override val viewModel: ChExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var group: IdGroup? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentExercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { group = it.getBundleParcelableArgs(ID_GROUP, IdGroup::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        group?.let { viewModel.getExercises(it.muscularGroup) } ?: run { error() }
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            setUpToolbar(title = group?.muscularGroup)
            rightIconToolbar(isVisible = true,
                icon = R.drawable.ic_info,
                action = { supportFragmentManager.showFragmentDialog(InfoDialog()) })
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExercisesEvent.GetExercises -> setUpExercises(event.exercises)
                is ChExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Exercise>) {
        binding.rvExercises.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = ChExercisesAdapter(exercises, this@ChExercisesFragment)
        }
    }

    override fun onExerciseClick(id: String) {
        group?.let { ids ->
            ids.exerciseId = id
            activity?.showFragmentDialog(AddChExerciseDialog.newInstance(ids, this@ChExercisesFragment))
        }
    }

    override fun seeExercise(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}