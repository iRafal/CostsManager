package com.andrii.costsmanager.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrii.costsmanager.R
import com.andrii.costsmanager.presentation.costs.CostsViewModel
import com.andrii.costsmanager.presentation.model.CategoryModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_statistics.progress_view
import kotlinx.android.synthetic.main.fragment_statistics.recycler_view
import kotlinx.android.synthetic.main.fragment_statistics.text_view_empty
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.LazyThreadSafetyMode.NONE

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class StatisticsFragment : Fragment(), StatisticsContract.View {

    private val viewModel: CostsViewModel by sharedViewModel()

    private val presenter: StatisticsContract.Presenter by lazy(mode = NONE) {
        StatisticsPresenter(
            this
        )
    }
    private val sectionAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_statistics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = sectionAdapter
        }

        presenter.onCreate()
    }

    override fun showCategories(map: Map<String, List<CategoryModel>>) {
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

    override fun loadCategories(loadListener: (List<CategoryModel>) -> Unit) {
        viewModel.getCategories().observe(
            this,
            Observer { categories -> loadListener(categories) }
        )
    }

    override fun showProgress() {
        progress_view.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        text_view_empty.visibility = View.GONE
    }

    override fun hideProgress() {
        progress_view.visibility = View.GONE
    }

    override fun showList() {
        recycler_view.visibility = View.VISIBLE
        text_view_empty.visibility = View.GONE
    }

    override fun hideList() {
        recycler_view.visibility = View.GONE
        text_view_empty.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}