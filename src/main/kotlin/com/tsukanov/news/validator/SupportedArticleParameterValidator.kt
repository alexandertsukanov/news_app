package com.tsukanov.news.validator

import com.tsukanov.news.enum.ArticleSupportedParameters
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SupportedArticleParameterValidator : ConstraintValidator<ArticleParameterValidator, Map<String, String>> {

    override fun isValid(parameters: Map<String, String>?, context: ConstraintValidatorContext?): Boolean {
        parameters?.forEach { (key, _) ->
            if (key !in ArticleSupportedParameters.namesAsString) return false
        }

        return true
    }
}