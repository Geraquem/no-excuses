package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentDfExercisesBinding
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DatePickerDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter.DefaultExercisesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import com.mmfsin.noexcuses.utils.checkNotNulls
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultExercisesFragment :
    BaseFragment<FragmentDfExercisesBinding, DefaultExercisesViewModel>(),
    IDefaultExerciseListener {

    override val viewModel: DefaultExercisesViewModel by viewModels()

    private lateinit var mContext: Context

    private var routineId: String? = null
    private var dayId: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDfExercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let {
            routineId = it.getString(ROUTINE_ID)
            dayId = it.getString(DAY_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayId?.let { id -> viewModel.getDefaultDay(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {
            llRegisterDf.isVisible = false
            nvExercises.isVisible = false
        }
    }

    override fun setListeners() {
        binding.apply {
            llRegisterDf.setOnClickListener {
                activity?.let {
                    checkNotNulls(dayId, routineId) { dId, rId ->
                        val calendar = DatePickerDialog { d, m, y ->
                            viewModel.registerDayInCalendar(
                                CalendarInfo(
                                    day = d,
                                    month = m,
                                    year = y,
                                    dayId = dId,
                                    routineId = rId
                                )
                            )
                        }
                        calendar.show(it.supportFragmentManager, "")
                    }
                }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DefaultExercisesEvent.GetDefaultDay -> {
                    (activity as BedRockActivity).setUpToolbar(title = event.day.title)
                    checkNotNulls(routineId, dayId) { r, d ->
                        viewModel.getDefaultDayExercises(r, d)
                    }
                }

                is DefaultExercisesEvent.GetDefaultDayExercises -> setUpExercises(event.exercises)
                is DefaultExercisesEvent.ExercisesRegisteredInCalendar -> {
                    Toast.makeText(mContext, R.string.calendar_added, Toast.LENGTH_SHORT).show()
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }

                is DefaultExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<DefaultExercise>) {
        binding.apply {
            rvExercises.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = DefaultExercisesAdapter(exercises, this@DefaultExercisesFragment)
            }
            nvExercises.isVisible = true
            llRegisterDf.isVisible = true
            loading.isVisible = false
        }
    }

    override fun seeExerciseButtonClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}