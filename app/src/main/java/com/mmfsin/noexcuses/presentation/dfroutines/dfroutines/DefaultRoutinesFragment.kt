package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

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
import com.mmfsin.noexcuses.databinding.FragmentDefaultRoutinesBinding
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.DefaultDaysDialog
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.adapter.DefaultRoutinesAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultRoutinesFragment :
    BaseFragment<FragmentDefaultRoutinesBinding, DefaultRoutinesViewModel>(),
    IDefaultRoutineListener {

    override val viewModel: DefaultRoutinesViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDefaultRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDefaultRoutines()
    }

    override fun onResume() {
        super.onResume()
        val routineOpened = (activity as MainActivity).routineOpened
        routineOpened?.let { onRoutineClick(it) }
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
                is DefaultRoutinesEvent.GetDefaultRoutines -> setUpRoutines(event.defaultRoutines)
                is DefaultRoutinesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpRoutines(defaultRoutines: List<DefaultRoutine>) {
        binding.apply {
            rvRoutines.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = DefaultRoutinesAdapter(defaultRoutines, this@DefaultRoutinesFragment)
            }
        }
    }

    override fun onRoutineClick(id: String) {
        (activity as MainActivity).routineOpened = id
        val dialog = DefaultDaysDialog(routineId = id, this@DefaultRoutinesFragment)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun onDayClick(id: String) {
        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}