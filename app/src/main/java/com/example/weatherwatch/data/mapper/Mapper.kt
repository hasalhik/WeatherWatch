package com.example.weatherwatch.data.mapper

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
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
import com.example.weatherwatch.domain.SettingConstants
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.domain.weather.entities.City
import com.example.weatherwatch.domain.weather.entities.Coord
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.weather.entities.Forecast
import com.example.weatherwatch.domain.weather.entities.ListForecast
import com.example.weatherwatch.domain.weather.entities.Main
import com.example.weatherwatch.domain.weather.entities.Sys
import com.example.weatherwatch.domain.weather.entities.SysForecast
import com.example.weatherwatch.domain.weather.entities.Weather
import com.example.weatherwatch.domain.weather.entities.Wind
import com.example.weatherwatch.presentation.WeatherApp
import com.google.gson.Gson
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

 class  Mapper @Inject constructor(private val application: Application) {

    private var prefs =
        application.getSharedPreferences(
            SettingConstants.APP_SETTINGS_NAME,
            Context.MODE_PRIVATE
        )
     private val onSharedPreferenceChangeListener= object : OnSharedPreferenceChangeListener{
         override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
             setPref()
         }
     }


    private var isTemperatureInFahrenheit: Boolean = false
    private var isWindInKmPerHour: Boolean = false
    private var isPressureInHpa : Boolean = false
    init {
        setPref()
        prefs.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    private fun setPref() {
        Log.d("Mapper", "setPref")

        isTemperatureInFahrenheit =
            prefs.getBoolean(
                SettingConstants.IS_TEMPERATURE_IN_FAHRENHEIT,
                false
            )

        isWindInKmPerHour =
            prefs.getBoolean(
                SettingConstants.IS_WIND_IN_KM_PER_HOUR,
                false
            )

        isPressureInHpa =
            prefs.getBoolean(
                SettingConstants.IS_PRESSURE_IN_HPA,
                false
            )

    }

    fun listPlaceInfoDtoToListPlaceInfo(
        listJsonContainer: List<PlaceInfoDto>
    ): List<PlaceInfo> {
        val result: MutableList<PlaceInfo> = mutableListOf()
        for (jsonObject in listJsonContainer ?: return result) {

            val priceInfo = Gson().fromJson(
                Gson().toJson(jsonObject),
                PlaceInfo::class.java
            )
            priceInfo.localNames = jsonObject.name
            if (!jsonObject.localNames?.ru.isNullOrEmpty())
                priceInfo.localNames = jsonObject.localNames?.ru

            result.add(priceInfo)
        }
        return result
    }

    fun placeInfoToDbModel(place: PlaceInfo): PlaceInfoDbModel =
        PlaceInfoDbModel(
            place.name,
            place.localNames,
            place.lat,
            place.lon,
            place.country,
            place.state,
            selected = place.selected
        )


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

    fun currentWeatherDtoToDbModel(it: CurrentWeatherDto, selected: Boolean, name: String?):CurrentWeatherDbModel {
       return CurrentWeatherDbModel(
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

        )}

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
        dt = it.dt?.let { it1 -> timestampToTime(it1) },
        sys = Sys(
            sunrise = it.sys?.sunrise,
            sunset = it.sys?.sunset
        ),
        timezone = it.timezone,
        name = it.name,
        cod = it.cod
    )

    private fun timestampToTime(time: Int): String {
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time.toLong())
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        return instant.atZone(zoneId).format(formatter)
    }

    private fun mainDtoToMainEntity(it: MainDto?) =
        Main(
            temp = kelvinOrFahrenheitTemp(it?.temp),
            feelsLike = kelvinOrFahrenheitTemp(it?.feelsLike),
            tempMin = it?.tempMin?.plus(KELVIN_TO_CELSIUS),
            tempMax = it?.tempMax?.plus(KELVIN_TO_CELSIUS),
            pressure = convertPressure(it?.pressure),
            seaLevel = it?.seaLevel,
            grndLevel = it?.grndLevel,
            humidity = it?.humidity.toString() + "%",
            tempKf = it?.tempKf,
        )

    private fun convertPressure(pressure: Int?) =
        if (isPressureInHpa) pressure.toString() + " ${application.resources.getString(R.string.hPa)}"
        else
            pressure?.times(HPA_TO_MMHG)?.toInt()
                .toString() + " ${application.resources.getString(R.string.mm_Hg)}"


    private fun kelvinOrFahrenheitTemp(temp: Double?) =
        if (isTemperatureInFahrenheit) kelvinToFahrenheit(temp) else
            kelvinToCelsius(temp)

    private fun kelvinToCelsius(temp: Double?): String {
        if (temp == null) return ""
        val res = temp.plus(KELVIN_TO_CELSIUS).toInt()
        return if (res > 0.0) "+$res°C"
        else "$res°C"
    }

    private fun kelvinToFahrenheit(temp: Double?): String {
        if (temp == null) return ""
        val res = ((1.8 * (temp - 273) + 32)).toInt()
        return if (res > 0.0) "+$res°F"
        else "$res°F"
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
            dt = it.dt,
            main = mainDtoToMainEntity(it.mainDto),
            weather = ArrayList(it.weather.map { weatherDtoToEntity(it) }),
            wind = it.windDto?.let { it1 -> windDbModelToEntity(it1) },
            visibility = it.visibility,
            pop = it.pop,
            sysForecast = SysForecast(it.sysForecastDto?.pod),
            textTime = it.dt?.let { it1 -> timestampToTime(it1) },
            textDate = it.dt?.let { it1 -> timestampToDay(it1) },
            textDayOfWeek = it.dt?.let { it1 -> timestampToDayOfWeek(it1) }
        )

    private fun timestampToDayOfWeek(timestamp: Int) =
        Calendar.getInstance().apply {
            time = Date(timestamp.toLong() * 1000)
        }
            .get(Calendar.DAY_OF_WEEK)

    private fun timestampToDay(time: Int): String {
        val res = timestampDate(time.toLong())
        val today = timestampDate(Instant.now().epochSecond)
        val tomorrow = timestampDate(
            Calendar.getInstance().apply { add(Calendar.DATE, 1) }.timeInMillis / 1000L
        )
        when (res) {
            today -> return application.resources.getString(R.string.today)
            tomorrow -> return application.resources.getString(R.string.tomorrow)
        }
        return res.substring(0, res.length - 5)
    }

    private fun timestampDate(time: Long): String {
        val zoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochSecond(time)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        return instant.atZone(zoneId).format(formatter)
    }

    private fun windDbModelToEntity(it: WindDto) =
        Wind(
            getWindSpeed(it.speed),
            deg = windDirectionDegreesToDirection(it.deg),
            gust = it.gust.toString()
        )

    private fun getWindSpeed(speed: Double?) =
        if (isWindInKmPerHour) String.format("%.1f", (speed?.times(3.6))) + " ${
            application.resources.getString(
                R.string.km_h
            )
        }, "
        else speed.toString() + " ${application.resources.getString(R.string.m_s)}, "

    private fun windDirectionDegreesToDirection(it: Int?): String {

        if (it == null) return ""
        return when (it + 0.0) {
            in 0.0..22.5 -> application.resources.getString(R.string.n_wind)
            in 22.5..67.5 -> application.resources.getString(R.string.ne_wind)
            in 67.5..112.5 -> application.resources.getString(R.string.e_wind)
            in 112.5..157.5 -> application.resources.getString(R.string.se_wind)
            in 157.5..202.5 -> application.resources.getString(R.string.s_wind)
            in 202.5..247.5 -> application.resources.getString(R.string.sw_wind)
            in 247.5..292.5 -> application.resources.getString(R.string.w_wind)
            in 292.5..337.5 -> application.resources.getString(R.string.nw_wind)
            in 337.5..360.0 -> application.resources.getString(R.string.n_wind)
            else -> ""

        }
    }


    companion object {
        const val KELVIN_TO_CELSIUS: Double = -273.15
        const val HPA_TO_MMHG: Double = 0.750062
    }
}