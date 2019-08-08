package com.andrii.costsmanager.presentation

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.CategoryLocalRepository
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
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

    @SuppressLint("CheckResult")
    fun saveCategory(category: CategoryModel): Completable {
        return Completable.fromAction {
            localRepository.insert(
                Category(
                    name = category.name,
                    price = category.price,
                    date = category.date
                )
            )
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}