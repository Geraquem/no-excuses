package com.mmfsin.noexcuses.presentation.myroutines.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.create.CreateExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.delete.DeleteCreatedExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit.EditCreatedExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit.listeners.IEditCreatedExerciseListener
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.adapter.ChExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.AddChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.interfaces.IChExercisesListener
import com.mmfsin.noexcuses.presentation.myroutines.snackbar.CustomSnackbar
import com.mmfsin.noexcuses.utils.ADD_EXERCISE
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.getBundleParcelableArgs
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChExercisesFragment : BaseFragment<FragmentExercisesBinding, ChExercisesViewModel>(),
    IChExercisesListener, IEditCreatedExerciseListener {

    override val viewModel: ChExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var mAdapter: ChExercisesAdapter? = null
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
        binding.apply {
            loading.root.isVisible = true
        }
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
                is ChExercisesEvent.GetExercises -> {
                    setUpExercises(event.exercises, event.newCreated)
                }

                is ChExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Exercise>, newCreated: Boolean) {
        binding.apply {
            rvExercises.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                mAdapter = ChExercisesAdapter(exercises, this@ChExercisesFragment)
                adapter = mAdapter
            }
            mAdapter?.let {
                if (newCreated) rvExercises.scrollToPosition(it.itemCount - 1)
            }
            loading.root.isVisible = false
        }
    }

    override fun onExerciseClick(id: String) {
        if (id == ADD_EXERCISE) {
            group?.muscularGroup?.let { category ->
                val dialog = CreateExerciseDialog(category) {
                    viewModel.getExercises(category, newCreated = true)
                }
                activity?.showFragmentDialog(dialog)
            }
        } else {
            group?.let { ids ->
                ids.exerciseId = id
                val dialog = AddChExerciseDialog.newInstance(
                    ids,
                    this@ChExercisesFragment
                )
                activity?.showFragmentDialog(dialog)
            }
        }
    }

    override fun onExerciseLongClick(id: String) {
        /** sólo si lo ha creado el user */
        group?.muscularGroup?.let { category ->
            viewModel.getExercises(category, newCreated = true)
            val dialog = EditCreatedExerciseDialog(id, category, this)
            activity?.showFragmentDialog(dialog)
        }
    }

    override fun seeExercise(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    override fun showSnackBar() {
        CustomSnackbar.make(binding.clMain, Snackbar.LENGTH_SHORT).show()
    }

    override fun editedCreatedExercise() {
        group?.muscularGroup?.let { category ->
            viewModel.getExercises(category, newCreated = true)
        }
    }

    override fun deletedCreatedExercise(id: String) {
        val dialog = DeleteCreatedExerciseDialog(id) {
            group?.muscularGroup?.let { category ->
                viewModel.getExercises(category, newCreated = true)
            }
        }
        activity?.showFragmentDialog(dialog)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}