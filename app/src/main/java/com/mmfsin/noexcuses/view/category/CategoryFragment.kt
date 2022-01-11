package com.mmfsin.noexcuses.view.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.view.category.adapter.RViewAdapter
import com.mmfsin.noexcuses.view.category.model.CategoryDTO
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), CategoryView {

    private val presenter by lazy { CategoryPresenter(this) }

    private lateinit var adapter: RViewAdapter

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
        presenter.getCategoryList()
    }

    override fun showCategoryList(list: List<CategoryDTO>) {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        adapter = RViewAdapter(
            { Toast.makeText(mContext, it.name, Toast.LENGTH_SHORT).show() },
            mContext,
            list
        )
        recyclerView.adapter = adapter
        // loading.visibility = View.GONE
    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.somethingWentWrong), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}