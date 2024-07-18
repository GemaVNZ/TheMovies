package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class Movies (
        @SerializedName ("imdbID") val imdbID: String,
        @SerializedName("Title") val title:String,
        @SerializedName("Year") val year: String,
        @SerializedName("Poster") val imageURL : String,
        @SerializedName("Plot") val plot: String,
        @SerializedName("Runtime") val runtime: String,
        @SerializedName("Director") val director: String,
        @SerializedName("Genre") val genre: String,
        @SerializedName("Country") val country: String)


data class MoviesResponse (
        @SerializedName("Search") val movies: List<Movies>,
        @SerializedName("Response") val response: String,
        @SerializedName("Error") val error: String?)
