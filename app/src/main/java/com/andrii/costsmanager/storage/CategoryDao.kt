package com.andrii.costsmanager.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_table")
    fun getAll(): List<CategoryEntity>

    @Insert
    fun insert(category: CategoryEntity)

    @Query("DELETE FROM category_table")
    fun deleteAll()
}