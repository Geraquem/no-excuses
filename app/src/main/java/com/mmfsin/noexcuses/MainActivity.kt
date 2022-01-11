package com.mmfsin.noexcuses

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.view.category.CategoryFragment
import com.mmfsin.noexcuses.view.category.model.CategoryDTO
import com.mmfsin.noexcuses.view.exercises.ExercisesFragment

class MainActivity : AppCompatActivity(), IFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* QUITAR EN UN FUTURO */
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_container_view, CategoryFragment(this)).commit()
    }

    override fun openExercises(category: CategoryDTO) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container_view, ExercisesFragment(this, category))
            .commit()
    }

    override fun close() {
        supportFragmentManager.popBackStack()
    }
}