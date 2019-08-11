package com.andrii.costsmanager.data

import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
interface CategoryRepository {
    fun getAll(): Flowable<List<Category>>

    fun insertOrReplace(category: Category): Completable

    fun deleteAll(): Completable
}