package com.andrii.costsmanager.data.storage.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.entity.CategoryEntity
import com.andrii.costsmanager.util.TestLifecycleOwner
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

/**
 * Created by Andrii Medvid on 8/12/2019.
 */
@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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

        dao.getAll().observe(
            TestLifecycleOwner(),
            Observer { list ->
                val actual = list.firstOrNull()
                assertNotNull(actual)
                actual?.let {
                    assertEquals(it.name, input.name)
                    assertEquals(it.price, input.price)
                    assertEquals(it.date, input.date)
                }
            }
        )
    }

    @Test
    fun updateAndGetCategory() {
        dao.insertOrReplace(input)

        val updatedInput = input.copy(name = "Category_1_New", price = 200)
        dao.insertOrReplace(updatedInput)

        dao.getAll().observe(
            TestLifecycleOwner(),
            Observer { list ->
                val actual = list.firstOrNull()
                assertNotNull(actual)
                actual?.let {
                    assertEquals(it.name, updatedInput.name)
                    assertEquals(it.price, updatedInput.price)
                    assertEquals(it.date, input.date)
                }
            }
        )
    }

    fun deleteAndGetCategory() {
        dao.insertOrReplace(input)
        dao.deleteAll()
        dao.getAll().observe(TestLifecycleOwner(), Observer { list -> assertNotNull(list) })
    }
}

