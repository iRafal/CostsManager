package com.andrii.costsmanager.presentation.statistics

import com.andrii.costsmanager.presentation.model.CategoryModel


/**
 * Created by Andrii Medvid
 */
interface StatisticsContract {
    interface View {
        fun showProgress()
        fun hideProgress()

        fun showList()
        fun hideList()

        fun loadCategories(loadListener: (List<CategoryModel>) -> Unit)

        fun showCategories(map: Map<String, List<CategoryModel>>)
    }

    interface Presenter {
        fun onCreate()
    }
}