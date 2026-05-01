package io.github.t1geryan.penny.data.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class LocalDateTimeConverter {

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime) = dateTime.format(LocalDateTime.Formats.ISO)

    @TypeConverter
    fun toLocalDateTime(iso: String) = LocalDateTime.parse(iso, LocalDateTime.Formats.ISO)
}
