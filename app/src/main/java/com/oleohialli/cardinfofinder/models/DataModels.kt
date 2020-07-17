package com.oleohialli.cardinfofinder.models

import com.google.gson.annotations.SerializedName


data class ResponseDTO(
    var number: NumberObject,
    var scheme: String,
    var type: String,
    var brand: String,
    var country: CountryObject,
    var bank: BankObject
)

data class BankObject(
    var name: String,
    var url: String,
    var phone: String,
    var city: String
)

data class CountryObject(
    var numeric: String,
    var name: String,
    var emoji: String,
    var currency: String
)

data class NumberObject(
    var length: Int,
    var luhn: Boolean
)


data class ErrorBody(
    @SerializedName("ExceptionMessage") var serverMessage: String?,
    @SerializedName("Message") var message: String?
)