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
import com.mmfsin.noexcuses.databinding.FragmentStretchingBinding
import com.mmfsin.noexcuses.domain.models.Stretching
import com.mmfsin.noexcuses.presentation.stretching.StretchingEvent
import com.mmfsin.noexcuses.presentation.stretching.StretchingViewModel
import com.mmfsin.noexcuses.presentation.stretching.adapter.StretchingDetailAdapter
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.MGROUP_ID
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StretchingDetailFragment : BaseFragment<FragmentStretchingBinding, StretchingViewModel>() {

    override val viewModel: StretchingViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroup: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentStretchingBinding.inflate(inflater, container, false)

//    override fun getBundleArgs() {
//        arguments?.let { mGroup = it.getString(MGROUP_ID) }
//    }

    override fun getBundleArgs() {
        mGroup = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGroup?.let { viewModel.getStretching(it) } ?: run { error() }
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(title = mGroup)
        binding.apply {
            tvTopText.visibility = View.GONE
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is StretchingEvent.GetMuscularGroups -> {}
                is StretchingEvent.GetStretching -> {
                    setUpMuscularGroups(event.stretching)
                }

                is StretchingEvent.SWW -> error()
            }
        }
    }

    private fun setUpMuscularGroups(stretching: List<Stretching>) {
        binding.rvStretching.apply {
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