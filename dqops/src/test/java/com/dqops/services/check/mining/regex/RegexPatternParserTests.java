/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining.regex;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegexPatternParserTests extends BaseTest {
    @Test
    void parseText_whenJustOneUpperCaseLetter_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("A", true);
        Assertions.assertEquals(RegexPatternTokenType.UPPER_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("A", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("A", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenJustOneLowerCaseLetter_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("a", true);
        Assertions.assertEquals(RegexPatternTokenType.LOWER_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("a", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("a", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenJustOneDigitAndTreatDigitsAsWordsTrue_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("1", true);
        Assertions.assertEquals(RegexPatternTokenType.DIGITS, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("1", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("1", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenJustOneDigitAndTreatDigitsAsWordsFalse_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("1", false);
        Assertions.assertEquals(RegexPatternTokenType.DIGITS, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("1", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("1", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenMultipleUpperCaseLetters_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("ABC", true);
        Assertions.assertEquals(RegexPatternTokenType.UPPER_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(3, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("ABC", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("ABC", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenMultipleLowerCaseLetters_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("abc", true);
        Assertions.assertEquals(RegexPatternTokenType.LOWER_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(3, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("abc", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("abc", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenMultipleUMixedCaseLetters_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("Abc", true);
        Assertions.assertEquals(RegexPatternTokenType.MIXED_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(3, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("Abc", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("Abc", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenMultipleDigitsAndTreatDigitsAsWordsTrue_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("123", true);
        Assertions.assertEquals(RegexPatternTokenType.DIGITS, pattern.getTokenType());
        Assertions.assertEquals(3, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("123", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("123", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenMultipleDigitsAndTreatDigitsAsWordsFalse_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("123", false);
        Assertions.assertEquals(RegexPatternTokenType.DIGITS, pattern.getTokenType());
        Assertions.assertEquals(3, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("123", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("123", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenSpecialCharacterComma_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText(",", true);
        Assertions.assertEquals(RegexPatternTokenType.COMMA, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals(",", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals(",", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenSpecialCharacterUnderscore_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText("_", true);
        Assertions.assertEquals(RegexPatternTokenType.UNDERSCORE, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("_", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("_", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenSpecialCharacterUnderscoreRepeatedTwice_thenParsedAsTwoTokens() {
        RegexPatternToken pattern = RegexPatternParser.parseText("__", true);
        Assertions.assertEquals(RegexPatternTokenType.UNDERSCORE, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(1, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("_", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("_", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());

        RegexPatternToken nextToken = pattern.getBranch().getNextTokens().get(RegexPatternTokenType.UNDERSCORE);
        Assertions.assertNotNull(nextToken);
        Assertions.assertEquals(1, nextToken.getMaxRepeats());
        Assertions.assertEquals(0, nextToken.getBranch().getNextTokens().size());
        Assertions.assertEquals("_", nextToken.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("_", nextToken.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenSpace_thenParsed() {
        RegexPatternToken pattern = RegexPatternParser.parseText(" ", true);
        Assertions.assertEquals(RegexPatternTokenType.SPACE, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals(" ", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals(" ", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenLetterAndDigitAndTreatDigitsAsWordsFalse_thenParsedAsTwoTokens() {
        RegexPatternToken pattern = RegexPatternParser.parseText("a1", false);
        Assertions.assertEquals(RegexPatternTokenType.LOWER_CASE_LETTERS, pattern.getTokenType());
        Assertions.assertEquals(1, pattern.getMaxRepeats());
        Assertions.assertEquals(1, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("a", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("a", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());

        RegexPatternToken nextToken = pattern.getBranch().getNextTokens().get(RegexPatternTokenType.DIGITS);
        Assertions.assertNotNull(nextToken);
        Assertions.assertEquals(1, nextToken.getMaxRepeats());
        Assertions.assertEquals(0, nextToken.getBranch().getNextTokens().size());
        Assertions.assertEquals("1", nextToken.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("1", nextToken.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenLetterAndDigitAndTreatDigitsAsWordsTrue_thenParsedAsTwoTokens() {
        RegexPatternToken pattern = RegexPatternParser.parseText("a1", true);
        Assertions.assertEquals(RegexPatternTokenType.LETTERS_OR_DIGITS, pattern.getTokenType());
        Assertions.assertEquals(2, pattern.getMaxRepeats());
        Assertions.assertEquals(0, pattern.getBranch().getNextTokens().size());
        Assertions.assertEquals("a1", pattern.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("a1", pattern.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }

    @Test
    void parseText_whenTextThenSlashThenNumber_thenParsedAsThreeTokens() {
        RegexPatternToken firstToken = RegexPatternParser.parseText("abc/1234", true);
        Assertions.assertEquals(RegexPatternTokenType.LOWER_CASE_LETTERS, firstToken.getTokenType());
        Assertions.assertEquals(3, firstToken.getMaxRepeats());
        Assertions.assertEquals(1, firstToken.getBranch().getNextTokens().size());
        Assertions.assertEquals("abc", firstToken.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("abc", firstToken.getTokenOccurrences().values().stream().findFirst().get().getToken());

        RegexPatternToken secondToken = firstToken.getBranch().getNextTokens().get(RegexPatternTokenType.SLASH);
        Assertions.assertNotNull(secondToken);
        Assertions.assertEquals(1, secondToken.getMaxRepeats());
        Assertions.assertEquals(1, secondToken.getBranch().getNextTokens().size());
        Assertions.assertEquals("/", secondToken.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("/", secondToken.getTokenOccurrences().values().stream().findFirst().get().getToken());

        RegexPatternToken thirdToken = secondToken.getBranch().getNextTokens().get(RegexPatternTokenType.DIGITS);
        Assertions.assertNotNull(thirdToken);
        Assertions.assertEquals(4, thirdToken.getMaxRepeats());
        Assertions.assertEquals(0, thirdToken.getBranch().getNextTokens().size());
        Assertions.assertEquals("1234", thirdToken.getTokenOccurrences().keySet().stream().findFirst().get());
        Assertions.assertEquals("1234", thirdToken.getTokenOccurrences().values().stream().findFirst().get().getToken());
    }
}
