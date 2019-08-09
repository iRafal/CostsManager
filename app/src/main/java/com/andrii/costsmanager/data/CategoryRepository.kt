package com.andrii.costsmanager.data

import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
interface CategoryRepository {
    fun getAll(): Flowable<List<Category>>

    fun getAllByNamePattern(namePattern: String): Single<List<Category>>

    fun insert(category: Category): Completable

    fun deleteAll(): Completable
}