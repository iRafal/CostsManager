package com.andrii.costsmanager.data.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.dao.CategoryDao
import com.andrii.costsmanager.data.storage.entity.CategoryEntity
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CategoryLocalRepository(private val dao: CategoryDao) : CategoryRepository {

    override fun getAll(): LiveData<List<Category>> =
        Transformations.map(dao.getAll()) { entities -> entities.map { item -> item.map() } }

    override fun insertOrReplace(category: Category): Completable =
        dao.insertOrReplace(category.map())

    private fun Category.map() = CategoryEntity(id = id, name = name, price = price, date = date)
    private fun CategoryEntity.map() = Category(id = id, name = name, price = price, date = date)

    override fun deleteAll(): Completable = dao.deleteAll()
}