package com.andrii.costsmanager.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_table")
    fun getAll(): Single<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: CategoryEntity): Completable

    @Query("DELETE FROM category_table")
    fun deleteAll(): Completable
}