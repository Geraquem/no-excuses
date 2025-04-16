package com.mmfsin.noexcuses.presentation.maximums.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.ExercisesEvent
import com.mmfsin.noexcuses.presentation.exercises.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.create.CreateExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.delete.DeleteCreatedExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit.EditCreatedExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit.listeners.IEditCreatedExerciseListener
import com.mmfsin.noexcuses.presentation.exercises.exercises.interfaces.IExercisesListener
import com.mmfsin.noexcuses.presentation.maximums.dialogs.add.AddMaxExerciseDialog
import com.mmfsin.noexcuses.presentation.maximums.dialogs.delete.DeleteMDataDialog
import com.mmfsin.noexcuses.presentation.maximums.listeners.IDialogsMaxExerciseListener
import com.mmfsin.noexcuses.utils.ADD_EXERCISE
import com.mmfsin.noexcuses.utils.MGROUP_ID
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MaxExercisesFragment : BaseFragment<FragmentExercisesBinding, MaxExercisesViewModel>(),
    IExercisesListener, IEditCreatedExerciseListener, IDialogsMaxExerciseListener {

    override val viewModel: MaxExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroup: String? = null
    private var mExercises: MutableList<Exercise>? = null
    private var mAdapter: ExercisesAdapter? = null

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
        (activity as BedRockActivity).setUpToolbar(title = mGroup)
        binding.apply {
            loading.root.isVisible = true
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ExercisesEvent.GetExercises -> setUpExercises(event.exercises, event.newCreated)
                is ExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Exercise>, newCreated: Boolean) {
        mExercises = exercises.toMutableList()
        binding.apply {
            mExercises?.let { list ->
                rvExercises.apply {
                    layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                    mAdapter = ExercisesAdapter(list, this@MaxExercisesFragment)
                    adapter = mAdapter
                }
                mAdapter?.let {
                    if (newCreated) rvExercises.scrollToPosition(it.itemCount - 1)
                }
            }
            loading.root.isVisible = false
        }
    }

    override fun onExerciseClick(id: String) {
        val dialog = if (id == ADD_EXERCISE) {
            mGroup?.let { category ->
                CreateExerciseDialog(category) {
                    viewModel.getExercises(category, newCreated = true)
                }
            }
        } else AddMaxExerciseDialog(id, this@MaxExercisesFragment)
        if (dialog != null) activity?.showFragmentDialog(dialog)
    }

    override fun onExerciseLongClick(id: String) {
        mGroup?.let { category ->
            viewModel.getExercises(category, newCreated = true)
            val dialog = EditCreatedExerciseDialog(id, category, this)
            activity?.showFragmentDialog(dialog)
        }
    }

    override fun editedCreatedExercise() {
        mGroup?.let { category -> viewModel.getExercises(category, newCreated = true) }
    }

    override fun deletedCreatedExercise(id: String) {
        val dialog = DeleteCreatedExerciseDialog(id) {
            mGroup?.let { category -> viewModel.getExercises(category, newCreated = true) }
        }
        activity?.showFragmentDialog(dialog)
    }

    override fun onSeeExerciseClick(exerciseId: String) {
        activity?.showFragmentDialog(ExerciseDialog(exerciseId))
    }

    override fun deleteMData(mDataId: String) {
        activity?.showFragmentDialog(DeleteMDataDialog(mDataId))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}