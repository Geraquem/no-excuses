package com.mmfsin.noexcuses.presentation.chooseexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemChooseExerciseBinding
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.chooseexercises.interfaces.IChExercisesListener

class ChExercisesAdapter(
    private val realmExercises: List<RealmExercise>, private val listener: IChExercisesListener
) : RecyclerView.Adapter<ChExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChooseExerciseBinding.bind(view)
        fun bind(realmExercise: RealmExercise) {
            binding.apply {
                tvName.text = realmExercise.nombre
                var selected = false
                check.setOnClickListener {
                    if (selected) check.setImageResource(R.drawable.ic_uncheck)
                    else check.setImageResource(R.drawable.ic_check)
                    /** mirar en el contacts del curro */
                    Toast.makeText(binding.root.context, realmExercise.nombre, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_choose_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(realmExercises[position])
        holder.itemView.setOnClickListener { listener.onClick(realmExercises[position].id) }
    }

    override fun getItemCount(): Int = realmExercises.size
}