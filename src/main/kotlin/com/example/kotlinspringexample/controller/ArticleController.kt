package com.example.kotlinspringexample.controller

import com.example.kotlinspringexample.dto.ArticleDto
import com.example.kotlinspringexample.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feed/article")
class ArticleController(private var articleService: ArticleService) {

    @GetMapping("/{id}")
    fun getOneArticle(@PathVariable id: Long): ArticleDto {
        return articleService.findOneById(id)
    }
}