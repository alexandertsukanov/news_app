package com.tsukanov.news.service

import com.tsukanov.news.dto.ArticleDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IArticleService {
    fun findOneById(id: Long): ArticleDto
    fun getAllArticles(parameters: Map<String, String>, pageable: Pageable): Page<ArticleDto>

    fun updateArticle(id: Long, articleDto: ArticleDto): ArticleDto

    fun saveArticle(article: ArticleDto): ArticleDto

    fun deleteArticle(id: Long)
}