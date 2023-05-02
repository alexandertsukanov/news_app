package com.tsukanov.news.entity

import javax.persistence.*

@Entity
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    @ManyToMany(mappedBy = "author")
    val article: List<Article> = listOf()
)