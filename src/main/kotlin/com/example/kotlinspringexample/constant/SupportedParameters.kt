package com.example.kotlinspringexample.constant

enum class SupportedParameters(val nameAsStringInLowerCase: String) {
    AUTHOR("author"),
    KEYWORD("keyword"),
    FROM_DATE("from_date"),
    TO_DATE("to_date");

    companion object {
       val namesAsString = values().map { it.nameAsStringInLowerCase }
    }
}