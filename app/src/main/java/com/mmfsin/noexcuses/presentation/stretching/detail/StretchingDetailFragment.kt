package com.mmfsin.noexcuses.presentation.stretching.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentStretchingDetailBinding
import com.mmfsin.noexcuses.domain.models.Stretching
import com.mmfsin.noexcuses.presentation.stretching.StretchingEvent
import com.mmfsin.noexcuses.presentation.stretching.StretchingViewModel
import com.mmfsin.noexcuses.presentation.stretching.adapter.StretchingDetailAdapter
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StretchingDetailFragment : BaseFragment<FragmentStretchingDetailBinding, StretchingViewModel>() {

    override val viewModel: StretchingViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroup: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentStretchingDetailBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        mGroup = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGroup?.let { viewModel.getStretchingByMGroup(it) } ?: run { error() }
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(title = mGroup)
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is StretchingEvent.GetStretchingData -> {}
                is StretchingEvent.GetStretchingByMGroup -> {
                    setUpMuscularGroups(event.stretching)
                }

                is StretchingEvent.SWW -> error()
            }
        }
    }

    private fun setUpMuscularGroups(stretching: List<Stretching>) {
        binding.rvStretchingDetail.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = StretchingDetailAdapter(stretching)
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}