package com.mmfsin.noexcuses.presentation.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentInitBinding
import com.mmfsin.noexcuses.presentation.init.InitFragmentDirections.Companion.actionInitToPhases

class InitFragment : BaseFragment<FragmentInitBinding>(), InitView {

    private val presenter by lazy { InitPresenter(this) }

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInitBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkWhereToCall()
    }

    override fun setUI() {
        super.setUI()
    }

    override fun setListeners() {
        binding.apply {
            phases.setOnClickListener { findNavController().navigate(actionInitToPhases()) }
        }
    }

    override fun onFirebaseResult() {
        Toast.makeText(this@InitFragment.requireContext(), "realm existe", Toast.LENGTH_SHORT)
            .show()
    }
}