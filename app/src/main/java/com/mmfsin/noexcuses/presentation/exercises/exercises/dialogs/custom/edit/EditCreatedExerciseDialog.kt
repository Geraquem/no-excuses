package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogCreateExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit.listeners.IEditCreatedExerciseListener
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCreatedExerciseDialog(
    val exerciseId: String,
    val category: String,
    val listener: IEditCreatedExerciseListener
) : BaseBottomSheet<DialogCreateExerciseBinding>() {

    private val viewModel: EditCreatedExerciseDialogViewModel by viewModels()

    private var imageURL: String? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogCreateExerciseBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 0.95).toInt()
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        observe()

        viewModel.getCreatedExercise(exerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            btnAdd.isVisible = false
            selectedImage.isVisible = false
        }
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            tvAddImage.setOnClickListener { pickImageLauncher.launch("image/*") }

            btnDelete.setOnClickListener {
                listener.deletedCreatedExercise(exerciseId)
                dismiss()
            }

            btnEdit.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty()) {
                    val createdExercise = CreatedExercise(
                        image = imageURL,
                        name = name,
                        description = etDescription.text.toString(),
                        category = category,
                        muscles = etMuscles.text.toString(),
                        externalURL = etExternalUrl.text.toString()
                    )
                    viewModel.editExercise(createdExercise, exerciseId)
                }
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedImageUri ->
                binding.apply {
                    imageURL = selectedImageUri.toString()
                    Glide.with(requireContext()).load(selectedImageUri).into(selectedImage)
                    selectedImage.isVisible = true
                }
            }
        }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is EditCreateExerciseDialogEvent.CreatedExercise -> {
                    setData(event.exercise)
                }

                is EditCreateExerciseDialogEvent.Edited -> {
                    listener.editedCreatedExercise()
                    dismiss()
                }

                is EditCreateExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun setData(exercise: Exercise) {
        binding.apply {
            imageURL = exercise.gifURL
            imageURL?.let {
                Glide.with(requireContext()).load(it).into(selectedImage)
                selectedImage.isVisible = true
            }
            etName.setText(exercise.name)
            etDescription.setText(exercise.description)
            etMuscles.setText(exercise.involvedMuscles)
            etExternalUrl.setText(exercise.muscleWikiURL)
        }
    }

    private fun error() = activity?.showErrorDialog()
}