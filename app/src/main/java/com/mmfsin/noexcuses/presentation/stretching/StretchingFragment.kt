package com.mmfsin.noexcuses.presentation.stretching

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentStretchingBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.stretching.adapter.StretchingMGroupAdapter
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
        viewModel.getMuscularGroups()
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
                is StretchingEvent.GetMuscularGroups -> setUpMuscularGroups(event.mGroups)
                is StretchingEvent.GetStretching -> {}
                is StretchingEvent.SWW -> error()
            }
        }
    }

    private fun setUpMuscularGroups(mgroups: List<MuscularGroup>) {
        binding.rvStretching.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = StretchingMGroupAdapter(mgroups, this@StretchingFragment)
        }
    }

    override fun onStretchingMGroupClick(category: String){
        (activity as MainActivity).openBedRockActivity(
            navGraph = R.navigation.nav_graph_stretching_detail,
            strArgs = category,
        )
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}