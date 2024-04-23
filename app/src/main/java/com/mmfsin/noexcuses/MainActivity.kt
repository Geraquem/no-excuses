package com.mmfsin.noexcuses

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.ActivityMainBinding
import com.mmfsin.noexcuses.utils.BEDROCK_ARGS
import com.mmfsin.noexcuses.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    fun openBedRockActivity(navGraph: Int, args: String? = null) {
        val intent = Intent(this, BedRockActivity::class.java)
        args?.let { intent.putExtra(BEDROCK_ARGS, args) }
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, navGraph)
        startActivity(intent)
    }
}