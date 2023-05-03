package com.tsukanov.news.service

import com.tsukanov.news.dto.ArticleDto
import com.tsukanov.news.dto.AuthorDto
import com.tsukanov.news.dto.KeywordDto
import com.tsukanov.news.repository.ArticleRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleServiceIntegrationTest {

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

    @Autowired
    private lateinit var articleService: ArticleService

    @Autowired
    private lateinit var articleRepository: ArticleRepository

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
}
