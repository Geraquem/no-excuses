package com.mmfsin.noexcuses.view.exercises.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.RowExerciseBinding
import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO

class RViewAdapter(
    private val onClick: (exercise: ExerciseDTO) -> Unit,
    private val context: Context,
    private var exercises: List<ExerciseDTO>
) :
    RecyclerView.Adapter<RViewAdapter.RecordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        return RecordHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.bind(context, exercises[position])
        holder.bdg.row.setOnClickListener {
            onClick(exercises[position])
        }
    }

    override fun getItemCount() = exercises.size


    class RecordHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bdg = RowExerciseBinding.bind(view)
        fun bind(context: Context, exercise: ExerciseDTO) {

        }
    }
}