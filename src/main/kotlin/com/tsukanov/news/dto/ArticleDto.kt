package com.tsukanov.news.dto

import java.time.LocalDate

data class ArticleDto(
    val id: Long,
    val header: String,
    val shortDescription: String,
    val publishDate: LocalDate,
    val author: List<AuthorDto> = listOf(),
    val keyword: List<KeywordDto> = listOf()
)
