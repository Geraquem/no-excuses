package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDataAddBinding
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.interfaces.IAddChExerciseListener
import com.mmfsin.noexcuses.utils.deletePointZero

class AddChExerciseAdapter(
    private val data: MutableList<Data>,
    private val listener: IAddChExerciseListener
) : RecyclerView.Adapter<AddChExerciseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDataAddBinding.bind(view)
        private val c = binding.root.context
        private var pos = 0
        private var listener: IAddChExerciseListener? = null
        fun bind(data: Data, position: Int, addListener: IAddChExerciseListener) {
            binding.apply {
                pos = position
                listener = addListener

                tvSerie.text = c.getString(R.string.days_exercise_dialog_serie, position.toString())
                data.reps?.let { etRep.setText(it.toString()) }
                data.weight?.let { etWeight.setText(it.deletePointZero()) }

                etRep.addTextChangedListener(repsTextWatcher)
                etWeight.addTextChangedListener(weightTextWatcher)

                ivDelete.setOnClickListener { listener?.deleteSerie(data.id ?: "nada") }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data_add, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position + 1, listener)
    }

    override fun getItemCount(): Int = data.size
}