package com.mmfsin.noexcuses.presentation.maximums.mgroups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.maximums.mgroups.ChMaxMuscGroupsFragmentDirections.Companion.actionRoutinesToExercises
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.adapter.ChMuscGroupsAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.intefaces.IChMuscGroupListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChMaxMuscGroupsFragment :
    BaseFragment<FragmentMuscularGroupsBinding, ChMaxMuscGroupsViewModel>(),
    IChMuscGroupListener {

    override val viewModel: ChMaxMuscGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    private var bodyImage: Boolean = false

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBodyImage()
    }

    override fun setUI() {
        (activity as BedRockActivity).apply {
            setUpToolbar(title = getString(R.string.mgroups_toolbar))
            rightIconToolbar(isVisible = true,
                icon = R.drawable.ic_info,
                action = { supportFragmentManager.showFragmentDialog(InfoDialog()) })
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChMaxMuscGroupsEvent.BodyImage -> {
                    bodyImage = event.isWomanImage
                    viewModel.getMuscularGroups()
                }

                is ChMaxMuscGroupsEvent.MuscGroups -> setUpMGroups(event.groups)
                is ChMaxMuscGroupsEvent.SWW -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = ChMuscGroupsAdapter(items, bodyImage, this@ChMaxMuscGroupsFragment)
        }
    }

    override fun onMGroupClick(mGroup: String) {
        findNavController().navigate(actionRoutinesToExercises(mGroup))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}