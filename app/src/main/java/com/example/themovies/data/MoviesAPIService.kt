package com.example.themovies.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPIService {
    @GET("/")
    suspend fun findMoviesById(@Query("i") imdbID: String): Movies

    @GET("/")
    suspend fun searchMoviesByTitle(@Query("s") title: String): MoviesResponse

}