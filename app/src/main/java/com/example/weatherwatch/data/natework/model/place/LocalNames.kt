package com.example.weatherwatch.data.natework.model.place

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class LocalNames(


    @SerializedName("ru")
    var ru: String? = null,
    @SerializedName("fr")
    var fr: String? = null,
    @SerializedName("en")
    var en: String? = null,
    @SerializedName("feature_name")
    var featureName: String? = null,
    @SerializedName("ascii")
    var ascii: String? = null

)