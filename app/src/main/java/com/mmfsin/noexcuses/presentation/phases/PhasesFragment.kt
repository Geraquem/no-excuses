package com.mmfsin.noexcuses.presentation.phases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentPhasesBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.PhasesFragmentDirections.Companion.actionPhasesToRoutines
import com.mmfsin.noexcuses.presentation.phases.adapter.PhasesAdapter
import com.mmfsin.noexcuses.presentation.phases.dialog.NewPhaseDialog
import com.mmfsin.noexcuses.presentation.phases.interfaces.IPhasesListener

class PhasesFragment() : BaseFragment<FragmentPhasesBinding>(), PhasesView, IPhasesListener {

    private val presenter by lazy { PhasesPresenter(this) }

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPhasesBinding = FragmentPhasesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getPhases()
    }

    override fun setListeners() {
        binding.apply {
            newPhase.setOnClickListener {
                val dialog = NewPhaseDialog { presenter.getPhases() }
                activity?.let { dialog.show(it.supportFragmentManager, "") }
            }
        }
    }

    override fun getPhases(phases: List<Phase>) {
        binding.rvPhases.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = PhasesAdapter(phases, this@PhasesFragment)
        }
    }

    override fun onClick(id: String) = findNavController().navigate(actionPhasesToRoutines())

    override fun editPhase(id: String) {
        val a = 2
    }

    override fun deletePhase(id: String) {
        val a = 2
    }

    override fun sww() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}