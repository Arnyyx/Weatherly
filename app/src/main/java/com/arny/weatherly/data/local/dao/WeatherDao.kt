package com.arny.weatherly.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arny.weatherly.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    /**
     * Chèn một bản ghi thời tiết mới.
     * Nếu đã tồn tại (dựa trên id), thay thế bản ghi cũ.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)

    /**
     * Chèn nhiều bản ghi thời tiết.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weathers: List<WeatherEntity>)

    /**
     * Cập nhật một bản ghi thời tiết.
     */
    @Update
    suspend fun update(weather: WeatherEntity)

    /**
     * Xóa một bản ghi thời tiết.
     */
    @Delete
    suspend fun delete(weather: WeatherEntity)

    /**
     * Xóa bản ghi theo ID.
     */
    @Query("DELETE FROM weather WHERE id = :id")
    suspend fun deleteById(id: String)

    /**
     * Xóa toàn bộ dữ liệu trong bảng.
     */
    @Query("DELETE FROM weather")
    suspend fun deleteAll()

    /**
     * Lấy bản ghi thời tiết theo ID.
     */
    @Query("SELECT * FROM weather WHERE id = :id")
    suspend fun getById(id: String): WeatherEntity?

    /**
     * Lấy bản ghi thời tiết theo tọa độ (latitude và longitude).
     * Sử dụng khoảng cách gần đúng để tìm vị trí gần nhất.
     */
    @Query(
        """
        SELECT * FROM weather 
        WHERE ABS(latitude - :latitude) < 0.01 
        AND ABS(longitude - :longitude) < 0.01 
        AND ttl > :currentTime
        ORDER BY 
            (latitude - :latitude) * (latitude - :latitude) + 
            (longitude - :longitude) * (longitude - :longitude)
        LIMIT 1
    """
    )
    suspend fun getByLocation(
        latitude: Double,
        longitude: Double,
        currentTime: Long
    ): WeatherEntity?

    /**
     * Lấy tất cả các bản ghi thời tiết.
     */
    @Query("SELECT * FROM weather")
    suspend fun getAll(): List<WeatherEntity>

    /**
     * Lấy bản ghi thời tiết gần nhất theo thời gian cập nhật (dựa trên timestamp của current).
     * Giả sử bạn muốn lấy dữ liệu mới nhất.
     */
    @Query(
        """
        SELECT * FROM weather 
        ORDER BY current_timestamp DESC 
        LIMIT 1
    """
    )
    suspend fun getLatest(): WeatherEntity?
}