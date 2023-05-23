package com.mmfsin.noexcuses.presentation.detailexercise

import android.os.CountDownTimer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailExercisePresenter(val view: DetailExerciseView) {

    fun playVideo(youtubePlayerView: YouTubePlayerView, url: String) {
        youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(url, 0f)
                object : CountDownTimer(1000, 100) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        youTubePlayer.pause()
                    }
                }.start()
            }
        })
    }

    fun pauseVideo(youtubePlayerView: YouTubePlayerView) {
        youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
            }
        })
    }
}