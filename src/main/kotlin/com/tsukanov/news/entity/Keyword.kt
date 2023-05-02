package com.tsukanov.news.entity

import javax.persistence.*

@Entity
data class Keyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    @ManyToMany(mappedBy = "keyword")
    val article: List<Article> = listOf()
)
