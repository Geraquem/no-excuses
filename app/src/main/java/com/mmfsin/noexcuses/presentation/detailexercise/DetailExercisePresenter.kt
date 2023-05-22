package com.mmfsin.noexcuses.presentation.detailexercise

import android.os.CountDownTimer
import com.mmfsin.noexcuses.data.repository.FirebaseRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercises
import com.mmfsin.noexcuses.domain.models.CompleteExercise
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailExercisePresenter(val view: DetailExerciseView) : IExercises {

    private val repository by lazy { FirebaseRepository(this) }

    fun getExercise(exercise: RealmExercise) {
        repository.getExerciseFromFirebase(exercise)
    }

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

    override fun retrievedExercisesFromFirebase(result: Boolean) {}

    override fun retrievedSingleExercise(result: CompleteExercise?) {
        result?.let { exercise ->
            view.getExerciseDetail(exercise)
        } ?: run { view.sww() }
    }
}