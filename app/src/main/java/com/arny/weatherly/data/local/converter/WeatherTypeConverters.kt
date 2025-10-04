package com.arny.weatherly.data.local.converter

import androidx.room.TypeConverter
import com.arny.weatherly.domain.model.CurrentWeather
import com.arny.weatherly.domain.model.DailyWeather
import com.arny.weatherly.domain.model.FeelsLike
import com.arny.weatherly.domain.model.HourlyWeather
import com.arny.weatherly.domain.model.Rain
import com.arny.weatherly.domain.model.Snow
import com.arny.weatherly.domain.model.Temperature
import com.arny.weatherly.domain.model.WeatherCondition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromCurrentWeather(current: CurrentWeather?): String? {
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrentWeather(json: String?): CurrentWeather? {
        return json?.let { gson.fromJson(it, CurrentWeather::class.java) }
    }

    @TypeConverter
    fun fromHourlyWeatherList(hourly: List<HourlyWeather>?): String? {
        return gson.toJson(hourly)
    }

    @TypeConverter
    fun toHourlyWeatherList(json: String?): List<HourlyWeather>? {
        return json?.let {
            gson.fromJson(it, object : TypeToken<List<HourlyWeather>>() {}.type)
        }
    }

    @TypeConverter
    fun fromDailyWeatherList(daily: List<DailyWeather>?): String? {
        return gson.toJson(daily)
    }

    @TypeConverter
    fun toDailyWeatherList(json: String?): List<DailyWeather>? {
        return json?.let {
            gson.fromJson(it, object : TypeToken<List<DailyWeather>>() {}.type)
        }
    }

    @TypeConverter
    fun fromWeatherConditionList(conditions: List<WeatherCondition>?): String? {
        return gson.toJson(conditions)
    }

    @TypeConverter
    fun toWeatherConditionList(json: String?): List<WeatherCondition>? {
        return json?.let {
            gson.fromJson(it, object : TypeToken<List<WeatherCondition>>() {}.type)
        }
    }

    @TypeConverter
    fun fromRain(rain: Rain?): String? {
        return gson.toJson(rain)
    }

    @TypeConverter
    fun toRain(json: String?): Rain? {
        return json?.let { gson.fromJson(it, Rain::class.java) }
    }

    @TypeConverter
    fun fromSnow(snow: Snow?): String? {
        return gson.toJson(snow)
    }

    @TypeConverter
    fun toSnow(json: String?): Snow? {
        return json?.let { gson.fromJson(it, Snow::class.java) }
    }

    @TypeConverter
    fun fromTemperature(temperature: Temperature?): String? {
        return gson.toJson(temperature)
    }

    @TypeConverter
    fun toTemperature(json: String?): Temperature? {
        return json?.let { gson.fromJson(it, Temperature::class.java) }
    }

    @TypeConverter
    fun fromFeelsLike(feelsLike: FeelsLike?): String? {
        return gson.toJson(feelsLike)
    }

    @TypeConverter
    fun toFeelsLike(json: String?): FeelsLike? {
        return json?.let { gson.fromJson(it, FeelsLike::class.java) }
    }
}