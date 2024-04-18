package com.mmfsin.noexcuses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mmfsin.noexcuses.databinding.ActivityMainBinding
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs.InfoDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var routineOpened: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(100)
        setTheme(R.style.Theme_Noexcuses)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Realm.init(this)
    }

    fun setUpToolbar(showBack: Boolean = true, title: String? = "") {
        binding.toolbar.apply {
            val toolbarTitle = if (showBack) title else getString(R.string.app_name)
            tvTitle.text = toolbarTitle

            ivBack.isVisible = showBack
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    fun rightIconToolbar(isVisible: Boolean, icon: Int? = null, action: () -> Unit = {}) {
        binding.toolbar.apply {
            ivRightIcon.isVisible = isVisible
            icon?.let { ivRightIcon.setImageResource(icon) }
            ivRightIcon.setOnClickListener { action() }
        }
    }
}