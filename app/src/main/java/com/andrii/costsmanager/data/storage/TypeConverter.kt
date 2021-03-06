package com.andrii.costsmanager.data.storage

import androidx.room.TypeConverter
import java.util.*

object TypeConverter {
    @TypeConverter
    @JvmStatic
    fun toDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date?): Long? = date?.time
}
