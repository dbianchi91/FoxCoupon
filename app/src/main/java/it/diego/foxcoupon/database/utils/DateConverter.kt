package it.diego.foxcoupon.database.utils

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}