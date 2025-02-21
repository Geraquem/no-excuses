package com.mmfsin.noexcuses

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.ActivityMainBinding
import com.mmfsin.noexcuses.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.noexcuses.utils.BEDROCK_PARCELABLE_ARGS
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    private fun setNavigationDrawer() {
        getAppVersion()
        binding.apply {
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_default_routines -> openBedRockActivity(R.navigation.nav_graph_default_routines)
                    R.id.nav_my_routines -> openBedRockActivity(R.navigation.nav_graph_my_routines)
                    R.id.nav_exercises -> openBedRockActivity(R.navigation.nav_graph_exercises)
                    R.id.nav_fav_exercises -> openBedRockActivity(R.navigation.nav_graph_fav_exercises)
                    R.id.nav_stretching -> openBedRockActivity(R.navigation.nav_graph_stretchings)
                    R.id.nav_notes -> openBedRockActivity(R.navigation.nav_graph_notes)
                }
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    private fun getAppVersion() {
        try {
            val pInfo: PackageInfo =
                this.packageManager.getPackageInfo(this.packageName, 0)
            val version = pInfo.versionName
            binding.tvVersion.text = getString(R.string.drawer_version, version)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Error", e.printStackTrace().toString())
        }
    }

    fun openDrawer() = binding.drawerLayout.openDrawer(binding.navigationView)

    fun openBedRockActivity(
        navGraph: Int, strArgs: String? = null,
        booleanArgs: Boolean? = null,
        parcelable: Any? = null
    ) {
        val intent = Intent(this, BedRockActivity::class.java)
        strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
        booleanArgs?.let { intent.putExtra(BEDROCK_BOOLEAN_ARGS, booleanArgs) }
        parcelable?.let { intent.putExtra(BEDROCK_PARCELABLE_ARGS, parcelable as Parcelable) }
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, navGraph)
        startActivity(intent)
    }
}