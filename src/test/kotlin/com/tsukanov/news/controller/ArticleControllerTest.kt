package com.tsukanov.news.controller

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.dto.AuthorDto
import com.tsukanov.news.dto.KeywordDto
import com.tsukanov.news.service.ArticleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class ArticleControllerTest {

    @Mock
    private lateinit var articleService: ArticleService

    @InjectMocks
    private lateinit var articleController: ArticleController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(articleController).build()
    }

    private val articleDto = ArticleDto(
        id = 1L,
        header = "Test Article",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        shortDescription = "A short description.",
        publishDate = LocalDate.now(),
        author = listOf(AuthorDto(name = "John Doe")),
        keyword = listOf(KeywordDto(name = "test"))
    )

    @Test
    @DisplayName("Should throw an exception when the article with the given id is not found")
    fun getOneArticleWhenArticleNotFoundThenThrowException() {
        val articleId = 1L

        `when`(articleService.findOneById(articleId)).thenThrow(
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Article not found"
            )
        )

        val exception = assertThrows<ResponseStatusException> {
            articleController.getOneArticle(articleId)
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.status)
        assertEquals("Article not found", exception.reason)

        verify(articleService).findOneById(articleId)
        verifyNoMoreInteractions(articleService)
    }

    @Test
    @DisplayName("Should return all articles")
    fun `test getAllArticles`() {
        val parameters = mapOf("author" to "John Doe")
        val pageable = Pageable.unpaged()
        val page = PageImpl(listOf(articleDto))

        doReturn(page).`when`(articleService).getAllArticles(parameters, pageable)

        val result = articleController.getAllArticles(parameters, pageable)

        assertEquals(page, result)
    }

    @Test
    @DisplayName("Should return a bad request response when an invalid parameter is passed to getAllArticles")
    fun `test getAllArticles with invalid parameter`() {
        val parameters = mapOf("invalid" to "parameter")
        val pageable = Pageable.unpaged()

        try {
            articleController.getAllArticles(parameters, pageable)
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
            assertEquals(
                "The next parameters currently supported by the system: author, keyword, from_date, to_date",
                e.reason
            )
        }
    }


    @Test
    @DisplayName("Should update an article")
    fun `test updateArticle`() {
        val updatedArticleDto = articleDto.copy(shortDescription = "Updated Test Article")

        doReturn(updatedArticleDto).`when`(articleService).updateArticle(articleDto.id, articleDto)

        val result = articleController.updateArticle(articleDto.id, articleDto)

        assertEquals(updatedArticleDto, result)
    }

    @Test
    @DisplayName("Should return a DTO from saveArticle()")
    fun `test saveArticle`() {
        doReturn(articleDto).`when`(articleService).saveArticle(articleDto)

        val result = articleController.saveArticle(articleDto)

        assertEquals(articleDto, result)
    }

    @Test
    @DisplayName("Verify that the deleteArticle method of the articleService was called with the correct ID")
    fun `test deleteArticle`() {
        articleController.deleteArticle(articleDto.id)

        verify(articleService).deleteArticle(articleDto.id)
    }
}