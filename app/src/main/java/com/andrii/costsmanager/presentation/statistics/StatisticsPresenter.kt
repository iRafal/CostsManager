package com.andrii.costsmanager.presentation.statistics


/**
 * Created by Andrii Medvid
 */
class StatisticsPresenter(
    private val view: StatisticsContract.View
) : StatisticsContract.Presenter {

    override fun onCreate() {
        view.showProgress()

        view.loadCategories { categories ->
            val map = categories.groupBy { item -> item.name }

            view.hideProgress()
            if (map.isEmpty()) {
                view.hideList()
            } else {
                view.showList()
                view.showCategories(map)
            }
        }
    }
}