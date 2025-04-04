package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDfRoutineBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener

class DefaultRoutinesAdapter(
    private val myDefaultRoutines: List<Routine>,
    private val listener: IDefaultRoutineListener
) : RecyclerView.Adapter<DefaultRoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDfRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(defaultRoutine: Routine, listener: IDefaultRoutineListener) {
            binding.apply {
                image.tvNumOfDays.text = defaultRoutine.days.toString()
                image.llMain.backgroundTintList = getColorStateList(c, R.color.dark_green)

                tvTitle.text = defaultRoutine.name
                tvDescription.text = defaultRoutine.description

                val pushPinIcon = if (defaultRoutine.doingIt) R.drawable.ic_pushpin
                else R.drawable.ic_pushpin_off
                ivPushpin.setImageResource(pushPinIcon)

                ivPushpin.setOnClickListener { listener.onRoutinePushPinClick(defaultRoutine.id) }
                ivAddToMine.setOnClickListener {
                    listener.addToMyRoutines(defaultRoutine.id, defaultRoutine.name)
                }
            }
        }
    }

    fun updatePushPin(id: String) {
        deletePreviousPushPin(id)
        var position: Int? = null
        myDefaultRoutines.forEachIndexed { i, routine ->
            if (routine.id == id) {
                routine.doingIt = !routine.doingIt
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    private fun deletePreviousPushPin(id: String) {
        var position: Int? = null
        myDefaultRoutines.forEachIndexed { i, routine ->
            if (routine.doingIt && routine.id != id) {
                routine.doingIt = false
                position = i
            }
        }
        position?.let { pos -> notifyItemChanged(pos) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_df_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = myDefaultRoutines[position]
        holder.bind(routine, listener)
        holder.itemView.setOnClickListener { listener.onRoutineClick(routine.id) }
    }

    override fun getItemCount(): Int = myDefaultRoutines.size
}