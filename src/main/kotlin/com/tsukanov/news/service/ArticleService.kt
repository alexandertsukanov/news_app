package com.tsukanov.news.service

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.mapper.toArticle
import com.tsukanov.news.mapper.toArticleDto
import com.tsukanov.news.repository.ArticleRepository
import com.tsukanov.news.specification.ArticleSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException


@Service
class ArticleService(private val articleRepository: ArticleRepository) : IArticleService {
    override fun findOneById(id: Long): ArticleDto {
        return articleRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
            .toArticleDto()
    }

    override fun getAllArticles(
        parameters: Map<String, String>,
        pageable: Pageable
    ): Page<ArticleDto> {
        val articleSpecification = ArticleSpecification(parameters)
        return articleRepository.findAll(articleSpecification, pageable).map { it.toArticleDto() }
    }

    @Transactional
    override fun updateArticle(id: Long, articleDto: ArticleDto): ArticleDto {
        val findById = articleRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        val updatedUser = articleDto.copy(id = findById.id)
        return articleRepository.save(updatedUser.toArticle()).toArticleDto()
    }

    @Transactional
    override fun saveArticle(article: ArticleDto): ArticleDto {
        return articleRepository.save(article.toArticle()).toArticleDto()
    }

    @Transactional
    override fun deleteArticle(id: Long) {
        if (!articleRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        articleRepository.deleteById(id)
    }
}
