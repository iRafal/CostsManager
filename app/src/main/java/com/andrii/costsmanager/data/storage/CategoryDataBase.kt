package com.andrii.costsmanager.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andrii.costsmanager.data.storage.dao.CategoryDao
import com.andrii.costsmanager.util.SingletonHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.Date

/**
 * Created by Andrii Medvid on 8/5/2019.
 */
@Database(entities = [CategoryEntity::class], version = 2)
abstract class CategoryDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object : SingletonHolder<CategoryDataBase, Context>(
        creator = { context ->
            Room.databaseBuilder(
                context.applicationContext,
                CategoryDataBase::class.java,
                "Category_DataBase"
            )
                .addCallback(object : Callback() {

                    private var disposable: Disposable? = null

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.d("DB CREATED!")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Timber.d("DB OPENED!")
                        disposable = CategoryDataBase.getInstance(context).categoryDao().insert(

                            CategoryEntity(name = "Category_1", price = 1, date = Date()),
                            CategoryEntity(name = "Category_1", price = 11, date = Date()),
                            CategoryEntity(name = "Category_2", price = 2, date = Date()),
                            CategoryEntity(name = "Category_2", price = 22, date = Date()),
                            CategoryEntity(name = "Category_2", price = 222, date = Date()),
                            CategoryEntity(name = "Category_3", price = 3, date = Date()),
                            CategoryEntity(name = "Category_4", price = 4, date = Date()),
                            CategoryEntity(name = "Category_5", price = 5, date = Date()),
                            CategoryEntity(name = "Category_5", price = 55, date = Date())
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {
                                    Timber.d("DB IS populated")
                                    if (disposable?.isDisposed == false) disposable?.dispose()
                                },
                                {
                                    Timber.d("DB IS NOT populated")
                                    if (disposable?.isDisposed == false) disposable?.dispose()
                                }
                            )
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    )
}