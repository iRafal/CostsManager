package com.andrii.costsmanager.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getItemCount(): Int = fragments.size
}