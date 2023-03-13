package com.mmfsin.noexcuses.presentation.musculargroups

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.databinding.IncludeMuscularGroupBinding
import com.mmfsin.noexcuses.presentation.days.DaysFragmentDirections
import com.mmfsin.noexcuses.presentation.musculargroups.MGroupsFragmentDirections.Companion.actionMuscularGroupsToChooseExercises

class MGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding>() {

    private var dayId: String? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    private fun getBundleArgs() = arguments?.let { bundle -> dayId = bundle.getString("dayId") }

    override fun setUI() {
        getBundleArgs()
        binding.apply {
            toolbar.title.text = getString(R.string.muscularGroups)
            setMuscularGroupData(hombro, R.drawable.iv_hombros, R.string.mg_hombro)
            setMuscularGroupData(pecho, R.drawable.iv_pecho, R.string.mg_pecho)
            setMuscularGroupData(biceps, R.drawable.iv_biceps, R.string.mg_biceps)
            setMuscularGroupData(triceps, R.drawable.iv_triceps, R.string.mg_triceps)
            setMuscularGroupData(espalda, R.drawable.iv_espalda, R.string.mg_espalda)
            setMuscularGroupData(pierna, R.drawable.iv_piernas, R.string.mg_piernas)
            setMuscularGroupData(abdominales, R.drawable.iv_torso, R.string.mg_torso)
            setMuscularGroupData(cardio, R.drawable.iv_cardio, R.string.mg_cardio)
        }

        dayId?.let {
            Toast.makeText(this@MGroupsFragment.requireContext(), dayId, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMuscularGroupData(group: IncludeMuscularGroupBinding, image: Int, name: Int) {
        group.image.setImageResource(image)
        group.tvName.text = getString(name)
        group.root.setOnClickListener {
            dayId?.let {
                navigateToChooseExercises(getString(name))
            } ?: run {
                navigateToExercises(getString(name))
            }
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
            findNavController().navigate(actionMuscularGroupsToChooseExercises(name, dayId))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}