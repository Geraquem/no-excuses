package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemAddDataBinding
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.interfaces.IAddChExerciseListener

class AddChExerciseAdapter(
    private val data: MutableList<Data>,
    private val listener: IAddChExerciseListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_DATA = 2
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAddDataBinding.bind(view)
        private val c = binding.root.context
        private var pos = 0
        private var listener: IAddChExerciseListener? = null
        fun bind(data: Data, position: Int, addListener: IAddChExerciseListener) {
            binding.apply {
                tvSerie.text = c.getString(R.string.days_exercise_dialog_serie, position.toString())
                etRep.addTextChangedListener(repsTextWatcher)
                etWeight.addTextChangedListener(weightTextWatcher)

                pos = position
                listener = addListener
            }
        }

        private val repsTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    val reps = s.toString().toInt()
                    listener?.addRepToSerie(pos.toString(), reps)
                } catch (e: java.lang.Exception) {
                    Log.e("ERROR ->", "Algo ha salido mal")
                }
            }
        }

        private val weightTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    val weight = s.toString().toDouble()
                    listener?.addWeightToSerie(pos.toString(), weight)
                } catch (e: java.lang.Exception) {
                    Log.e("ERROR ->", "Algo ha salido mal")
                }
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
            is DataViewHolder -> holder.bind(data[position], position, listener)
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