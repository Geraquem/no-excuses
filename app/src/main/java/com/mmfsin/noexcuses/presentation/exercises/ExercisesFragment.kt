package com.mmfsin.noexcuses.presentation.exercises

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.detailexercise.DetailExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.interfaces.IExercisesListener
import com.mmfsin.noexcuses.presentation.phases.dialogs.newphase.NewPhaseDialog

class ExercisesFragment : BaseFragment<FragmentExercisesBinding>(), ExercisesView,
    IExercisesListener {

    private val presenter by lazy { ExercisesPresenter(this) }

    private var name: String = ""

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentExercisesBinding.inflate(inflater, container, false)

    private fun getBundleArgs() = arguments?.let { bundle -> name = bundle.getString("name", "") }

    override fun setUI() {
        getBundleArgs()
        binding.apply {
            toolbar.title.text = getString(R.string.exercises_toolbar, name)
        }
        presenter.getExercisesByMuscularGroup(name)
    }

    override fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getExecises(realmExercises: List<RealmExercise>) {
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = ExercisesAdapter(realmExercises, this@ExercisesFragment)
        }
    }

    override fun sww() {
        Toast.makeText(this@ExercisesFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(exercise: RealmExercise) {
        val dialog = DetailExerciseDialog(exercise)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}