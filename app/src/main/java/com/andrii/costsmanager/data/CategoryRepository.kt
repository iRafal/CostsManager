package com.andrii.costsmanager.data

import androidx.lifecycle.LiveData
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
interface CategoryRepository {
    fun getAll(): LiveData<List<Category>>

    fun insertOrReplace(category: Category): Completable

    fun deleteAll(): Completable
}