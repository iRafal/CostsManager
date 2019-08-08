package com.andrii.costsmanager.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrii.costsmanager.model.CategoryItem

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Database(entities = [CategoryItem::class], version = 1)
abstract class CategoryDataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDataBase? = null

        fun getDatabase(context: Context): CategoryDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryDataBase::class.java,
                    "Category_DataBase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}