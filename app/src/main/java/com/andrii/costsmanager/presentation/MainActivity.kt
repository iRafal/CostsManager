package com.andrii.costsmanager.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.andrii.costsmanager.R
import com.andrii.costsmanager.presentation.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.view_pager

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.navigation_costs -> {
                view_pager.currentItem = 0
                true
            }
            R.id.navigation_statistics -> {
                view_pager.currentItem = 1
                true
            }
            else -> false
        }
    }

    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            nav_view.selectedItemId = when (position) {
                0 -> R.id.navigation_costs
                else -> R.id.navigation_statistics
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        nav_view.apply {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.navigation_costs
        }

        view_pager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            listOf(CostsFragment.newInstance(), StatisticsFragment.newInstance())
        )
        view_pager.registerOnPageChangeCallback(onPageChangeCallback)
    }
}
