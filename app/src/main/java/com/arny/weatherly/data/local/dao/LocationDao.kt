package com.arny.weatherly.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arny.weatherly.data.local.entity.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Update
    suspend fun update(location: LocationEntity)

    @Delete
    suspend fun delete(location: LocationEntity)

    @Query("DELETE FROM location WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM location")
    suspend fun deleteAll()

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun getById(id: String): LocationEntity?

    @Query("SELECT * FROM location ORDER BY id DESC LIMIT 1")
    fun getLatestLocation(): LocationEntity?

    @Query(
        """
        SELECT * FROM location 
        WHERE ABS(latitude - :latitude) < 0.01 
        AND ABS(longitude - :longitude) < 0.01 
        ORDER BY 
            (latitude - :latitude) * (latitude - :latitude) + 
            (longitude - :longitude) * (longitude - :longitude)
        LIMIT 1
    """
    )
    suspend fun getByLocation(latitude: Double, longitude: Double): LocationEntity?

    @Query("SELECT * FROM location")
    suspend fun getAll(): List<LocationEntity>
}