package com.tsukanov.news.service

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.dto.AuthorDto
import com.tsukanov.news.dto.KeywordDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleServiceIntegrationTest {

    @Autowired
    private lateinit var articleService: IArticleService

    companion object {
        @Container
        val mySQLContainer = MySQLContainer<Nothing>("mysql:8.0").apply {
            withDatabaseName("news_app")
        }


        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.flyway.url", mySQLContainer::getJdbcUrl)
            registry.add("spring.flyway.user", mySQLContainer::getPassword)
            registry.add("spring.flyway.password", mySQLContainer::getUsername)


            registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mySQLContainer::getPassword)
            registry.add("spring.datasource.username", mySQLContainer::getUsername)
        }
    }

    @Test
    @Transactional
    fun `test save and find Article`() {
        val articleDto = ArticleDto(
            id = 1L,
            header = "Test Article",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "John Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )

        // Save the article
        val savedArticle = articleService.saveArticle(articleDto)
        assertNotNull(savedArticle.id)

        // Find the article
        val foundArticle = articleService.findOneById(savedArticle.id)
        assertEquals(savedArticle, foundArticle)
    }

    @Test
    @Transactional
    fun `test updateArticle`() {
        // Create a new article
        val articleDto = ArticleDto(
            header = "Test Article",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "John Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )
        val savedArticle = articleService.saveArticle(articleDto)

        // Update the article
        val updatedArticleDto = articleDto.copy(header = "Updated Article")
        val updatedArticle = articleService.updateArticle(savedArticle.id, updatedArticleDto)

        // Check that the article was updated
        val foundArticle = articleService.findOneById(savedArticle.id)
        assertEquals(updatedArticle, foundArticle)
    }


    @Test
    @Transactional
    fun `test deleteArticle`() {
        // Create a new article
        val articleDto = ArticleDto(
            header = "Test Article",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "John Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )
        val savedArticle = articleService.saveArticle(articleDto)

        // Delete the article
        articleService.deleteArticle(savedArticle.id)

        // Check that the article was deleted
        assertThrows<ResponseStatusException> {
            articleService.findOneById(savedArticle.id)
        }
    }

    @Test
    @Transactional
    fun `test getAllArticles`() {
        // Create some articles
        val article1 = ArticleDto(
            header = "Test Article 1",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "John Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )
        articleService.saveArticle(article1)

        val article2 = ArticleDto(
            header = "Test Article 2",
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            shortDescription = "A short description.",
            publishDate = LocalDate.now(),
            author = listOf(AuthorDto(name = "Jane Doe")),
            keyword = listOf(KeywordDto(name = "test"))
        )
        articleService.saveArticle(article2)

        // Retrieve all articles
        val articles = articleService.getAllArticles(emptyMap(), Pageable.unpaged())

        // Check that both articles were retrieved
        assertEquals(2, articles.totalElements)
        assertThat(article1).usingRecursiveComparison().ignoringFields("id", "author.id", "keyword.id")
            .isEqualTo(articles.content[0])
        assertThat(article2).usingRecursiveComparison().ignoringFields("id", "author.id", "keyword.id")
            .isEqualTo(articles.content[1])
    }
}
