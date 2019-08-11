package com.andrii.costsmanager.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrii.costsmanager.data.storage.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Dao
abstract class CategoryDao {
    @Query("SELECT * FROM category_table")
    abstract fun getAll(): Flowable<List<CategoryEntity>>

    @Query("SELECT * FROM category_table WHERE name LIKE :name LIMIT 1")
    abstract fun getByName(name: String): Flowable<CategoryEntity>
    open fun getDistinctByName(name: String): Flowable<CategoryEntity> = getByName(name).distinctUntilChanged()

    @Query("SELECT * FROM category_table WHERE name LIKE :namePattern")
    abstract fun getAllByNamePattern(namePattern: String): Single<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(category: CategoryEntity): Completable

        @Insert
    abstract fun insert(vararg entities: CategoryEntity): Completable

//    @Insert
//    fun insert(entities: List<T>): Completable

    @Query("DELETE FROM category_table")
    abstract fun deleteAll(): Completable
}