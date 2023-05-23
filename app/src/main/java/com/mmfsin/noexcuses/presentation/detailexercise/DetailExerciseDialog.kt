package com.mmfsin.noexcuses.presentation.detailexercise

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDetailExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.webview.WebViewActivity

class DetailExerciseDialog(
    private val fromDay: Boolean,
    private val exercise: Exercise,
    private val dayName: String? = null,
    private val add: () -> Unit = {}
) : BaseDialog<DialogDetailExerciseBinding>(), DetailExerciseView {

    private val presenter by lazy { DetailExercisePresenter(this) }

    private var isYPlayerVisible = false

    override fun inflateView(inflater: LayoutInflater) =
        DialogDetailExerciseBinding.inflate(inflater)

    override fun setUI() {
        binding.apply {
            Glide.with(this@DetailExerciseDialog.requireContext()).load(exercise.imageURL)
                .into(image)
            tvExerciseOf.text = getString(R.string.exercises_of, exercise.category)
            tvName.text = exercise.name
//            exercise.videoURL?.let { presenter.playVideo(youtubePlayerView, it) }
            exercise.videoURL?.let { presenter.playVideo(youtubePlayerView, "a5uQMwRMHcs") }

            btnAddToDay.apply {
                if (fromDay) {
                    tvAddToDay.text = getString(R.string.add_to_day, dayName)
                    visibility = View.VISIBLE
                } else visibility = View.GONE
            }
        }
    }

    override fun setListeners() {
        binding.apply {

            ivClose.setOnClickListener { dismiss() }

            btnHowToDoIt.setOnClickListener {
                startActivity(Intent(activity, WebViewActivity::class.java).apply {
                    putExtra("dataURL", exercise.dataURL)
                })
            }

            btnVideo.setOnClickListener {
                isYPlayerVisible = !isYPlayerVisible
                youtubePlayerView.visibility = if (isYPlayerVisible){
                    btnVideo.text = getString(R.string.hide_video)
                    View.VISIBLE
                } else{
                    btnVideo.text = getString(R.string.watch_video)
                    View.GONE
                }
                if (!isYPlayerVisible) presenter.pauseVideo(youtubePlayerView)
            }

            btnAddToDay.setOnClickListener {
                add()
                dismiss()
            }
        }
    }

    override fun sww() {
        Toast.makeText(this@DetailExerciseDialog.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }
}

