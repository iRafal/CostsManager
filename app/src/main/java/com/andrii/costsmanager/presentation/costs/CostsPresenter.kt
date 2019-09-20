package com.andrii.costsmanager.presentation.costs

import com.andrii.costsmanager.presentation.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.Date

class CostsPresenter(private val view: CostsContract.View):
    CostsContract.Presenter {

    private var onSubmitDataFetchDisposable: Disposable? = null

    override fun onCreate() {
        view.loadCategories { categories ->
            processCategories(categories)
        }
    }

    private fun processCategories(categories: List<CategoryModel>) {
        view.updateAutocomplete(categories.map { item -> item.name }.distinct())
    }

    override fun onSubmitClick(response: (model: CategoryModel) -> Completable) {
        onSubmitDataFetchDisposable = response(
            CategoryModel(
                name = view.categoryName,
                price = view.categoryPriceAsString.toInt(),
                date = Date()
            )
        ).subscribe(
            {
                view.showMessage("Category is Saved")
                Timber.d("Category is Saved")
                onSubmitDataFetchDisposable?.safeDispose()
            },
            {
                view.showMessage("Category is NOT saved")
                Timber.e(it, "Category is NOT saved")
                onSubmitDataFetchDisposable?.safeDispose()
            })
    }

    override fun onDestroy() {
        onSubmitDataFetchDisposable?.safeDispose()
    }

    private fun Disposable?.safeDispose() {
        if(this?.isDisposed == true) this.dispose()
    }
}