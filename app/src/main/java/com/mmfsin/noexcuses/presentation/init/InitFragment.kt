package com.mmfsin.noexcuses.presentation.init

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.databinding.FragmentInitBinding
import com.mmfsin.noexcuses.presentation.init.InitFragmentDirections.Companion.actionInitToPhases

class InitFragment : Fragment() {

    private var _bdg: FragmentInitBinding? = null
    private val binding get() = _bdg!!

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bdg = FragmentInitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            phases.setOnClickListener { findNavController().navigate(actionInitToPhases()) }
        }
    }
}