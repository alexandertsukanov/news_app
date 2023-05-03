package com.tsukanov.news.controller

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.service.ArticleService
import com.tsukanov.news.validator.ArticleParameterValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/feed/articles")
@Validated
class ArticleController(private var articleService: ArticleService) {
    @GetMapping("/{id}")
    fun getOneArticle(@PathVariable id: Long): ArticleDto = articleService.findOneById(id)

    @GetMapping
    fun getAllArticles(
        @RequestParam @ArticleParameterValidator parameters: Map<String, String>,
        pageable: Pageable
    ): Page<ArticleDto> = articleService.getAllArticles(parameters, pageable)

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @Valid @RequestBody requestBody: ArticleDto): ArticleDto = articleService.updateArticle(id, requestBody)

    @PostMapping
    fun saveArticle(@Valid @RequestBody requestBody: ArticleDto): ArticleDto = articleService.saveArticle(requestBody)

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long) = articleService.deleteArticle(id)
}