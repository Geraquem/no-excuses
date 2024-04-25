package com.mmfsin.noexcuses.presentation.myroutines.mexercises.actual

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMexercisesBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter.MExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActualExercisesFromMenuFragment :
    BaseFragment<FragmentMexercisesBinding, ActualExercisesFromMenuViewModel>(),
    IMExerciseListener {

    override val viewModel: ActualExercisesFromMenuViewModel by viewModels()

    private lateinit var mContext: Context

    private var dayId: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMexercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        dayId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayId?.let { viewModel.getDay(it) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply { clAdd.visibility = View.GONE }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ActualExercisesFromMenuEvent.GetDay -> {
                    (activity as BedRockActivity).apply {
                        setUpToolbar(title = event.day.title)
                        rightIconToolbar(isVisible = true,
                            icon = R.drawable.ic_info,
                            action = { supportFragmentManager.showFragmentDialog(InfoDialog()) })
                    }
                    viewModel.getDayExercises(event.day.id)
                }

                is ActualExercisesFromMenuEvent.GetDayExercises -> setUpExercises(event.exercises)
                is ActualExercisesFromMenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<CompactExercise>) {
        binding.apply {
            rvExercises.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MExercisesAdapter(exercises, this@ActualExercisesFromMenuFragment)
            }
            rvExercises.isVisible = exercises.isNotEmpty()
            clEmpty.isVisible = exercises.isEmpty()
        }
    }

    override fun onExerciseClick(chExerciseId: String) {
        activity?.showFragmentDialog(ChExerciseDialog.newInstance(chExerciseId))
    }

    override fun onExerciseLongClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            EditChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun deleteExerciseFromDay(chExerciseId: String) {
        activity?.showFragmentDialog(
            DeleteChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun updateView() {
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}