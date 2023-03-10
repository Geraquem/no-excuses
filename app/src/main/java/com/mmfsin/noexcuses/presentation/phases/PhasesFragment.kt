package com.mmfsin.noexcuses.presentation.phases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentPhasesBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.PhasesFragmentDirections.Companion.actionPhasesToRoutines
import com.mmfsin.noexcuses.presentation.phases.adapter.PhasesAdapter
import com.mmfsin.noexcuses.presentation.phases.dialogs.configphase.ConfigPhaseDialog
import com.mmfsin.noexcuses.presentation.phases.dialogs.deletephase.DeletePhaseDialog
import com.mmfsin.noexcuses.presentation.phases.dialogs.newphase.NewPhaseDialog
import com.mmfsin.noexcuses.presentation.phases.interfaces.IConfigPhasesListener
import com.mmfsin.noexcuses.presentation.phases.interfaces.IPhasesListener

class PhasesFragment() : BaseFragment<FragmentPhasesBinding>(), PhasesView, IPhasesListener,
    IConfigPhasesListener {

    private val presenter by lazy { PhasesPresenter(this) }

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPhasesBinding = FragmentPhasesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getPhases()
    }

    override fun setUI() {
        binding.toolbar.title.text = getString(R.string.routines)
    }

    override fun setListeners() {
        binding.apply {
            toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
            newPhase.root.setOnClickListener {
                val dialog = NewPhaseDialog(null) { presenter.getPhases() }
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

    override fun onClick(phase: Phase) = findNavController().navigate(actionPhasesToRoutines(phase))

    override fun config(phase: Phase) {
        val dialog = ConfigPhaseDialog(phase, this)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun edit(phase: Phase) {
        val dialog = NewPhaseDialog(phase) { presenter.getPhases() }
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun delete(phase: Phase) {
        val dialog = DeletePhaseDialog(phase) { presenter.deletePhase(it) }
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun phaseDeleted() = presenter.getPhases()

    override fun sww() { }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}