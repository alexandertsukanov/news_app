package com.tsukanov.news.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SupportedArticleParameterValidator::class])
annotation class ArticleParameterValidator(
    val message: String = "The next parameters currently supported by the system as RequestParams: author, keyword, from_date, to_date and pageable parameters as well",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
