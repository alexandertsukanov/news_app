package com.example.kotlinspringexample.mapper

import com.example.kotlinspringexample.dto.ArticleDto
import com.example.kotlinspringexample.dto.AuthorDto
import com.example.kotlinspringexample.dto.KeywordDto
import com.example.kotlinspringexample.entity.Article
import com.example.kotlinspringexample.entity.Author
import com.example.kotlinspringexample.entity.Keyword

fun Article.toArticleDto(): ArticleDto =
    ArticleDto(
        id = id,
        header = header,
        shortDescription = shortDescription,
        publishDate = publishDate,
        author = author.map { it.toDto() },
        keyword = keyword.map { it.toDto() }
    )

fun ArticleDto.toArticle(): Article =
    Article(
        id = id,
        header = header,
        shortDescription = shortDescription,
        publishDate = publishDate,
        author = author.map { it.toEntity() },
        keyword = keyword.map { it.toEntity() }
    )

fun Author.toDto(): AuthorDto = AuthorDto(
    id = this.id,
    name = this.name
)

fun AuthorDto.toEntity(): Author = Author(
    id = this.id,
    name = this.name
)

fun Keyword.toDto(): KeywordDto = KeywordDto(
    id = this.id,
    name = this.name
)

fun KeywordDto.toEntity(): Keyword = Keyword(
    id = this.id,
    name = this.name
)
