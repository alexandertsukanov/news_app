package com.tsukanov.news.mapper

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.dto.AuthorDto
import com.tsukanov.news.dto.KeywordDto
import com.tsukanov.news.entity.Article
import com.tsukanov.news.entity.Author
import com.tsukanov.news.entity.Keyword

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
