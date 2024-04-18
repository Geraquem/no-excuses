package com.mmfsin.noexcuses.presentation.chronometer

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
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
    private var pauseOffSet: Long = 0

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChronometerBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.menu_chronometer))
            btnStop.visibility = View.INVISIBLE
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
        binding.apply {
            if (!running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()

                btnPlay.visibility = View.INVISIBLE
                btnStop.visibility = View.VISIBLE

                running = true
            } else {
                chronometer.base = SystemClock.elapsedRealtime()
                pauseOffSet = 0
                chronometer.stop()

                btnPlay.visibility = View.VISIBLE
                btnStop.visibility = View.INVISIBLE

                running = false
            }
        }
    }

    private fun pauseChronometer() {
        binding.apply {
            if (running) {
                chronometer.stop()
                pauseOffSet = SystemClock.elapsedRealtime() - chronometer.base

                btnPlay.visibility = View.VISIBLE
                btnStop.visibility = View.INVISIBLE

                running = false
            } else {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()

                btnPlay.visibility = View.INVISIBLE
                btnStop.visibility = View.VISIBLE

                running = true
            }
        }
    }

    private fun resetChronometer() {
        binding.apply {
            chronometer.base = 0
            chronometer.stop()
            running = false

            btnPlay.visibility = View.VISIBLE
            btnStop.visibility = View.INVISIBLE
        }
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