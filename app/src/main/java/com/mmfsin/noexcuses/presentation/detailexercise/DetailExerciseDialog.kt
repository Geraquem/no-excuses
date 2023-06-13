package com.mmfsin.noexcuses.presentation.detailexercise

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDetailExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise

class DetailExerciseDialog(
    private val fromDay: Boolean,
    private val exercise: Exercise,
    private val dayName: String? = null,
    private val add: () -> Unit = {}
) : BaseDialog<DialogDetailExerciseBinding>(), DetailExerciseView {

    private val presenter by lazy { DetailExercisePresenter(this) }

    private var isYPlayerVisible = false
    private var editWeightClicked = false

    override fun inflateView(inflater: LayoutInflater) =
        DialogDetailExerciseBinding.inflate(inflater)

    override fun setUI() {
        binding.apply {
            Glide.with(this@DetailExerciseDialog.requireContext()).load(exercise.imageURL)
                .into(image)
            tvExerciseOf.text = getString(R.string.exercises_of, exercise.category)
            tvName.text = exercise.name

            tvWeight.text = exercise.weight.toString()


//            exercise.videoURL?.let { presenter.playVideo(youtubePlayerView, it) }
            exercise.videoURL?.let { presenter.playVideo(youtubePlayerView, "a5uQMwRMHcs") }

            etWeight.visibility = View.GONE

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
            ivEditWeight.setOnClickListener {
                editWeightClicked = !editWeightClicked
                if (editWeightClicked) {
                    ivEditWeight.setImageResource(R.drawable.ic_check)
                    etWeight.visibility = View.VISIBLE
                } else {
                    val newWeight = etWeight.text.toString()
                    if (newWeight.isNotEmpty()) {
//                        presenter.saveNewWeight(newWeight)
                        tvWeight.text = newWeight
                    }
                    ivEditWeight.setImageResource(R.drawable.ic_config)
                    etWeight.visibility = View.GONE
                    etWeight.text = null
                }
            }

            btnVideo.setOnClickListener {
                isYPlayerVisible = !isYPlayerVisible
                youtubePlayerView.visibility = if (isYPlayerVisible) {
                    btnVideo.text = getString(R.string.hide_video)
                    View.VISIBLE
                } else {
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

