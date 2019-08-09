package com.andrii.costsmanager.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrii.costsmanager.R
import com.andrii.costsmanager.presentation.CostsViewModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_statistics.recycler_view

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class StatisticsFragment : Fragment() {

    private lateinit var viewModel: CostsViewModel
    private val sectionAdapter = SectionedRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(CostsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_statistics, container, false)

    private var listDataDisposable: Disposable? = null

    private fun setListData() {
        listDataDisposable?.let { if (it.isDisposed) it.dispose() }

        listDataDisposable = viewModel.getCategories().map { it.groupBy { item -> item.name } }.subscribe { map ->
            sectionAdapter.removeAllSections()
            map.forEach {
                sectionAdapter.addSection(
                    ExpandableContactsSection(
                        title = it.key,
                        list = it.value,
                        onItemClick = { sectionAdapter.notifyDataSetChanged() },
                        expanded = true
                    )
                )
                recycler_view.layoutManager = LinearLayoutManager(activity)
                recycler_view.adapter = sectionAdapter
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setListData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listDataDisposable?.let { if (it.isDisposed) it.dispose() }
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}