package com.mmfsin.noexcuses.presentation.menu.actual

import android.content.Context
import android.os.Build
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
import com.mmfsin.noexcuses.databinding.FragmentChExercisesBinding
import com.mmfsin.noexcuses.domain.models.ActualData
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DatePickerDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter.DefaultExercisesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter.MExercisesAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.DeleteChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.EditChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.utils.BEDROCK_PARCELABLE_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActualExercisesFromMenuFragment :
    BaseFragment<FragmentChExercisesBinding, ActualExercisesFromMenuViewModel>(),
    IDefaultExerciseListener, IMExerciseListener {

    override val viewModel: ActualExercisesFromMenuViewModel by viewModels()

    private lateinit var mContext: Context

    private var data: ActualData? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentChExercisesBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.intent?.getParcelableExtra(BEDROCK_PARCELABLE_ARGS, ActualData::class.java)
        } else {
            @Suppress("DEPRECATION")
            activity?.intent?.getParcelableExtra(BEDROCK_PARCELABLE_ARGS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data?.let { d -> viewModel.getActualDay(d.dayId, d.createdByUser) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply { clAdd.visibility = View.GONE }
    }

    override fun setListeners() {
        binding.apply {
            llRegister.setOnClickListener {
                activity?.let {
                    data?.let { ids ->
                        val calendar = DatePickerDialog { d, m, y ->
                            viewModel.registerDayInCalendar(
                                CalendarInfo(
                                    day = d,
                                    month = m,
                                    year = y,
                                    dayId = ids.dayId,
                                    routineId = ids.routineId
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
                is ActualExercisesFromMenuEvent.GetDay -> {
                    (activity as BedRockActivity).setUpToolbar(title = event.day.title)
                    data?.let { d ->
                        viewModel.getActualDayExercises(d.routineId, d.dayId, d.createdByUser)
                    } ?: run { error() }
                }

                is ActualExercisesFromMenuEvent.GetDayExercises -> setUpExercises(event.exercises)
                is ActualExercisesFromMenuEvent.ExercisesRegisteredInCalendar -> {
                    Toast.makeText(mContext, R.string.calendar_added, Toast.LENGTH_SHORT).show()
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }

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
            llRegister.isVisible = exercises.isNotEmpty()
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
                        dfExercises.toMutableList(),
                        this@ActualExercisesFromMenuFragment
                    )
                }

                else -> return null
            }
        } else return null
    }

    override fun onExerciseClick(chExerciseId: String) {
        activity?.showFragmentDialog(
            ChExerciseDialog.newInstance(
                chExerciseId,
                this@ActualExercisesFromMenuFragment
            )
        )
    }

    override fun editExercise(chExerciseId: String) {
        activity?.showFragmentDialog(
            EditChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun onSeeExerciseButtonClick(id: String) {
        activity?.showFragmentDialog(ExerciseDialog(id))
    }

    override fun seeExerciseButtonClick(id: String) = onSeeExerciseButtonClick(id)

    override fun deleteExerciseFromDay(chExerciseId: String) {
        activity?.showFragmentDialog(
            DeleteChExerciseDialog.newInstance(chExerciseId, this@ActualExercisesFromMenuFragment)
        )
    }

    override fun updateView() {
        data?.let { d -> viewModel.getActualDayExercises(d.routineId, d.dayId, d.createdByUser) }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}