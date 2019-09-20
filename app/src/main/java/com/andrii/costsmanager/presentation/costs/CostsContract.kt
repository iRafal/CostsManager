package com.andrii.costsmanager.presentation.costs

import com.andrii.costsmanager.presentation.model.CategoryModel
import io.reactivex.Completable

interface CostsContract {
    interface View {

        fun showMessage(text: String)

        val categoryName: String

        val categoryPriceAsString: String

        fun updateAutocomplete(categoriesNames: List<String>)

        fun loadCategories(loadListener: (List<CategoryModel>) -> Unit)
    }

    interface Presenter {
        fun onCreate()

        fun onSubmitClick(response: (model: CategoryModel) -> Completable)

        fun onDestroy()
    }
}