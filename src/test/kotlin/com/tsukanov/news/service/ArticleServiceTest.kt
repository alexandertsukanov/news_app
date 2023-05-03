package com.tsukanov.news.service

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.entity.Article
import com.tsukanov.news.mapper.toArticle
import com.tsukanov.news.repository.ArticleRepository
import com.tsukanov.news.specification.ArticleSpecification
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class ArticleServiceTest {

    @Mock
    private lateinit var articleRepository: ArticleRepository

    private lateinit var articleService: ArticleService

    @BeforeEach
    fun setUp() {
        this.articleService = ArticleService(this.articleRepository)
    }


    @Test
    @DisplayName("Should throw a ResponseStatusException when the id is not found")
    fun findOneByIdWhenIdIsNotFoundThenThrowResponseStatusException() {
        val id = 1L
        `when`(articleRepository.findById(id)).thenReturn(Optional.empty())

        // Act and Assert
        assertThrows<ResponseStatusException> {
            articleService.findOneById(id)
        }
        verify(articleRepository).findById(id)
    }

    private fun createArticleDto(id: Long = 1L): ArticleDto {
        return ArticleDto(id, "Test title", "Test content", "Test author", LocalDate.now())
    }

    @Test
    @DisplayName("Should return the article when the id is found")
    fun findOneByIdWhenIdIsFound() {
        val id = 1L
        val article = createArticleDto()
        `when`(articleRepository.findById(id)).thenReturn(Optional.of(article.toArticle()))

        val result = articleService.findOneById(id)

        assertEquals(article, result)
        verify(articleRepository).findById(id)
    }

    @Test
    fun `test getAllArticles should return a page of articles`() {
        val articleDto = createArticleDto()
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(listOf(articleDto.toArticle()), pageable, 1)
        val parameters = emptyMap<String, String>()

        `when`(
            articleRepository
                .findAll(any(ArticleSpecification::class.java), any(Pageable::class.java))
        )
            .thenReturn(page)

        val result = articleService.getAllArticles(parameters, pageable)

        assertThat(result.content).containsExactly(articleDto)
    }

    @Test
    fun `test updateArticle should update and return an article`() {
        val articleDto = createArticleDto()
        val updatedArticleDto = articleDto.copy(shortDescription = "Updated title")

        `when`(articleRepository.findById(1L)).thenReturn(Optional.of(articleDto.toArticle()))
        `when`(articleRepository.save(any(Article::class.java))).thenReturn(updatedArticleDto.toArticle())

        val result = articleService.updateArticle(1L, updatedArticleDto)

        assertThat(result).isEqualTo(updatedArticleDto)
    }

    @Test
    fun `test updateArticle should throw ResponseStatusException when not found`() {
        val updatedArticleDto = createArticleDto().copy(shortDescription = "Updated title")

        `when`(articleRepository.findById(1L)).thenReturn(Optional.empty())

        assertThatThrownBy { articleService.updateArticle(1L, updatedArticleDto) }
            .isInstanceOf(ResponseStatusException::class.java)
            .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
    }

    @Test
    fun `test saveArticle should save and return an article`() {
        val articleDto = createArticleDto()

        `when`(articleRepository.save(any(Article::class.java))).thenReturn(articleDto.toArticle())

        val result = articleService.saveArticle(articleDto)

        assertThat(result).isEqualTo(articleDto)
    }

    @Test
    fun `test deleteArticle should delete an article`() {
        `when`(articleRepository.existsById(any())).thenReturn(true)

        doNothing().`when`(articleRepository).deleteById(1L)

        articleService.deleteArticle(1L)

        verify(articleRepository, times(1)).deleteById(1L)
    }

    @Test
    fun `test deleteArticle should throw ResponseStatusException when not found`() {
        `when`(articleRepository.existsById(any())).thenReturn(false)
        assertThatThrownBy { articleService.deleteArticle(1L) }
            .isInstanceOf(ResponseStatusException::class.java)
            .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
        verify(articleRepository, never()).deleteById(1L)
    }
}