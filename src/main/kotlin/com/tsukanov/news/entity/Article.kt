package com.tsukanov.news.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val header: String,
    @field:Column(columnDefinition = "TEXT")
    val text: String,
    val shortDescription: String,
    val publishDate: LocalDate?,
    @ManyToMany(cascade = [CascadeType.ALL])
    val author: List<Author> = listOf(),
    @ManyToMany(cascade = [CascadeType.ALL])
    val keyword: List<Keyword> = listOf()
)

