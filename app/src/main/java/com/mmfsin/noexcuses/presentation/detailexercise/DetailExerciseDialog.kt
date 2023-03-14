package com.mmfsin.noexcuses.presentation.detailexercise

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDetailExerciseBinding
import com.mmfsin.noexcuses.domain.models.CompleteExercise
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.webview.WebViewActivity

class DetailExerciseDialog(
    private val exercise: RealmExercise,
) : BaseDialog<DialogDetailExerciseBinding>(), DetailExerciseView {

    private val presenter by lazy { DetailExercisePresenter(this) }

    override fun inflateView(inflater: LayoutInflater) =
        DialogDetailExerciseBinding.inflate(inflater)

    override fun setUI() {
        presenter.getExercise(exercise)
    }

    override fun setListeners() {}

    override fun getExerciseDetail(exercise: CompleteExercise) {
        binding.apply {
            tvExerciseOf.text = getString(R.string.exercises_of, exercise.category)
            tvName.text = exercise.nombre

            btnHowToDoIt.setOnClickListener {
                startActivity(Intent(activity, WebViewActivity::class.java).apply {
                    putExtra("dataURL", exercise.dataURL)
                })
            }

        }
    }

    override fun sww() {
        Toast.makeText(this@DetailExerciseDialog.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }
}

