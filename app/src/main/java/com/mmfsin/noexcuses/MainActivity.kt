package com.mmfsin.noexcuses

import android.content.Intent
import android.net.Uri
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
        setNavigationDrawer()
        openDrawer()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    private fun setNavigationDrawer() {
        binding.apply {
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_default_routines -> openBedRockActivity(R.navigation.nav_graph_default_routines)
                    R.id.nav_my_routines -> openBedRockActivity(R.navigation.nav_graph_my_routines)
                    //////////////////////
                    R.id.nav_exercises -> openBedRockActivity(R.navigation.nav_graph_exercises)
                    R.id.nav_notes -> openBedRockActivity(R.navigation.nav_graph_notes)
                }
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    fun openDrawer() = binding.drawerLayout.openDrawer(binding.navigationView)

    fun openBedRockActivity(navGraph: Int, args: String? = null) {
        val intent = Intent(this, BedRockActivity::class.java)
        args?.let { intent.putExtra(BEDROCK_ARGS, args) }
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, navGraph)
        startActivity(intent)
    }
}