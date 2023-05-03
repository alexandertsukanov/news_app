package com.tsukanov.news.specification

import com.tsukanov.news.enum.ArticleSupportedParameters.*
import com.tsukanov.news.entity.Article
import com.tsukanov.news.entity.Author
import com.tsukanov.news.entity.Keyword
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class ArticleSpecification(val parameters: Map<String, Any>) : Specification<Article> {
    override fun toPredicate(
        root: Root<Article>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        if (parameters.isEmpty()) return criteriaBuilder.conjunction()
        val predicates = mutableListOf<Predicate>()
        parameters.mapNotNull { (field, value) ->
            when (field) {
                AUTHOR.nameAsStringInLowerCase -> {
                    val authorJoin = root.join<Article, Author>(AUTHOR.nameAsStringInLowerCase)
                    predicates.add(criteriaBuilder.equal(authorJoin.get<Long>("name"), value))
                }

                KEYWORD.nameAsStringInLowerCase -> {
                    val keywordJoin = root.join<Article, Keyword>(KEYWORD.nameAsStringInLowerCase)
                    predicates.add(criteriaBuilder.equal(keywordJoin.get<Long>("name"), value))
                }

                FROM_DATE.nameAsStringInLowerCase -> {
                    parameters[FROM_DATE.nameAsStringInLowerCase]
                        .let {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get("publishDate"),
                                    it.toString()
                                )
                            )
                        }
                }

                TO_DATE.nameAsStringInLowerCase -> {
                    parameters[FROM_DATE.nameAsStringInLowerCase]
                        .let {
                            predicates.add(
                                criteriaBuilder.lessThanOrEqualTo(
                                    root.get("publishDate"),
                                    it.toString()
                                )
                            )
                        }
                }

                else -> {}
            }
        }
        return criteriaBuilder.and(*predicates.toTypedArray())
    }
}
