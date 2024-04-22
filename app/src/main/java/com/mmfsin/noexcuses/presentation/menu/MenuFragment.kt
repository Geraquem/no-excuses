package com.mmfsin.noexcuses.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMenuBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyRoutines
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToRoutines
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {

    override val viewModel: MenuViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkVersion()
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).apply {
                setUpToolbar(false)
                rightIconToolbar(isVisible = false)
                routineOpened = null
            }
            llMyActualRoutine.visibility = View.GONE
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnDefaultRoutines.setOnClickListener { navigateTo(actionMenuToRoutines()) }
            btnMyRoutines.setOnClickListener { navigateTo(actionMenuToMyRoutines()) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> viewModel.getMenuItems()
                is MenuEvent.ActualRoutine -> setUpActualRoutine(event.routine)
                is MenuEvent.SWW -> error()
            }
        }
    }

    private fun setUpActualRoutine(routine: Routine?) {
        binding.apply {
            routine?.let {
                actualRoutine.image.tvNumOfDays.text = routine.days.toString()
                actualRoutine.tvTitle.text = routine.title
                actualRoutine.tvDescription.text = routine.description
                actualRoutine.ivPushpin.setImageResource(R.drawable.ic_pushpin)
                llMyActualRoutine.visibility = View.VISIBLE
            }
            loading.root.visibility = View.GONE
        }
    }

//    override fun onItemClick(action: MenuAction) {
//        when (action) {
//            ROUTINES -> navigateTo(actionMenuToRoutines())
//            MY_ROUTINES -> navigateTo(actionMenuToMyRoutines())
//            EXERCISES -> navigateTo(actionMenuToMuscularGroups())
//            NOTES -> navigateTo(actionMenuToNotes())
//            WEIGHTS -> {}
//        }
//    }

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}