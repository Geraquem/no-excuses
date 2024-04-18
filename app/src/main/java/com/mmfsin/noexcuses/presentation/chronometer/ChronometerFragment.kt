package com.mmfsin.noexcuses.presentation.chronometer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragmentNoVM
import com.mmfsin.noexcuses.databinding.FragmentChronometerBinding
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChronometerFragment : BaseFragmentNoVM<FragmentChronometerBinding>() {

    private lateinit var mContext: Context

    private var running = false
    var pauseOffSet = 0

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChronometerBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.menu_chronometer))
        }
    }

    override fun setListeners() {
        binding.apply {
            btnPlay.setOnClickListener { startChronometer() }
            btnStop.setOnClickListener { pauseChronometer() }
            btnReset.setOnClickListener { resetChronometer() }
        }
    }

    private fun startChronometer() {

    }

    private fun pauseChronometer() {

    }

    private fun resetChronometer() {

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}