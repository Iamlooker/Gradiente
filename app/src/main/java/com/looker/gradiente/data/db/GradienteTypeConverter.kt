package com.looker.gradiente.data.db

import androidx.room.TypeConverter
import com.looker.gradiente.model.ArgbStops
import com.looker.gradiente.model.GradientDirection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.PairSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GradienteTypeConverter {

    companion object {
        private val json = Json { encodeDefaults = true }
        private val argbStopsSerializer =
            ListSerializer(PairSerializer(Float.serializer(), Int.serializer()))
    }

    @TypeConverter
    fun directionToString(direction: GradientDirection): String =
        json.encodeToString(direction)

    @TypeConverter
    fun stringToDirection(string: String): GradientDirection =
        json.decodeFromString(string)

    @TypeConverter
    fun argbStopsToString(stops: ImmutableList<ArgbStops>): String =
        json.encodeToString(argbStopsSerializer, stops.toList())

    @TypeConverter
    fun stringToArgbStops(string: String): ImmutableList<ArgbStops> =
        json.decodeFromString(argbStopsSerializer, string).toImmutableList()
}