package com.arny.weatherly.data.remote.dto

import com.arny.weatherly.domain.model.CurrentWeather
import com.arny.weatherly.domain.model.DailyWeather
import com.arny.weatherly.domain.model.FeelsLike
import com.arny.weatherly.domain.model.HourlyWeather
import com.arny.weatherly.domain.model.Rain
import com.arny.weatherly.domain.model.Snow
import com.arny.weatherly.domain.model.Temperature
import com.arny.weatherly.domain.model.Weather
import com.arny.weatherly.domain.model.WeatherCondition
import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezoneOffset: Int,
    @SerializedName("current") val current: CurrentWeatherDto,
    @SerializedName("hourly") val hourly: List<HourlyWeatherDto>?,
    @SerializedName("daily") val daily: List<DailyWeatherDto>
)

data class CurrentWeatherDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Float,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("uvi") val uvIndex: Double,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("weather") val conditions: List<WeatherConditionDto>,
    @SerializedName("rain") val rain: RainDto?,
    @SerializedName("snow") val snow: SnowDto?
)

data class HourlyWeatherDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("uvi") val uvIndex: Double,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("weather") val conditions: List<WeatherConditionDto>,
    @SerializedName("pop") val precipitationProbability: Double,
    @SerializedName("rain") val rain: RainDto?,
    @SerializedName("snow") val snow: SnowDto?
)

data class DailyWeatherDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?,
    @SerializedName("moonrise") val moonrise: Long?,
    @SerializedName("moonset") val moonset: Long?,
    @SerializedName("moon_phase") val moonPhase: Double?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("temp") val temperature: TemperatureDto,
    @SerializedName("feels_like") val feelsLike: FeelsLikeDto,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("weather") val conditions: List<WeatherConditionDto>,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("pop") val precipitationProbability: Double,
    @SerializedName("rain") val rain: Double?,
    @SerializedName("snow") val snow: Double?,
    @SerializedName("uvi") val uvIndex: Double
)

data class WeatherConditionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class RainDto(
    @SerializedName("1h") val oneHour: Double?
)

data class SnowDto(
    @SerializedName("1h") val oneHour: Double?
)

data class TemperatureDto(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)

data class FeelsLikeDto(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)

fun WeatherDto.toDomain(): Weather = Weather(
    latitude = latitude,
    longitude = longitude,
    timezone = timezone,
    timezoneOffset = timezoneOffset,
    current = current.toDomain(),
    hourly = hourly?.map { it.toDomain() },
    daily = daily.map { it.toDomain() }
)

fun CurrentWeatherDto.toDomain(): CurrentWeather = CurrentWeather(
    timestamp = timestamp,
    sunrise = sunrise,
    sunset = sunset,
    temperature = temperature,
    feelsLike = feelsLike,
    pressure = pressure,
    humidity = humidity,
    dewPoint = dewPoint,
    uvIndex = uvIndex,
    clouds = clouds,
    visibility = visibility,
    windSpeed = windSpeed,
    windDirection = windDirection,
    windGust = windGust,
    conditions = conditions.map { it.toDomain() },
    rain = rain?.toDomain(),
    snow = snow?.toDomain()
)

fun HourlyWeatherDto.toDomain(): HourlyWeather = HourlyWeather(
    timestamp = timestamp,
    temperature = temperature,
    feelsLike = feelsLike,
    pressure = pressure,
    humidity = humidity,
    dewPoint = dewPoint,
    uvIndex = uvIndex,
    clouds = clouds,
    visibility = visibility,
    windSpeed = windSpeed,
    windDirection = windDirection,
    windGust = windGust,
    conditions = conditions.map { it.toDomain() },
    precipitationProbability = precipitationProbability,
    rain = rain?.toDomain(),
    snow = snow?.toDomain()
)

fun DailyWeatherDto.toDomain(): DailyWeather = DailyWeather(
    timestamp = timestamp,
    sunrise = sunrise,
    sunset = sunset,
    moonrise = moonrise,
    moonset = moonset,
    moonPhase = moonPhase,
    summary = summary,
    temperature = temperature.toDomain(),
    feelsLike = feelsLike.toDomain(),
    pressure = pressure,
    humidity = humidity,
    dewPoint = dewPoint,
    windSpeed = windSpeed,
    windDirection = windDirection,
    windGust = windGust,
    conditions = conditions.map { it.toDomain() },
    clouds = clouds,
    precipitationProbability = precipitationProbability,
    rain = rain,
    snow = snow,
    uvIndex = uvIndex
)

fun WeatherConditionDto.toDomain(): WeatherCondition = WeatherCondition(
    id = id,
    main = main,
    description = description,
    icon = icon
)

fun RainDto.toDomain(): Rain = Rain(
    oneHour = oneHour
)

fun SnowDto.toDomain(): Snow = Snow(
    oneHour = oneHour
)

fun TemperatureDto.toDomain(): Temperature = Temperature(
    day = day,
    min = min,
    max = max,
    night = night,
    evening = evening,
    morning = morning
)

fun FeelsLikeDto.toDomain(): FeelsLike = FeelsLike(
    day = day,
    night = night,
    evening = evening,
    morning = morning
)