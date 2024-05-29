package com.mmfsin.noexcuses.presentation.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentFavExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.interfaces.IExercisesListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavExercisesFragment : BaseFragment<FragmentFavExercisesBinding, FavExercisesViewModel>(),
    IExercisesListener {

    override val viewModel: FavExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFavExercisesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavExercises()
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            setUpToolbar(title = getString(R.string.favs_title))
            rightIconToolbar(isVisible = true,
                icon = R.drawable.ic_reload,
                action = { viewModel.getFavExercises() })
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is FavExercisesEvent.GetFavExercises -> setUpFavExercises(event.exercises)
                is FavExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpFavExercises(exercises: List<Exercise>) {
        binding.apply {
            rvFavExercises.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                adapter = ExercisesAdapter(exercises, this@FavExercisesFragment)
            }
            rvFavExercises.isVisible = exercises.isNotEmpty()
            tvFavsEmpty.isVisible = exercises.isEmpty()
        }
    }

    override fun onExerciseClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}