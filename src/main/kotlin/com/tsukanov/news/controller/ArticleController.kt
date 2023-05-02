package com.tsukanov.news.controller

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.service.ArticleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feed/article")
class ArticleController(private var articleService: ArticleService) {

    @GetMapping("/{id}")
    fun getOneArticle(@PathVariable id: Long): ArticleDto = articleService.findOneById(id)

    @GetMapping
    fun getAllArticles(
        @RequestParam parameters: Map<String, String>,
        pageable: Pageable
    ): Page<ArticleDto> = articleService.getAllArticles(parameters, pageable)
}