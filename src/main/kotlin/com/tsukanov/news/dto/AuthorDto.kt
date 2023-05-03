package com.tsukanov.news.dto

import javax.validation.constraints.NotBlank

data class AuthorDto(
    val id: Long = 0,
    @field:NotBlank
    val name: String
)
