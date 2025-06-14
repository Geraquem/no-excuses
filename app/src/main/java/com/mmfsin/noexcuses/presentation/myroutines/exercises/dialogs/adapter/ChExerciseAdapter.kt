package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDataBinding
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.utils.deletePointZero

class ChExerciseAdapter(private val data: List<Data>) :
    RecyclerView.Adapter<ChExerciseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDataBinding.bind(view)
        private val c = binding.root.context
        fun bind(data: Data, position: Int) {
            binding.apply {
                tvSerie.text = c.getString(R.string.days_exercise_dialog_serie, position.toString())
                data.reps?.let { tvRep.text = data.reps.toString() }
                data.weight?.let { tvWeight.text = data.weight.deletePointZero() }
                ivDelete.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position + 1)
    }

    override fun getItemCount(): Int = data.size
}