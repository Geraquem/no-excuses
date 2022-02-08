package com.mmfsin.noexcuses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.view.category.CategoryFragment
import com.mmfsin.noexcuses.view.category.model.CategoryDTO
import com.mmfsin.noexcuses.view.exercisedetail.ExerciseDetailFragment
import com.mmfsin.noexcuses.view.exercises.ExercisesFragment
import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exercises.setOnClickListener {
            supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_container_view, CategoryFragment(this)).commit()
        }

        tables.setOnClickListener {}

        material.setOnClickListener {}

    }

    override fun openExercises(category: CategoryDTO) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, ExercisesFragment(this, category))
            .addToBackStack(null)
            .commit()
    }

    override fun openExerciseDetail(exercise: ExerciseDTO) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, ExerciseDetailFragment(this, exercise))
            .addToBackStack(null)
            .commit()
    }

    override fun close() {
        supportFragmentManager.popBackStack()
    }
}