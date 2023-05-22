package com.mmfsin.noexcuses.presentation.chooseexercises

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.chooseexercises.adapter.ChExercisesAdapter
import com.mmfsin.noexcuses.presentation.chooseexercises.interfaces.IChExercisesListener
import com.mmfsin.noexcuses.presentation.detailexercise.DetailExerciseDialog

class ChExercisesFragment : BaseFragment<FragmentExercisesBinding>(), ChExercisesView,
    IChExercisesListener {

    private val presenter by lazy { ChExercisesPresenter(this) }

    private var mGroupName: String = ""
    private var dayName: String? = null
    private var dayId: String? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentExercisesBinding.inflate(inflater, container, false)

    private fun getBundleArgs() = arguments?.let { bundle ->
        mGroupName = bundle.getString("mGroupName", "")
        dayName = bundle.getString("dayName", "")
        dayId = bundle.getString("dayId")
    }

    override fun setUI() {
        getBundleArgs()
        binding.apply {
            toolbar.title.text = getString(R.string.exercises_toolbar, mGroupName)
        }
        presenter.getExercisesByMuscularGroup(mGroupName)
    }

    override fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getExercises(realmExercises: List<RealmExercise>) {
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = ChExercisesAdapter(realmExercises, this@ChExercisesFragment)
        }
    }

    override fun sww() {
        Toast.makeText(this@ChExercisesFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(exercise: RealmExercise) {
        val dialog = DetailExerciseDialog(true, exercise, dayName) {
            dayId?.let { dayId -> presenter.saveComoModel(dayId, exercise.id) }
        }
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun savedExerciseInDay(result: Boolean) {
        if (result) {
            Toast.makeText(binding.root.context, "todo guay", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(binding.root.context, "problemas", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}