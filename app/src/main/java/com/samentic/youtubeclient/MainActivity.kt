package com.samentic.youtubeclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.samentic.youtubeclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHost.navController

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                    navController.currentDestination?.id?.let { id ->
                        binding.tlMain.isVisible = id == R.id.subscriptionListFragment ||
                                id == R.id.playlistsFragment
                    }

                }
            },
            true
        )

        // region init BottomNav
        binding.tlMain.let { tlMain ->
            tlMain.addTab(
                tlMain.newTab().also {
                    it.setText(R.string.label_subscriptions)
                },
                true
            )
            tlMain.addTab(
                tlMain.newTab().also {
                    it.setText(R.string.label_playlists)
                }
            )
            tlMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (tab.position == 0) {
                        navController.popBackStack(R.id.subscriptionListFragment, false)
                    } else if (tab.position == 1) {
                        navController.navigate(R.id.playlistsFragment)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
        }
        // endregion init BottomNav
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.subscriptionListFragment -> {
                super.onBackPressed()
            }

            R.id.playlistsFragment -> {
                binding.tlMain.selectTab(binding.tlMain.getTabAt(0))
            }

            else -> {
                super.onBackPressed()
            }
        }

    }
}