package com.karan.cloudwalkerpoc1.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class OtpResponse(
    @SerializedName("ErrorMessage") var msg: String? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("sms_service") var typeService: String? = null
)

data class OtpDtoItem(
    @SerializedName("ErrorMessage") var msg: String? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("sms_service") var typeService: String? = null
)

data class OtpRequest(
    @field:Json(name = "mobile") val mobile: String? = null,
    @field:Json(name = "otp") val otp: String? = null,
    @field:Json(name = "password") val password: String? = null
)