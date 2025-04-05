package com.mmfsin.noexcuses.presentation.stretching

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentStretchingBinding
import com.mmfsin.noexcuses.domain.models.Stretch
import com.mmfsin.noexcuses.presentation.stretching.adapter.StretchingAdapter
import com.mmfsin.noexcuses.presentation.stretching.interfaces.IStretchingListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StretchingFragment : BaseFragment<FragmentStretchingBinding, StretchingViewModel>(),
    IStretchingListener {

    override val viewModel: StretchingViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentStretchingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStretchingData()
    }

    override fun setUI() {
        binding.apply {
            tvTopText.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is StretchingEvent.GetStretchingData -> setUpStretching(event.data)
                is StretchingEvent.GetStretchingByMGroup -> {}
                is StretchingEvent.SWW -> error()
            }
        }
    }

    private fun setUpStretching(data: List<Stretch>) {
        binding.rvStretching.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = StretchingAdapter(data, this@StretchingFragment)
        }
    }

    override fun onStretchingClick(category: String) {
        Toast.makeText(mContext, "ey", Toast.LENGTH_SHORT).show()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}