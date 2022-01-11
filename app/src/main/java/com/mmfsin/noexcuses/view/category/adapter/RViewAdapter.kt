package com.mmfsin.noexcuses.view.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.RowExercisesBinding
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class RViewAdapter(
    private val onClick: (categoryDTO: CategoryDTO) -> Unit,
    private val context: Context,
    private var list: List<CategoryDTO>
) :
    RecyclerView.Adapter<RViewAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_exercises, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(context, list[position])
        holder.bdg.row.setOnClickListener{
            onClick(list[position])
        }
    }

    override fun getItemCount() = when {
        (list.size > 14) -> 15
        else -> list.size
    }

    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bdg = RowExercisesBinding.bind(view)
        fun bind(context: Context, category: CategoryDTO) {
            bdg.categoryName.text = category.name
            Glide.with(context).load(category.image).into(bdg.categoryImage)
        }
    }
}