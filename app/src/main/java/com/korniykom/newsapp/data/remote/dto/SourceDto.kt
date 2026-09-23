package com.korniykom.newsapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String,
)