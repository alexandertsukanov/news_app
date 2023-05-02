package com.tsukanov.news.repository

import com.tsukanov.news.entity.Article
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : PagingAndSortingRepository<Article, Long>, JpaSpecificationExecutor<Article>