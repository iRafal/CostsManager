package com.andrii.costsmanager.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "date") val date: Date)