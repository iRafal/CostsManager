package com.andrii.costsmanager.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andrii.costsmanager.R.id
import com.andrii.costsmanager.R.layout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            id.navigation_costs -> {
                showFragment(CostsFragment.newInstance())
                true
            }
            id.navigation_statistics -> {
                showFragment(StatisticsFragment.newInstance())
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        nav_view.selectedItemId = id.navigation_costs
    }

    private fun showFragment(target: Fragment) {
        supportFragmentManager.beginTransaction().replace(id.frag_container, target).commitAllowingStateLoss()
    }
}
