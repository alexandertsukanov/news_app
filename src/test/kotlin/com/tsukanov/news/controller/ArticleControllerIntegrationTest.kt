package com.tsukanov.news.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.dto.AuthorDto
import com.tsukanov.news.dto.KeywordDto
import com.tsukanov.news.service.ArticleService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(ArticleController::class)
class ArticleControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var articleService: ArticleService

    private lateinit var articleDto: ArticleDto

    @BeforeEach
    fun setUp() {
        articleDto = ArticleDto(
            id = 1L,
            header = "Test Article",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "John Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )
    }

    @Test
    fun `getOneArticle should return an ArticleDto`() {
        `when`(articleService.findOneById(1L)).thenReturn(articleDto)

        mockMvc.perform(get("/feed/articles/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.header").value("Test Article"))
            .andExpect(jsonPath("$.text").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
            .andExpect(jsonPath("$.shortDescription").value("A short description."))
            .andExpect(jsonPath("$.publishDate").isNotEmpty)
            .andExpect(jsonPath("$.author[0].name").value("John Doe"))
            .andExpect(jsonPath("$.keyword[0].name").value("test"))

        verify(articleService).findOneById(1L)
    }

    @Test
    fun `getAllArticles should return a Page of ArticleDtos`() {
        val pageable = PageRequest.of(0, 10, Sort.unsorted())
        val articlePage = PageImpl(listOf(articleDto))
        val parameters = mapOf("page" to "0", "size" to "10")
        `when`(articleService.getAllArticles(parameters, pageable)).thenReturn(articlePage)

        mockMvc.perform(get("/feed/articles?page=0&size=10").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].header").value("Test Article"))
            .andExpect(jsonPath("$.content[0].text").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
            .andExpect(jsonPath("$.content[0].shortDescription").value("A short description."))
            .andExpect(jsonPath("$.content[0].publishDate").isNotEmpty)
            .andExpect(jsonPath("$.content[0].author[0].name").value("John Doe"))
            .andExpect(jsonPath("$.content[0].keyword[0].name").value("test"))

        verify(articleService).getAllArticles(parameters, pageable)
    }

    @Test
    fun `updateArticle should return updated ArticleDto`() {
        `when`(articleService.updateArticle(1L, articleDto)).thenReturn(articleDto)

        mockMvc.perform(
            put("/feed/articles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.header").value("Test Article"))
            .andExpect(jsonPath("$.text").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
            .andExpect(jsonPath("$.shortDescription").value("A short description."))
            .andExpect(jsonPath("$.publishDate").isNotEmpty)
            .andExpect(jsonPath("$.author[0].name").value("John Doe"))
            .andExpect(jsonPath("$.keyword[0].name").value("test"))

        verify(articleService).updateArticle(1L, articleDto)
    }

    @Test
    fun `saveArticle should return saved ArticleDto`() {
        `when`(articleService.saveArticle(articleDto)).thenReturn(articleDto)

        mockMvc.perform(
            post("/feed/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.header").value("Test Article"))
            .andExpect(jsonPath("$.text").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
            .andExpect(jsonPath("$.shortDescription").value("A short description."))
            .andExpect(jsonPath("$.publishDate").isNotEmpty)
            .andExpect(jsonPath("$.author[0].name").value("John Doe"))
            .andExpect(jsonPath("$.keyword[0].name").value("test"))

        verify(articleService).saveArticle(articleDto)
    }

    @Test
    fun `deleteArticle should return NoContent status`() {
        doNothing().`when`(articleService).deleteArticle(1L)

        mockMvc.perform(delete("/feed/articles/1"))
            .andExpect(status().is2xxSuccessful)

        verify(articleService).deleteArticle(1L)
    }
}
