package com.andrii.costsmanager.data.storage.dao

import io.reactivex.Completable

/**
 * Created by Andrii Medvid on 8/11/2019.
 */
interface BaseDao<T> {

//    @Insert
    fun insert(entity: T): Completable

//    @Insert
//    fun insert(vararg entities: T): Completable

//    @Insert
//    fun insert(entities: List<T>): Completable

//    @Update
    fun update(entity: T): Completable

//    @Delete
    fun delete(entity: T): Completable
}