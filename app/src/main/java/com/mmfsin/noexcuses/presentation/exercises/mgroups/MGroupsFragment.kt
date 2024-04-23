package com.mmfsin.noexcuses.presentation.exercises.mgroups

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
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.mgroups.MGroupsFragmentDirections.Companion.actionMuscularGroupsToExercises
import com.mmfsin.noexcuses.presentation.exercises.mgroups.adapter.MGroupsAdapter
import com.mmfsin.noexcuses.presentation.exercises.mgroups.interfaces.IMGroupListener
import com.mmfsin.noexcuses.presentation.notes.NotesFragmentDirections
import com.mmfsin.noexcuses.utils.BEDROCK_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding, MGroupsViewModel>(),
    IMGroupListener {

    override val viewModel: MGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroupId: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        mGroupId = activity?.intent?.getStringExtra(BEDROCK_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGroupId?.let { id ->
            findNavController().navigate(actionMuscularGroupsToExercises(id))
            mGroupId = null
        } ?: run { viewModel.getMuscularGroups() }
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(title = getString(R.string.mgroups_toolbar))
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MGroupsEvent.MGroups -> setUpMGroups(event.groups)
                is MGroupsEvent.SWW -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = MGroupsAdapter(items, this@MGroupsFragment)
        }
    }

    override fun onMGroupClick(mGroup: String) {
        findNavController().navigate(actionMuscularGroupsToExercises(mGroup))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}