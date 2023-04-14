package com.looker.gradiente.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.looker.gradiente.data.db.dao.GradientDao
import com.looker.gradiente.model.Gradient

@Database(entities = [Gradient::class], version = 1)
@TypeConverters(GradienteTypeConverter::class)
abstract class GradienteDatabase : RoomDatabase() {
    abstract fun gradientDao(): GradientDao

    companion object {
        private var INSTANCE: GradienteDatabase? = null

        fun create(context: Context): GradienteDatabase = INSTANCE ?: kotlin.run {
            val gradienteDatabase = Room.databaseBuilder(
                context = context,
                klass = GradienteDatabase::class.java,
                name = "gradient_database"
            )
                .createFromAsset("gradiente.db")
                .build()
            INSTANCE = gradienteDatabase
            gradienteDatabase
        }
    }
}