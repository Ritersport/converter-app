package nsu.leorita.exchanges.data.services.models

import com.google.gson.annotations.SerializedName

data class Valute(
    @SerializedName("CharCode")
    var code: String,

    @SerializedName("Nominal")
    var nominal: Int,

    @SerializedName("Name")
    var name: String,

    @SerializedName("Value")
    var value: Float,

    @SerializedName("Previous")
    var previousValue: Float,
)
