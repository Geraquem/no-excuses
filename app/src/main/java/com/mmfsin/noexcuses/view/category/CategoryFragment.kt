package com.mmfsin.noexcuses.view.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class CategoryFragment(private val listener: IFragment) : Fragment(), CategoryView {

    private val presenter by lazy { CategoryPresenter(this) }

    private lateinit var categories: List<CategoryDTO>
    private lateinit var groups: List<LinearLayout>
    private lateinit var images: List<ImageView>
    private lateinit var names: List<TextView>

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loading VISIBLE

//        backButton.setOnClickListener { listener.close() }

        groups = presenter.getGroupList(mContext, activity)
        images = presenter.getImagesList(mContext, activity)
        names = presenter.getNamesList(mContext, activity)

        if (groups.isNotEmpty() && images.isNotEmpty() && names.isNotEmpty()) {
            for (group in groups) {
                group.setOnClickListener(onCLick)
            }
            presenter.getCategoryList()
        } else {
            somethingWentWrong()
        }
    }

    override fun showCategoryList(list: List<CategoryDTO>) {
        categories = list
        for (i in list.indices) {
            Glide.with(mContext).load(list[i].image).into(images[i])
            names[i].text = list[i].name
        }

        //loading GONE
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private val
            onCLick = View.OnClickListener {
        when (it.id) {
            R.id.group1 -> {
                listener.openExercises(categories[0])
            }
            R.id.group2 -> {
                listener.openExercises(categories[1])
            }
            R.id.group3 -> {
                listener.openExercises(categories[2])
            }
            R.id.group4 -> {
                listener.openExercises(categories[3])
            }
            R.id.group5 -> {
                listener.openExercises(categories[4])
            }
            R.id.group6 -> {
                listener.openExercises(categories[5])
            }
        }
    }
}