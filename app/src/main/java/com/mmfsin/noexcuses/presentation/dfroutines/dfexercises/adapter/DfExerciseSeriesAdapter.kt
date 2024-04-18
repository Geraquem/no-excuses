package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDefaultDataBinding

class DfExerciseSeriesAdapter(private val data: List<String>) :
    RecyclerView.Adapter<DfExerciseSeriesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDefaultDataBinding.bind(view)
        private val c = binding.root.context
        fun bind(reps: String, position: Int) {
            binding.apply {
                tvSerie.text = c.getString(R.string.days_exercise_dialog_serie, position.toString())
                tvRep.text = reps
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_default_data, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position + 1)
    }

    override fun getItemCount(): Int = data.size
}