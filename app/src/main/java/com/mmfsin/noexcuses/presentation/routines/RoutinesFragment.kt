package com.mmfsin.noexcuses.presentation.routines

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onResume() {
        super.onResume()
//        val routineOpened = (activity as MainActivity).routineOpened
//        routineOpened?.let { onRoutineClick(it) }
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
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = RoutinesAdapter(routines, this@RoutinesFragment)
            }
        }
    }

    override fun onRoutineClick(id: String, days: String) {
        Toast.makeText(mContext, days, Toast.LENGTH_SHORT).show()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}