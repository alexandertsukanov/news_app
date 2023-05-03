package com.tsukanov.news.dto

import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ArticleDto(
    val id: Long = 0,
    @field:NotBlank
    val header: String,
    @field:NotBlank
    val text: String,
    @field:NotBlank
    val shortDescription: String,
    val publishDate: LocalDate?,
    @field:Size(min = 1)
    val author: List<AuthorDto> = listOf(),
    val keyword: List<KeywordDto> = listOf()
)
