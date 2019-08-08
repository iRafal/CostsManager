package com.andrii.costsmanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.andrii.costsmanager.R.layout
import kotlinx.android.synthetic.main.fragment_costs.toolbar

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class StatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}