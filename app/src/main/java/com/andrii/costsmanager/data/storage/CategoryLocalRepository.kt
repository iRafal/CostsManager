package com.andrii.costsmanager.data.storage

import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CategoryLocalRepository(private val dao: CategoryDao) : CategoryRepository {

    override fun getAll(): Flowable<List<Category>> = dao.getAll().map { list ->
        list.map { item ->
            Category(
                id = item.id,
                name = item.name,
                price = item.price,
                date = item.date
            )
        }
    }

    override fun getAllByNamePattern(namePattern: String): Single<List<Category>> =
        dao.getAllByNamePattern(namePattern).map { list -> list.map { it.map() } }

    override fun insert(category: Category): Completable = dao.insert(category.map())

    private fun Category.map() =
        CategoryEntity(
            id = id,
            name = name,
            price = price,
            date = date
        )

    private fun CategoryEntity.map() =
        Category(
            id = id,
            name = name,
            price = price,
            date = date
        )

    override fun deleteAll(): Completable = dao.deleteAll()
}