package com.mmfsin.noexcuses

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.mmfsin.noexcuses.databinding.ActivityMainBinding
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

        changeStatusBar()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    fun setUpToolbar(showBack: Boolean = true, title: String? = "") {
        binding.toolbar.apply {
            val toolbarTitle = if (showBack) title else getString(R.string.app_name)
            tvTitle.text = toolbarTitle

            val visible = if (showBack) View.VISIBLE else View.INVISIBLE
            ivLogo.isVisible = !showBack
            ivBack.visibility = visible
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