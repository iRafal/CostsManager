package com.andrii.costsmanager.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrii.costsmanager.util.SingletonHolder

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object : SingletonHolder<CategoryDataBase, Context>(
        creator = { context ->
            Room.databaseBuilder(
                context.applicationContext,
                CategoryDataBase::class.java,
                "Category_DataBase"
            ).build()
        }
    )
}