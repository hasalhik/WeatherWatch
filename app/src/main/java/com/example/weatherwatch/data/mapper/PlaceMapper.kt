package com.example.weatherwatch.data.mapper

import android.util.Log
import com.example.weatherwatch.R
import com.example.weatherwatch.data.database.CurrentWeatherDbModel
import com.example.weatherwatch.data.database.ForecastDbModel
import com.example.weatherwatch.data.database.PlaceInfoDbModel
import com.example.weatherwatch.data.natework.model.forecast.ForecastDto
import com.example.weatherwatch.data.natework.model.forecast.ListForecastDto
import com.example.weatherwatch.data.natework.model.forecast.MainDto
import com.example.weatherwatch.data.natework.model.place.PlaceInfoDto
import com.example.weatherwatch.data.natework.model.weather.CurrentWeatherDto
import com.example.weatherwatch.data.natework.model.weather.WeatherDto
import com.example.weatherwatch.data.natework.model.weather.WindDto
import com.example.weatherwatch.domain.weather.entities.*
import com.example.weatherwatch.domain.place.PlaceInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PlaceMapper @Inject constructor() {


    fun listPlaceInfoDtoToListPlaceInfo(
        listJsonContainer: List<PlaceInfoDto>
    ): List<PlaceInfo> {
        val result: MutableList<PlaceInfo> = mutableListOf()
        for (jsonObject in listJsonContainer ?: return result) {

            Log.d("listJsonContainerToToListPlaceInfo", "jsonObject ${jsonObject.toString()}")
            val priceInfo = Gson().fromJson(
                Gson().toJson(jsonObject),
                PlaceInfo::class.java
            )
            priceInfo.localNames = jsonObject.name
            if (!jsonObject.localNames?.ru.isNullOrEmpty())
                priceInfo.localNames = jsonObject.localNames?.ru

            Log.d("listJsonContainerToToListPlaceInfo", "jsonObject ${priceInfo.toString()}")
            result.add(priceInfo)
        }
        return result
    }

    fun placeInfoToDbModel(place: PlaceInfo): PlaceInfoDbModel {
        Log.d(
            "placeInfoToDbModel", "place ${
                PlaceInfoDbModel(
                    place.name,
                    place.localNames,
                    place.lat,
                    place.lon,
                    place.country,
                    place.state,
                    selected = place.selected
                ).toString()
            }"
        )


        return PlaceInfoDbModel(
            place.name,
            place.localNames,
            place.lat,
            place.lon,
            place.country,
            place.state,
            selected = place.selected
        )
    }


    fun mapListDbModelToListEntity(it: List<PlaceInfoDbModel>) = it.map {
        dbModelToPlaceInfo(it)
    }

    private fun dbModelToPlaceInfo(it: PlaceInfoDbModel) = PlaceInfo(

        it.name,
        it.localNames,
        it.lat,
        it.lon,
        it.country,
        it.state,
        it.id,
        selected = it.selected
    )

    fun listCurrentWeatherDtoToListDbModel(listDto: List<CurrentWeatherDto>): List<CurrentWeatherDbModel> =
        listDto.map {
            currentWeatherDtoToDbModel(it, false, it.name)
        }

    fun currentWeatherDtoToDbModel(it: CurrentWeatherDto, selected: Boolean, name: String?) =
        CurrentWeatherDbModel(
            coord = it.coordDto,
            weather = it.weather,
            base = it.base,
            main = it.mainDto,
            visibility = it.visibility,
            wind = it.windDto,
            dt = it.dt,
            sys = it.sys,
            timezone = it.timezone,
            name = name ?: it.name,
            cod = it.cod,
            selected = selected

        )

    fun listCurrentWeatherDbModelToListEntity(listDBModel: List<CurrentWeatherDbModel>): List<CurrentWeather> =
        listDBModel.map {
            currentWeatherDbModelToEntity(it)
        }

    fun currentWeatherDbModelToEntity(it: CurrentWeatherDbModel) = CurrentWeather(
        coord =
        Coord(
            it.coord?.lon,
            it.coord?.lat
        ),
        id = it.id,
        weather = ArrayList(it.weather.map { weatherDtoToEntity(it) }),
        base = it.base,
        main = mainDtoToMainEntity(it.main),
        visibility = it.visibility,
        wind = it.wind?.let { it1 -> windDbModelToEntity(it1) } ?: Wind(),
        dt = it.dt?.let { it1 -> dateTime(it1) },
        sys = Sys(
            sunrise = it.sys?.sunrise,
            sunset = it.sys?.sunset
        ),
        timezone = it.timezone,
        name = it.name,
        cod = it.cod
    )

    private fun dateTime(time: Int): String {
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time.toLong())
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        return instant.atZone(zoneId).format(formatter)
    }

    private fun mainDtoToMainEntity(it: MainDto?) =
        Main(
            temp = kelvinToCelsius(it?.temp),
            feelsLike = kelvinToCelsius(it?.feelsLike),
            tempMin = it?.tempMin?.plus(KELVIN_TO_CELSIUS),
            tempMax = it?.tempMax?.plus(KELVIN_TO_CELSIUS),
            pressure = it?.pressure?.times(HPA_TO_MMHG)?.toInt().toString() + " мм рт. ст.",
            seaLevel = it?.seaLevel,
            grndLevel = it?.grndLevel,
            humidity = it?.humidity.toString() + "%",
            tempKf = it?.tempKf,
        )

    private fun kelvinToCelsius(temp: Double?): String {
        if (temp == null) return ""
        val res = temp.plus(KELVIN_TO_CELSIUS).toInt()
        if (res > 0.0) return "+$res°C"
        else return "$res°C"
    }

    private fun weatherDtoToEntity(it: WeatherDto): Weather =
        Weather(it.id, it.main, it.description, it.icon)


    fun forecastDtoToDbModel(it: ForecastDto): ForecastDbModel =
        ForecastDbModel(
            cod = it.cod,
            message = it.message,
            cnt = it.cnt,
            listForecastDto = it.listForecastDto,
            cityDto = it.cityDto
        )


    fun forecastDbModelToEntity(it: ForecastDbModel): Forecast =
        Forecast(
            cod = it.cod,
            message = it.message,
            cnt = it.cnt,
            listForecast = ArrayList(it.listForecastDto.map { listForecastDtoToEntity(it) }),
            city = City(
                id = it.cityDto?.id,
                name = it.cityDto?.name,
                coord = Coord(it.cityDto?.coordDto?.lon, it.cityDto?.coordDto?.lat),
                country = it.cityDto?.country,
                population = it.cityDto?.population,
                timezone = it.cityDto?.timezone,
                sunrise = it.cityDto?.sunrise,
                sunset = it.cityDto?.sunset
            ),
            id = it.id
        )

    private fun listForecastDtoToEntity(it: ListForecastDto) =
        ListForecast(
            dt = it.dt?.let { it1 -> dateDay(it1) },
            main = mainDtoToMainEntity(it.mainDto),
            weather = ArrayList(it.weather.map { weatherDtoToEntity(it) }),
            wind = it.windDto?.let { it1 -> windDbModelToEntity(it1) },
            visibility = it.visibility,
            pop = it.pop,
            sysForecast = SysForecast(it.sysForecastDto?.pod),
            dtTxt = it.dt?.let { it1 -> dateTime(it1) }
        )

    private fun dateDay(time: Int): String {
        val res = timestampDate(time.toLong())
        val today = timestampDate(Instant.now().epochSecond)
        val tomorrow = timestampDate(
            Calendar.getInstance().apply { add(Calendar.DATE, 1) }.timeInMillis / 1000L
        )
        when (res) {
            today -> return "Сегодня"
            tomorrow -> return "Завтра"
        }
        return res.substring(0, res.length - 5)
    }

    private fun timestampDate(time: Long): String {
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        val res = instant.atZone(zoneId).format(formatter)
        return res
    }

    private fun windDbModelToEntity(it: WindDto) =
        Wind(
            it.speed.toString() + " м/с, ",
            deg = windDirectionDegreesToDirection(it.deg),
            gust = it.gust.toString()
        )

    private fun windDirectionDegreesToDirection(it: Int?): String {

        if (it == null) return ""
        return when (it + 0.0) {
            in 0.0..22.5 -> "↑ С"
            in 22.5..67.5 -> "↗ СВ"
            in 67.5..112.5 -> "→ В"
            in 112.5..157.5 -> "↘ ЮВ"
            in 157.5..202.5 -> "↓ Ю"
            in 202.5..247.5 -> "↙ ЮЗ"
            in 247.5..292.5 -> "← З"
            in 292.5..337.5 -> "↖ СЗ"
            in 337.5..360.0 -> "↑ С"
            else -> ""

        }
    }


    companion object {
        const val KELVIN_TO_CELSIUS: Double = -273.15
        const val HPA_TO_MMHG: Double = 0.750062
    }
}