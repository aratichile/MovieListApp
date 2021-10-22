package com.example.anvisdigitalassignment.data.model

import java.io.Serializable


data class Search(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
) : Serializable