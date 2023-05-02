package com.tsukanov.news.service

import com.tsukanov.news.constant.SupportedParameters
import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.mapper.toArticle
import com.tsukanov.news.mapper.toArticleDto
import com.tsukanov.news.repository.ArticleRepository
import com.tsukanov.news.specification.ArticleSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun getAllArticles(parameters: Map<String, String>, pageable: Pageable): Page<ArticleDto> {
        parameters.forEach{ (key, _) ->
            if (key !in SupportedParameters.namesAsString) throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                """The next parameters currently supported by the system: ${SupportedParameters.namesAsString}""")
        }
        val articleSpecification = ArticleSpecification(parameters)
        return articleRepository.findAll(articleSpecification, pageable).map { it.toArticleDto() }
    }
}
