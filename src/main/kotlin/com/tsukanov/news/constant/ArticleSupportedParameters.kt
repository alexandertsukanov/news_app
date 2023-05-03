package com.tsukanov.news.constant

enum class ArticleSupportedParameters(val nameAsStringInLowerCase: String) {
    AUTHOR("author"),
    KEYWORD("keyword"),
    FROM_DATE("from_date"),
    TO_DATE("to_date"),
    PAGE("page"),
    SORT("sort"),
    SIZE("size"),
    OFFSET("offset");

    companion object {
       val namesAsString = values().map { it.nameAsStringInLowerCase }
    }
}