package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.create

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
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateExerciseDialog(val category: String, val refresh: () -> Unit) :
    BaseBottomSheet<DialogCreateExerciseBinding>() {

    private val viewModel: CreateExerciseDialogViewModel by viewModels()

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
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            llEdit.isVisible = false
            selectedImage.isVisible = false
        }
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            tvAddImage.setOnClickListener { pickImageLauncher.launch("image/*") }

            btnAdd.setOnClickListener {
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
                    viewModel.createExercise(createdExercise)
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
                is CreateExerciseDialogEvent.Created -> {
                    refresh()
                    dismiss()
                }

                is CreateExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()
}