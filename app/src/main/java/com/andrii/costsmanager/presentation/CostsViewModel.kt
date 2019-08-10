package com.andrii.costsmanager.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.CategoryLocalRepository
import com.andrii.costsmanager.domain.model.Category
import com.andrii.costsmanager.presentation.model.CategoryModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CostsViewModel(application: Application) : AndroidViewModel(application) {

    private val localRepository: CategoryRepository
    private val disposable = CompositeDisposable()

    init {
        val db = CategoryDataBase.getInstance(application)
        localRepository = CategoryLocalRepository(db.categoryDao())
    }

    fun saveCategory(category: CategoryModel): Completable {
        return localRepository.insert(
            Category(
                name = category.name,
                price = category.price,
                date = category.date
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCategories(): Flowable<List<CategoryModel>> =
        localRepository.getAll()
            .map { list -> list.map { it.map() } }
            .observeOn(AndroidSchedulers.mainThread())

    private fun Category.map() = CategoryModel(
        id = id,
        name = name,
        price = price,
        date = date
    )

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}