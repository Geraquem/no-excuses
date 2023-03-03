package com.mmfsin.noexcuses.presentation.phases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentPhasesBinding

class PhaseFragment() : BaseFragment<FragmentPhasesBinding>() {

    //    private val presenter by lazy { ExercisesPresenter(this) }

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPhasesBinding = FragmentPhasesBinding.inflate(inflater, container, false)

    private lateinit var mContext: Context

    override fun setUI() {
        Toast.makeText(mContext, "hola", Toast.LENGTH_SHORT).show()
    }

    override fun setListeners() {
        binding.apply {
            newPhase.setOnClickListener {
                val dialog = NewPhaseDialog()
                activity?.let { dialog.show(it.supportFragmentManager, "") }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}