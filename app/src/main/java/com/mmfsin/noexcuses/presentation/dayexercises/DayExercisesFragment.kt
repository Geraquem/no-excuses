package com.mmfsin.noexcuses.presentation.dayexercises

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.ComboModel
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.dayexercises.DayExercisesFragmentDirections.Companion.actionDayExercisesToMuscularGroups
import com.mmfsin.noexcuses.presentation.dayexercises.adapter.DayExercisesAdapter
import com.mmfsin.noexcuses.presentation.dayexercises.dialogs.deleteday.DeleteDayExerciseDialog
import com.mmfsin.noexcuses.presentation.dayexercises.interfaces.IDayExercisesListener
import com.mmfsin.noexcuses.presentation.detailexercise.DetailExerciseDialog
import com.mmfsin.noexcuses.presentation.phases.dialogs.deletephase.DeletePhaseDialog

class DayExercisesFragment : BaseFragment<FragmentExercisesBinding>(), DayExercisesView,
    IDayExercisesListener {

    private val presenter by lazy { DayExercisesPresenter(this) }

    private var name: String = ""
    private var dayId: String? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentExercisesBinding.inflate(inflater, container, false)

    private fun getBundleArgs() = arguments?.let { bundle ->
        name = bundle.getString("name", "")
        dayId = bundle.getString("dayId")
    }

    override fun setUI() {
        getBundleArgs()
        binding.apply {
            toolbar.title.text = name
            toolbar.iconRight.setImageResource(R.drawable.ic_add)
            toolbar.iconRight.visibility = View.VISIBLE
        }
        dayId?.let { presenter.getDayExercises(it) }
    }

    override fun setListeners() {
        binding.toolbar.apply {
            ivBack.setOnClickListener { activity?.onBackPressed() }
            iconRight.setOnClickListener {
                dayId?.let { dayId ->
                    findNavController().navigate(actionDayExercisesToMuscularGroups(name, dayId))
                }
            }
        }
    }

    override fun getDayExercises(exercises: List<RealmExercise>) {
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = DayExercisesAdapter(exercises, this@DayExercisesFragment)
        }
    }

    override fun onClick(exercise: RealmExercise) {
        val dialog = DetailExerciseDialog(false, exercise)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun deleteDayExercise(exercise: RealmExercise) {
        val combo = presenter.getComboModelByExerciseId(exercise.id)
        val dialog =
            DeleteDayExerciseDialog(exercise.nombre, combo) { presenter.deleteComboModel(it) }
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun sww() {
        Toast.makeText(this@DayExercisesFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }

    override fun dayExerciseDeleted() {
        dayId?.let { presenter.getDayExercises(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}