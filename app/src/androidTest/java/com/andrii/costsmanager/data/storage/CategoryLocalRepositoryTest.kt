package com.andrii.costsmanager.data.storage

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andrii.costsmanager.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Andrii Medvid on 8/12/2019.
 */
@RunWith(AndroidJUnit4::class)
class CategoryLocalRepositoryTest {

    private lateinit var db: CategoryDataBase
    private lateinit var repository: CategoryLocalRepository
    private val input = Category(name = "Category_1", price = 100)

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CategoryDataBase::class.java
        ).build()
        repository = CategoryLocalRepository(db.categoryDao())
    }

    @Test
    fun insertAndGetCategory() {
        repository.insertOrReplace(input)

        repository.getAll().subscribe { list ->
            val actual = list.firstOrNull()
            assertNotNull(actual)
            actual?.let {
                assertEquals(it.name, input.name)
                assertEquals(it.price, input.price)
                assertEquals(it.date, input.date)
            }
        }
    }

    @Test
    fun updateAndGetCategory() {
        repository.insertOrReplace(input)

        val updatedInput = input.copy(name = "Category_1_New", price = 200)
        repository.insertOrReplace(updatedInput)

        repository.getAll().subscribe { list ->
            val actual = list.firstOrNull()
            assertNotNull(actual)
            actual?.let {
                assertEquals(it.name, updatedInput.name)
                assertEquals(it.price, updatedInput.price)
                assertEquals(it.date, input.date)
            }
        }
    }

    @Test
    fun deleteAndGetCategory() {
        repository.insertOrReplace(input)
        repository.deleteAll()
        repository.getAll().subscribe { list -> assertNotNull(list) }
    }
}