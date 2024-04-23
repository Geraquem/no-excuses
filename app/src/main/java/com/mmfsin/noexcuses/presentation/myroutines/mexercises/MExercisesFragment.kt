package com.mmfsin.noexcuses.presentation.myroutines.mexercises

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
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMexercisesBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter.MExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.MExercisesFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.getBundleParcelableArgs
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MExercisesFragment : BaseFragment<FragmentMexercisesBinding, MExercisesViewModel>(),
    IMExerciseListener {

    override val viewModel: MExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var idGroup: IdGroup? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMexercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { idGroup = it.getBundleParcelableArgs(ID_GROUP, IdGroup::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idGroup?.let { viewModel.getDay(it.dayId) } ?: run { error() }
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            btnAddExercise.setOnClickListener {
                idGroup?.let { data ->
                    findNavController().navigate(actionMGroupsToExercises(data))
                }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MExercisesEvent.GetDay -> {
                    (activity as BedRockActivity).apply {
                        setUpToolbar(title = event.day.title)
                        rightIconToolbar(isVisible = true,
                            icon = R.drawable.ic_info,
                            action = { supportFragmentManager.showFragmentDialog(InfoDialog()) })
                    }
                    viewModel.getDayExercises(event.day.id)
                }

                is MExercisesEvent.GetDayExercises -> setUpExercises(event.exercises)
                is MExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<CompactExercise>) {
        binding.apply {
            rvExercises.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MExercisesAdapter(exercises, this@MExercisesFragment)
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
            EditChExerciseDialog.newInstance(chExerciseId, this@MExercisesFragment)
        )
    }

    override fun deleteExerciseFromDay(chExerciseId: String) {
        activity?.showFragmentDialog(
            DeleteChExerciseDialog.newInstance(chExerciseId, this@MExercisesFragment)
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