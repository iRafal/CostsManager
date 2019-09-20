package com.andrii.costsmanager.presentation.costs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.CategoryLocalRepository
import com.andrii.costsmanager.domain.model.Category
import com.andrii.costsmanager.presentation.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CostsViewModel(application: Application) : AndroidViewModel(application) {

    private val localRepository: CategoryRepository

    init {
        val db = CategoryDataBase.getInstance(application)
        localRepository = CategoryLocalRepository(db.categoryDao())
    }

    fun saveCategory(category: CategoryModel): Completable {
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

    fun getCategories(): LiveData<List<CategoryModel>> =
        Transformations.map(localRepository.getAll()) { categories -> categories.map { it.map() } }

    private fun Category.map() = CategoryModel(id = id, name = name, price = price, date = date)
}