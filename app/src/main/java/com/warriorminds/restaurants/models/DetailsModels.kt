package com.warriorminds.restaurants.models

data class DetailsResponse(val meta: Meta,
                           val response: Result)

data class Result(val venue: Details)

data class Details(val id: String,
                   val name: String,
                   val contact: Contact?,
                   val location: Location,
                   val canonicalUrl: String?,
                   val categories: List<Category>?,
                   val url: String?,
                   val price: Price?,
                   val likes: Likes?,
                   val rating: Float,
                   val ratingColor: String?,
                   val menu: Menu?,
                   val description: String?,
                   val hours: Hours?,
                   val bestPhoto: BestPhoto?)

data class BestPhoto(val id: String,
                     val prefix: String,
                     val suffix: String,
                     val width: Int,
                     val height: Int) {
    fun getUrl(): String = "${prefix}width$width$suffix"
}

data class Hours(val status: String,
                 val timeframes: List<TimeFrames>)

data class TimeFrames(val days: String,
                      val open: List<OpenHours>)

data class OpenHours(val renderedTime: String)

data class Menu(val type: String,
                val label: String,
                val anchor: String,
                val url: String,
                val mobileUrl: String)

data class Likes(val count: Int,
                 val summary: String)

data class Price(val tier: Int,
                 val message: String,
                 val currency: String)

data class Contact(val phone: String?,
                   val formattedPhone: String?,
                   val instagram: String?,
                   val facebook: String?,
                   val facebookUsername: String?,
                   val facebookName: String?)