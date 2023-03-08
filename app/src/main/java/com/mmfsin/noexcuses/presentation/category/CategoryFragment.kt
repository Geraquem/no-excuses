package com.mmfsin.noexcuses.presentation.category

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
import com.mmfsin.noexcuses.presentation.category.model.CategoryDTO

class CategoryFragment() : Fragment(), CategoryView {

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
//                group.setOnClickListener(onCLick)
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
        Toast.makeText(mContext, getString(R.string.sww), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}