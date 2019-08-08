package com.andrii.costsmanager.storage

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
class CategoryRepository(val dao: CategoryDao) {

    fun getAll(): List<CategoryEntity> = dao.getAll()

    fun insert(category: CategoryEntity) {
        dao.insert(category)
    }

    fun deleteAll() {
        dao.deleteAll()
    }
}