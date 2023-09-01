package com.peterchege.composenewsapp.core.room.converters

import androidx.room.TypeConverter
import com.peterchege.composenewsapp.core.api.responses.Source

class SourceTypeConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name ?: ""
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}


