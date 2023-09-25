package com.mmfsin.noexcuses.presentation.exercises.mgroups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.mgroups.adapter.MGroupsAdapter
import com.mmfsin.noexcuses.presentation.exercises.mgroups.interfaces.IMGroupListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding, MGroupsViewModel>(),
    IMGroupListener {

    override val viewModel: MGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMuscularGroups()
    }

    override fun setUI() {
        binding.apply {}
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MGroupsEvent.MGroups -> setUpMGroups(event.groups)
                is MGroupsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.apply {
            binding.rvMgroups.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                adapter = MGroupsAdapter(items, this@MGroupsFragment)
            }
        }
    }

    override fun onMGroupClick(mGroup: String) {

    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}