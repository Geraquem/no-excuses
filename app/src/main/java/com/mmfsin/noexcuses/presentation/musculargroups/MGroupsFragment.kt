package com.mmfsin.noexcuses.presentation.musculargroups

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.musculargroups.adapter.MGroupsAdapter
import com.mmfsin.noexcuses.presentation.musculargroups.MGroupsFragmentDirections.Companion.actionMuscularGroupsToChooseExercises

class MGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding>(), MGroupsView {

    private val presenter by lazy { MGroupsPresenter(this) }

    private var dayName: String? = null
    private var dayId: String? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    private fun getBundleArgs() {
        arguments?.let { bundle ->
            dayName = bundle.getString("dayName", "")
            dayId = bundle.getString("dayId")
        }
    }

    override fun setUI() {
        getBundleArgs()
        /** loading on */
        binding.toolbar.title.text = getString(R.string.muscularGroups)
        presenter.getMuscularGroups()
    }

    override fun getMuscularGroups(exercises: List<MuscularGroup>) {
        binding.rvMgroups.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = MGroupsAdapter(exercises.sortedBy { it.order }) { mg ->
                dayId?.let { navigateToChooseExercises(mg.name) } ?: run {
                    navigateToExercises(mg.name)
                }
            }
            /** loading off */
        }
    }

    override fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun navigateToExercises(name: String) {
        findNavController().navigate(MGroupsFragmentDirections.actionMuscularGroupsToExercises(name))
    }

    private fun navigateToChooseExercises(name: String) {
        dayId?.let { dayId ->
            dayName?.let { dayName ->
                findNavController().navigate(
                    actionMuscularGroupsToChooseExercises(name, dayName, dayId)
                )
            }
        }
    }

    override fun sww() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}