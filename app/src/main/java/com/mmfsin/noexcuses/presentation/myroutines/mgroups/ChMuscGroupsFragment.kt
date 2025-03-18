package com.mmfsin.noexcuses.presentation.myroutines.mgroups

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
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.ChMuscGroupsFragmentDirections.Companion.actionMGroupsToExercises
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.adapter.ChMuscGroupsAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.intefaces.IChMuscGroupListener
import com.mmfsin.noexcuses.utils.ID_GROUP
import com.mmfsin.noexcuses.utils.getBundleParcelableArgs
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChMuscGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding, ChMuscGroupsViewModel>(),
    IChMuscGroupListener {

    override val viewModel: ChMuscGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    private var idGroup: IdGroup? = null
    private var bodyImage: Boolean = false

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { idGroup = it.getBundleParcelableArgs(ID_GROUP, IdGroup::class.java) }
    }

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
                is ChMuscGroupsEvent.BodyImage -> {
                    bodyImage = event.isWomanImage
                    viewModel.getMuscularGroups()
                }

                is ChMuscGroupsEvent.MuscGroups -> setUpMGroups(event.groups)
                is ChMuscGroupsEvent.SWW -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = ChMuscGroupsAdapter(items, bodyImage, this@ChMuscGroupsFragment)
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