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
import kotlinx.android.synthetic.main.fragment_statistics.progress_view
import kotlinx.android.synthetic.main.fragment_statistics.recycler_view
import kotlinx.android.synthetic.main.fragment_statistics.text_view_empty

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = sectionAdapter
        }

        setListData()
    }

    private var listDataDisposable: Disposable? = null

    private fun setListData() {
        showProgress()
        listDataDisposable?.let { if (it.isDisposed) it.dispose() }
        listDataDisposable = viewModel.getCategories().map { it.groupBy { item -> item.name } }.subscribe { map ->

            hideProgress()

            if (map.isEmpty()) {
                hideList()
            } else {
                showList()
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
                }
            }
        }
    }

    private fun showProgress() {
        progress_view.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        text_view_empty.visibility = View.GONE
    }

    private fun hideProgress() {
        progress_view.visibility = View.GONE
    }

    private fun showList() {
        recycler_view.visibility = View.VISIBLE
        text_view_empty.visibility = View.GONE
    }

    private fun hideList() {
        recycler_view.visibility = View.GONE
        text_view_empty.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listDataDisposable?.let { if (it.isDisposed) it.dispose() }
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}