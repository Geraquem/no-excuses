package com.mmfsin.noexcuses.presentation.myroutines.mexercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMexercisesBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.MExercisesFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter.MExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
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
                adapter = MExercisesAdapter(
                    exercises.toMutableList(),
                    this@MExercisesFragment
                )
                itemTouchHelper.attachToRecyclerView(this)
            }
            rvExercises.isVisible = exercises.isNotEmpty()
            clEmpty.isVisible = exercises.isEmpty()
        }
    }

    override fun onExerciseClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            ChExerciseDialog.newInstance(
                chExerciseId,
                this@MExercisesFragment
            )
        )
    }

    override fun editExercise(chExerciseId: String) {
        activity?.showFragmentDialog(
            EditChExerciseDialog.newInstance(chExerciseId, this@MExercisesFragment)
        )
    }

    override fun onSeeExerciseButtonClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
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

    private val itemTouchHelper by lazy {
        val simpleItemTouchHelper = object : SimpleCallback(UP or DOWN or START or END, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as MExercisesAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                adapter.swapItems(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                val adapter = recyclerView.adapter as MExercisesAdapter
                val newSortedlist = adapter.getNewSortedList()
                viewModel.moveChExercise(newSortedlist)
            }
        }
        ItemTouchHelper(simpleItemTouchHelper)
    }
}