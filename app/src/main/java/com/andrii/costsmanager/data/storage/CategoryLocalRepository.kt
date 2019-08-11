package com.andrii.costsmanager.data.storage

import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.dao.CategoryDao
import com.andrii.costsmanager.data.storage.entity.CategoryEntity
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CategoryLocalRepository(private val dao: CategoryDao) : CategoryRepository {

    override fun getAll(): Flowable<List<Category>> = dao.getAll().map { list ->
        list.map { item -> item.map() }
    }

    override fun insertOrReplace(category: Category): Completable = dao.insertOrReplace(category.map())

    private fun Category.map() = CategoryEntity(id = id, name = name, price = price, date = date)
    private fun CategoryEntity.map() = Category(id = id, name = name, price = price, date = date)

    override fun deleteAll(): Completable = dao.deleteAll()
}