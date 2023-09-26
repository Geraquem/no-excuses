package com.mmfsin.noexcuses.presentation.chronometer

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragmentNoVM
import com.mmfsin.noexcuses.databinding.FragmentChronoBinding
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChronoFragment : BaseFragmentNoVM<FragmentChronoBinding>() {

    private lateinit var mContext: Context

    private lateinit var chronometer: Chronometer

    private var running = false
    private var pauseOffset: Long = 0
    private val handler = Handler(Looper.getMainLooper())
    private var elapsedTime: Long = 0

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChronoBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.menu_chronometer))
            chronometer = chrono
            chronometer.text = getString(R.string.chrono_base)
            alternateButtonPlay()
        }
    }

    private fun alternateButtonPlay() {
        val text = if (running) "PAUSE" else "PLAY"
        binding.btnPlay.text = text
    }

    override fun setListeners() {
        binding.apply {
            btnPlay.setOnClickListener { if (running) pauseChronometer() else playChronometer() }
            btnReset.setOnClickListener { resetChronometer() }
        }
    }

    private fun playChronometer() {
        if (!running) {
            if (chronometer.base == SystemClock.elapsedRealtime()) {
                chronometer.base = SystemClock.elapsedRealtime()
            } else {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            }
            chronometer.start()
            running = true
            alternateButtonPlay()
            handler.postDelayed({ updateElapsedTime() }, 10)
        }
    }

    private fun pauseChronometer() {
        if (running) {
            chronometer.stop()
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            running = false
            alternateButtonPlay()
            handler.removeCallbacksAndMessages(null)
        }
    }

    private fun resetChronometer() {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
        running = false
        alternateButtonPlay()
        elapsedTime = 0
        binding.chrono.text = getString(R.string.chrono_base)
    }

    private fun updateElapsedTime() {
        if (running) {
            val currentTime = SystemClock.elapsedRealtime() - chronometer.base
            elapsedTime += currentTime - elapsedTime
            handler.postDelayed({ updateElapsedTime() }, 10)
            binding.chrono.text = formatTime(elapsedTime)
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val hours = (timeInMillis / 3600000).toInt()
        val minutes = ((timeInMillis - hours * 3600000) / 60000).toInt()
        val seconds = ((timeInMillis - hours * 3600000 - minutes * 60000) / 1000).toInt()
//        val milliseconds = (timeInMillis % 1000).toInt()

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}