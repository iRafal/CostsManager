package com.andrii.costsmanager.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
@Entity(tableName = "category_table")
@TypeConverters(TypeConverter::class)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "date") val date: Date)