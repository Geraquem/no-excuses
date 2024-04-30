package com.mmfsin.noexcuses.presentation.menu.actual

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMexercisesBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter.DefaultExercisesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.dialogs.DfExerciseDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter.MExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.checkNotNulls
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActualExercisesFromMenuFragment :
    BaseFragment<FragmentMexercisesBinding, ActualExercisesFromMenuViewModel>(),
    IDefaultExerciseListener, IMExerciseListener {

    override val viewModel: ActualExercisesFromMenuViewModel by viewModels()

    private lateinit var mContext: Context

    private var dayId: String? = null
    private var createdByUser: Boolean? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMexercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        dayId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
        createdByUser = activity?.intent?.getBooleanExtra(BEDROCK_BOOLEAN_ARGS, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNotNulls(dayId, createdByUser) { id, byUser ->
            viewModel.getActualDay(id, byUser)
        } ?: run { error() }
    }

    override fun setUI() {
        binding.apply { clAdd.visibility = View.GONE }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ActualExercisesFromMenuEvent.GetDay -> {
                    (activity as BedRockActivity).setUpToolbar(title = event.day.title)
                    createdByUser?.let { byUser ->
                        viewModel.getActualDayExercises(event.day.id, byUser)
                    }
                }

                is ActualExercisesFromMenuEvent.GetDayExercises -> setUpExercises(event.exercises)
                is ActualExercisesFromMenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Any>) {
        /** Get Adapter */
        val customAdapter = getAdapter(exercises)
        binding.apply {
            /** Setup Recycler */
            if (customAdapter is DefaultExercisesAdapter) {
                rvExercises.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = customAdapter
                }
            } else if (customAdapter is MExercisesAdapter) {
                rvExercises.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = customAdapter
                }
            }

            rvExercises.isVisible = exercises.isNotEmpty()
            clEmpty.isVisible = exercises.isEmpty()
        }
    }

    private fun getAdapter(exercises: List<Any>): Any? {
        if (exercises.isNotEmpty()) {
            when (exercises.first()) {
                is DefaultExercise -> {
                    val dfExercises = exercises.mapNotNull { it as? DefaultExercise }
                    return DefaultExercisesAdapter(
                        dfExercises,
                        this@ActualExercisesFromMenuFragment
                    )
                }

                is CompactExercise -> {
                    val dfExercises = exercises.mapNotNull { it as? CompactExercise }
                    return MExercisesAdapter(
                        dfExercises,
                        this@ActualExercisesFromMenuFragment
                    )
                }

                else -> return null
            }
        } else return null
    }

    override fun onDefaultExerciseClick(id: String) {
        activity?.showFragmentDialog(DfExerciseDialog.newInstance(id))
    }

    override fun onExerciseClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            ChExerciseDialog.newInstance(
                chExerciseId,
                this@ActualExercisesFromMenuFragment
            )
        )
    }

    override fun onExerciseLongClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            EditChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun onSeeExerciseButtonClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    override fun deleteExerciseFromDay(chExerciseId: String) {
        activity?.showFragmentDialog(
            DeleteChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun updateView() {
        checkNotNulls(dayId, createdByUser) { id, byUser ->
            viewModel.getActualDayExercises(id, byUser)
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}