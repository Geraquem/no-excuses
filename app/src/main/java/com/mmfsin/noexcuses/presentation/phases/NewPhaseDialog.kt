package com.mmfsin.noexcuses.presentation.phases

import android.view.LayoutInflater
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogNewPhaseBinding

class NewPhaseDialog : BaseDialog<DialogNewPhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater): DialogNewPhaseBinding =
        DialogNewPhaseBinding.inflate(inflater)

    override fun setUI() {
        super.setUI()
    }

    override fun setListeners() {
        binding.apply {
            cancel.setOnClickListener { dismiss() }
            save.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty()) {
                    //Save in realm
                }
            }
        }
    }
}