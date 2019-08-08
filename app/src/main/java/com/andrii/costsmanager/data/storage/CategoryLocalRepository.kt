package com.andrii.costsmanager.data.storage

import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.domain.model.Category
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CategoryLocalRepository(private val dao: CategoryDao) : CategoryRepository {

    override fun getAll(): Single<List<Category>> = dao.getAll().map { list ->
        list.map { item ->
            Category(
                id = item.id,
                name = item.name,
                price = item.price,
                date = item.date
            )
        }
    }

    override fun insert(category: Category): Completable =
        dao.insert(
            CategoryEntity(
                name = category.name,
                price = category.price,
                date = category.date
            )
        )


    override fun deleteAll(): Completable = dao.deleteAll()
}