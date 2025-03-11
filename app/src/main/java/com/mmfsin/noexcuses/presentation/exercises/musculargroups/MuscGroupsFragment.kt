package com.mmfsin.noexcuses.presentation.exercises.musculargroups

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.MuscGroupsFragmentDirections.Companion.actionMuscularGroupsToExercises
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.adapter.MuscGroupsAdapter
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.interfaces.IMuscGroupListener
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MuscGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding, MuscGroupsViewModel>(),
    IMuscGroupListener {

    override val viewModel: MuscGroupsViewModel by viewModels()

    private lateinit var mContext: Context

    private var mGroupId: String? = null
    private var bodyImage: Boolean = false

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        mGroupId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGroupId?.let { id ->
            findNavController().navigate(actionMuscularGroupsToExercises(id))
            mGroupId = null
        } ?: run {
            viewModel.getBodyImage()
        }
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(title = getString(R.string.mgroups_toolbar))
    }

    override fun setListeners() {
        binding.apply {
            btnMan.setOnClickListener {
                Toast.makeText(mContext, "man", Toast.LENGTH_SHORT).show()
                viewModel.editBodyImage(selectedWomanImage = false)
            }
            btnWoman.setOnClickListener {
                Toast.makeText(mContext, "woman", Toast.LENGTH_SHORT).show()
                viewModel.editBodyImage(selectedWomanImage = true)
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MuscGroupsEvent.BodyImage -> {
                    bodyImage = event.isWomanImage
                    if (event.isWomanImage) binding.btnWoman.isChecked = true
                    else binding.btnMan.isChecked = true

                    viewModel.getMuscularGroups()
                }

                is MuscGroupsEvent.MuscGroups -> setUpMGroups(event.groups)
                is MuscGroupsEvent.BodyImageChanged -> {
                    bodyImage = !bodyImage
                    viewModel.getMuscularGroups()
                }

                is MuscGroupsEvent.SWW -> error()
            }
        }
    }

    private fun setUpMGroups(items: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = MuscGroupsAdapter(items, bodyImage, this@MuscGroupsFragment)
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