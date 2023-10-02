package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemAddDataBinding
import com.mmfsin.noexcuses.domain.models.Data

class AddChExerciseAdapter(
    private val data: List<Data>,
//    private val listener: IDayExerciseListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_DATA = 2
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAddDataBinding.bind(view)
        private val c = binding.root.context
        fun bind(data: Data, position: Int) {
            binding.apply {
                tvSerie.text = c.getString(R.string.days_exercise_dialog_serie, position.toString())
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_data_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_data, parent, false)
                DataViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> holder.bind(data[position], position)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_DATA
        }
    }

    override fun getItemCount(): Int = data.size
}