package com.andrii.costsmanager.presentation.costs

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.domain.model.Category
import com.andrii.costsmanager.presentation.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CostsViewModelImpl(localRepository: CategoryRepository) : CostsViewModel(localRepository) {

    override fun saveCategory(category: CategoryModel): Completable {
        return localRepository.insertOrReplace(
            Category(
                name = category.name,
                price = category.price,
                date = category.date
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Timber.e(it) }
    }

    override fun getCategories(): LiveData<List<CategoryModel>> =
        Transformations.map(localRepository.getAll()) { categories -> categories.map { it.map() } }

    private fun Category.map() = CategoryModel(id = id, name = name, price = price, date = date)
}

abstract class CostsViewModel(protected val localRepository: CategoryRepository) : ViewModel() {
    abstract fun saveCategory(category: CategoryModel): Completable
    abstract fun getCategories(): LiveData<List<CategoryModel>>
}
