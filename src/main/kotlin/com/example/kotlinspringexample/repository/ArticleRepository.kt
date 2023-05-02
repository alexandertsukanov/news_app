package com.example.kotlinspringexample.repository

import com.example.kotlinspringexample.entity.Article
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, Long>