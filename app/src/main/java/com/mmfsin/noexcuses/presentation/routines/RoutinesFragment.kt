package com.mmfsin.noexcuses.presentation.routines

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentRoutinesBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.routines.adapter.RoutinesAdapter
import com.mmfsin.noexcuses.presentation.routines.interfaces.IRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutinesFragment : BaseFragment<FragmentRoutinesBinding, RoutinesViewModel>(),
    IRoutineListener {

    override val viewModel: RoutinesViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRoutines()
    }

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.routines_toolbar))
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoutinesEvent.GetRoutines -> setUpRoutines(event.routines)
                is RoutinesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpRoutines(routines: List<Routine>) {
        binding.apply {
            binding.rvRoutines.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                adapter = RoutinesAdapter(routines, this@RoutinesFragment)
            }
            rvRoutines.isVisible = routines.isEmpty()
            tvEmpty.isVisible = routines.isEmpty()
        }
    }

    override fun onRoutineClick(id: String) {

    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}