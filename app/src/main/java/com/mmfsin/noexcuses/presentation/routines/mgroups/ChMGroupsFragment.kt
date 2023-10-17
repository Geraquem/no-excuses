package com.mmfsin.noexcuses.presentation.routines.mgroups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.routines.mgroups.ChMGroupsFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.routines.mgroups.adapter.ChMGroupsAdapter
import com.mmfsin.noexcuses.presentation.routines.mgroups.intefaces.IChMGroupListener
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChMGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding, ChMGroupsViewModel>(),
    IChMGroupListener {

    override val viewModel: ChMGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    private var idGroup: IdGroup? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { idGroup = it.getParcelable(ID_GROUP) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMuscularGroups()
    }

    override fun setUI() {
        (activity as MainActivity).setUpToolbar(title = getString(R.string.mgroups_toolbar), info = true)
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChMGroupsEvent.MGroups -> setUpMGroups(event.groups)
                is ChMGroupsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = ChMGroupsAdapter(items, this@ChMGroupsFragment)
        }
    }

    override fun onMGroupClick(mGroup: String) {
        idGroup?.let { ids ->
            ids.muscularGroup = mGroup
            findNavController().navigate(actionMGroupsToExercises(ids))
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}