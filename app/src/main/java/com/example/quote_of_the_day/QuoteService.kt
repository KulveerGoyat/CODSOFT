package com.example.quote_of_the_day

import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("random")
    suspend fun getRandomQuote() : Response<List<Quote>>
}