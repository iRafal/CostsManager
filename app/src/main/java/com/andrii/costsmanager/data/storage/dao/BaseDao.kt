package com.andrii.costsmanager.data.storage.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.andrii.costsmanager.data.storage.entity.BaseEntity
import io.reactivex.Completable

/**
 * Created by Andrii Medvid on 8/11/2019.
 */
interface BaseDao<T: BaseEntity> {

    @Insert
    fun insert(entity: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(entity: T): Completable

    @Insert
    fun insertAll(vararg entities: T): Completable

    @Insert
    fun insertAll(entities: List<T>): Completable

    @Update
    fun update(entity: T): Completable

    @Delete
    fun delete(entity: T): Completable
}