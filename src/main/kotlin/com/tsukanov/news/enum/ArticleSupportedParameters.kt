package com.tsukanov.news.enum

enum class ArticleSupportedParameters(val nameAsStringInLowerCase: String) {
    AUTHOR("author"),
    KEYWORD("keyword"),
    FROM_DATE("from_date"),
    TO_DATE("to_date"),
    PAGE("page"),
    SORT("sort"),
    SIZE("size");

    companion object {
        val namesAsString = values().map { it.nameAsStringInLowerCase }
    }
}