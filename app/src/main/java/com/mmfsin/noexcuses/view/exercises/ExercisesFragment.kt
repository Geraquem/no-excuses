package com.mmfsin.noexcuses.view.exercises

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.view.category.model.CategoryDTO
import com.mmfsin.noexcuses.view.exercises.adapter.RViewAdapter
import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO
import kotlinx.android.synthetic.main.fragment_exercises.*

class ExercisesFragment(private val listener: IFragment, val category: CategoryDTO) : Fragment(),
    ExercisesView {

    private val presenter by lazy { ExercisesPresenter(this) }

    private lateinit var adapter: RViewAdapter

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loading VISIBLE

        backButton.setOnClickListener { listener.close() }
        toolbarText.text = category.name

        presenter.getExercises(category.name)
    }

    override fun showExercises(list: List<ExerciseDTO>) {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        adapter = RViewAdapter({ listener.openExerciseDetail(it) }, mContext, list)
        recyclerView.adapter = adapter

//        loading.visibility = View.GONE
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}