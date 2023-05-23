package com.mmfsin.noexcuses.presentation.exercises

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentExercisesBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.detailexercise.DetailExerciseDialog
import com.mmfsin.noexcuses.presentation.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.interfaces.IExercisesListener

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
            nothingYet.root.visibility = View.GONE
        }
        presenter.getExercisesByMuscularGroup(name)
    }

    override fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun getExecises(exercises: List<Exercise>) {
        binding.rvExercises.apply {
//            layoutManager = LinearLayoutManager(mContext)
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = ExercisesAdapter(exercises, this@ExercisesFragment)
        }
    }

    override fun sww() {
        Toast.makeText(this@ExercisesFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(exercise: Exercise) {
        val dialog = DetailExerciseDialog(false, exercise)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}