package com.example.kotlinspringexample.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val header: String,
    val shortDescription: String,
    val publishDate: LocalDate,
    @ManyToMany
    val author: List<Author> = listOf(),
    @ManyToMany
    val keyword: List<Keyword> = listOf()
)

