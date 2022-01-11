package com.mmfsin.noexcuses

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.view.category.CategoryFragment
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class MainActivity : AppCompatActivity(), IFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CategoryFragment(this)).commit()
    }

    override fun openExercises(category: CategoryDTO) {
        Toast.makeText(this, category.name, Toast.LENGTH_SHORT).show()
    }

    override fun close() {}
}