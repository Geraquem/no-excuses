package com.mmfsin.noexcuses.presentation.chronometer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragmentNoVM
import com.mmfsin.noexcuses.databinding.FragmentChronoBinding
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChronoFragment : BaseFragmentNoVM<FragmentChronoBinding>() {

    private lateinit var mContext: Context

    private var running = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChronoBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            (activity as MainActivity).setUpToolbar(title = getString(R.string.menu_chronometer))
            alternateButtonPlay()
        }
    }

    private fun alternateButtonPlay() {
        val text = if (running) "PAUSE" else "PLAY"
        binding.btnPlay.text = text
    }

    override fun setListeners() {
        binding.apply {
            btnPlay.setOnClickListener {
                if (running) pauseChronometer() else playChronometer()
                alternateButtonPlay()
            }
            btnReset.setOnClickListener { resetChronometer() }
        }
    }

    private fun playChronometer() {
        if (!running) {
            running = true
            binding.chrono.start()
        }
    }

    private fun pauseChronometer() {
        if (running) {
            running = false
            binding.chrono.stop()
        }
    }

    private fun resetChronometer() {
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