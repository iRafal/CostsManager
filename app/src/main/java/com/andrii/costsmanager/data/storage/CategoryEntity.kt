package com.andrii.costsmanager.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
@Entity(tableName = "category_table")
@TypeConverters(DateConverter::class)
data class CategoryEntity(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "date") val date: Date)