package com.example.quote_of_the_day

data class FavouriteQuotesItems(
    val quote : String,
    val author : String,
){

    constructor() : this("", "")
}
