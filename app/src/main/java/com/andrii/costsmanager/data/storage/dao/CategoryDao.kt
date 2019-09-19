package com.andrii.costsmanager.data.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.andrii.costsmanager.data.getDistinct
import com.andrii.costsmanager.data.storage.entity.CategoryEntity
import io.reactivex.Completable

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Dao
abstract class CategoryDao : BaseDao<CategoryEntity> {

    @Query("SELECT * FROM category_table")
    abstract fun getAll(): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM category_table WHERE name LIKE :name LIMIT 1")
    abstract fun getByName(name: String): LiveData<CategoryEntity>

    open fun getDistinctByName(name: String): LiveData<CategoryEntity> =
        getByName(name).getDistinct()

    @Query("SELECT * FROM category_table WHERE name LIKE :namePattern")
    abstract fun getAllByNamePattern(namePattern: String): LiveData<List<CategoryEntity>>

    @Query("DELETE FROM category_table")
    abstract fun deleteAll(): Completable
}