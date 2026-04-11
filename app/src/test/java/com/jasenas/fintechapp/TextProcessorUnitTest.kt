package com.jasenas.fintechapp

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class TextProcessorUnitTest {
    private lateinit var sut: TextProcessor

    @Before
    fun testSetup() {
        sut = TextProcessor()
    }

    @Test
    fun validateTextInput_emptyInput_returnsError() {
        val result = sut.validateTextInput("")
        assertEquals(false, result.isValid)
        assertEquals(R.string.error_empty_input, result.errorMessageRes)
    }

    @Test
    fun validateTextInput_onlyWhitespace_returnsError() {
        val result = sut.validateTextInput(" \t\n  ")
        assertEquals(false, result.isValid)
        assertEquals(R.string.error_empty_input, result.errorMessageRes)
    }

    @Test
    fun validateTextInput_validInput_returnsSuccess() {
        val result = sut.validateTextInput("Hello, world!")
        assertEquals(true, result.isValid)
        assertEquals(null, result.errorMessageRes)
    }

    @Test
    fun countWords_emptyText_returnsZero() {
        val result = sut.countWords("")
        assertEquals(0, result)
    }

    @Test
    fun countWords_whitespace_returnsZero() {
        val result = sut.countWords("   \t\n ")
        assertEquals(0, result)
    }

    @Test
    fun countWords_onlyNumbers_returnsZero() {
        val result = sut.countWords("123 45678 9")
        assertEquals(0, result)
    }

    @Test
    fun countWords_onlyPunctuation_returnsZero() {
        val result = sut.countWords(". , ?")
        assertEquals(0, result)
    }

    @Test
    fun countWords_oneWord_returnsOne() {
        val result = sut.countWords("Hello")
        assertEquals(1, result)
    }

    @Test
    fun countWords_twoWords_returnsTwo() {
        val result = sut.countWords("Hello world")
        assertEquals(2, result)
    }

    @Test
    fun countWords_twoWordsWithPunctuation_returnsTwo() {
        val result = sut.countWords("Hello - world!")
        assertEquals(2, result)
    }

    @Test
    fun countWords_unicodeCharacters_returnsCorrect() {
        val result = sut.countWords("Ąčęėž ęįų")
        assertEquals(2, result)
    }

    @Test
    fun countSeparators_empty_returnsZero() {
        val result = sut.countSeparators("")
        assertEquals(0, result)
    }

    @Test
    fun countSeparators_onlyWhitespace_returnsZero() {
        val result = sut.countSeparators("   \t\n ")
        assertEquals(0, result)
    }

    @Test
    fun countSeparators_onlyNumbers_returnsZero() {
        val result = sut.countSeparators("123 45678 9")
        assertEquals(0, result)
    }

    @Test
    fun countSeparators_onlyPunctuation_countsCorrectly() {
        val result = sut.countSeparators(",.?!:;-")
        assertEquals(7, result)
    }

    @Test
    fun countSeparators_wordsWithPunctuation_countsCorrectly() {
        val result = sut.countSeparators("Hello, World! Next? - Goodbye.")
        assertEquals(5, result)
    }

    @Test
    fun countSeparators_wordsWithBracketsAndMath_returnsZero() {
        val result = sut.countSeparators("(Hello) + [World] = {Goodbye}")
        assertEquals(0, result)
    }
}