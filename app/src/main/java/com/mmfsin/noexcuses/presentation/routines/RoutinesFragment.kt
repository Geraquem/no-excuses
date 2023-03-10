package com.mmfsin.noexcuses.presentation.routines

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentRoutinesBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.routines.interfaces.IRoutinesListener

class RoutinesFragment() : BaseFragment<FragmentRoutinesBinding>(), RoutinesView,
    IRoutinesListener {

    private val presenter by lazy { RoutinesPresenter(this) }

    private lateinit var mContext: Context

    private var phase: Phase? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentRoutinesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBundleArgs() =
        arguments?.let { bundle -> phase = bundle.getSerializable("phase") as Phase }


    override fun setUI() {
        getBundleArgs()
        binding.apply {
            phase?.let {phase ->
                toolbar.title.text = phase.name
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
//            newPhase.setOnClickListener {
//                val dialog = NewPhaseDialog { presenter.getPhases() }
//                activity?.let { dialog.show(it.supportFragmentManager, "") }
//            }
        }
    }

    override fun getRoutines(phases: List<Phase>) {
        binding.rvRoutines.apply {
            layoutManager = LinearLayoutManager(mContext)
//            adapter = RoutinesAdapter(phases, this@RoutinesFragment)
        }
    }

    override fun onClick(id: String) {
    }

    override fun sww() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}