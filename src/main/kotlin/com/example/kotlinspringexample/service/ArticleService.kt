package com.example.kotlinspringexample.service

import com.example.kotlinspringexample.dto.ArticleDto
import com.example.kotlinspringexample.mapper.toArticle
import com.example.kotlinspringexample.mapper.toArticleDto
import com.example.kotlinspringexample.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    fun findOneById(id: Long): ArticleDto {
        return articleRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found") }
            .toArticleDto()

    }

    fun saveArticle(article: ArticleDto): ArticleDto {
        return articleRepository.save(article.toArticle()).toArticleDto()
    }
}
