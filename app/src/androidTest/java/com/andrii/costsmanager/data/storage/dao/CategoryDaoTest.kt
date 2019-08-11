package com.andrii.costsmanager.data.storage.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.entity.CategoryEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Andrii Medvid on 8/12/2019.
 */
@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    private lateinit var db: CategoryDataBase
    private lateinit var dao: CategoryDao
    private val input = CategoryEntity(name = "Category_1", price = 100)

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CategoryDataBase::class.java
        ).build()
        dao = db.categoryDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetCategory() {
        dao.insertOrReplace(input)

        dao.getAll().subscribe { list ->
            val actual = list.firstOrNull()
            Assert.assertNotNull(actual)
            actual?.let {
                Assert.assertEquals(it.name, input.name)
                Assert.assertEquals(it.price, input.price)
                Assert.assertEquals(it.date, input.date)
            }
        }
    }

    @Test
    fun updateAndGetCategory() {
        dao.insertOrReplace(input)

        val updatedInput = input.copy(name = "Category_1_New", price = 200)
        dao.insertOrReplace(updatedInput)

        dao.getAll().subscribe { list ->
            val actual = list.firstOrNull()
            Assert.assertNotNull(actual)
            actual?.let {
                Assert.assertEquals(it.name, updatedInput.name)
                Assert.assertEquals(it.price, updatedInput.price)
                Assert.assertEquals(it.date, input.date)
            }
        }
    }

    @Test
    fun deleteAndGetCategory() {
        dao.insertOrReplace(input)
        dao.deleteAll()
        dao.getAll().subscribe { list -> Assert.assertNotNull(list) }
    }
}