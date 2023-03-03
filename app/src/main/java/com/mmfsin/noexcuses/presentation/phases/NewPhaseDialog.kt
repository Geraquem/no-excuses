package com.mmfsin.noexcuses.presentation.phases

import android.view.LayoutInflater
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogNewPhaseBinding

class NewPhaseDialog : BaseDialog<DialogNewPhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater): DialogNewPhaseBinding =
        DialogNewPhaseBinding.inflate(inflater)
}