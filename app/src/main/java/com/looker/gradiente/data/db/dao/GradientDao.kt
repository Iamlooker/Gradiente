package com.looker.gradiente.data.db.dao

import androidx.room.*
import com.looker.gradiente.model.Gradient
import kotlinx.coroutines.flow.Flow

@Dao
interface GradientDao {

    @Query("SELECT * FROM gradient")
    fun getAllGradients(): Flow<List<Gradient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gradient: Gradient)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(gradient: Gradient)

    @Query("SELECT * FROM gradient WHERE id = :id")
    suspend fun getGradient(id: Int): Gradient

    @Delete
    suspend fun delete(gradient: Gradient)

}