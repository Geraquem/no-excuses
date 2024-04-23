package com.mmfsin.noexcuses.base.bedrock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ActivityBedrockBinding
import com.mmfsin.noexcuses.utils.ROOT_ACTIVITY_NAV_GRAPH
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBedrockBinding

    var routineOpened: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBedrockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar()
        setUpNavGraph()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    private fun setUpNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.br_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = intent.getIntExtra(ROOT_ACTIVITY_NAV_GRAPH, -1)
        navController.apply { if (navGraph != -1) setGraph(navGraph) else error() }
    }

    fun setUpToolbar(title: String? = "") {
        binding.toolbar.apply {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            tvTitle.text = title
        }
    }

    fun rightIconToolbar(isVisible: Boolean, icon: Int? = null, action: () -> Unit = {}) {
        binding.toolbar.apply {
            ivRightIcon.isVisible = isVisible
            icon?.let { ivRightIcon.setImageResource(icon) }
            ivRightIcon.setOnClickListener { action() }
        }
    }

    private fun error() = showErrorDialog()
}