/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.services.check.mining.regex;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class CommonRegexPatternAnalyzerTests extends BaseTest {
    private RegexPatternAnalyzerParameters parameters;

    @BeforeEach
    void setUp() {
        this.parameters = new RegexPatternAnalyzerParameters();
    }

    private void assertSamplesWithRegex(String regex, List<String> examples) {
        for (String example : examples) {
            Assertions.assertTrue(example.matches(regex));
        }
    }

    @Test
    void findCommonRegex_whenEmptySampleList_thenReturnsNull() {
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(new ArrayList<>(), parameters);
        Assertions.assertNull(commonRegex);
    }

    @Test
    void findCommonRegex_whenOneTextWithOneWord_thenReturnsNull() {
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(Arrays.asList("abc"), parameters);
        Assertions.assertNull(commonRegex);
    }

    @Test
    void findCommonRegex_whenManyTextsWithOneWord_thenReturnsNull() {
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(Arrays.asList("abc", "def", "a", "b", "c", "d", "e", "fff", "g", "H"), parameters);
        Assertions.assertNull(commonRegex);
    }

    @Test
    void findCommonRegex_whenManyTextsTwoWordsAndSpaceAndMinToken3_thenReturnsRegularExpression() {
        this.parameters.setMinimumTokensInRegex(3);
        List<String> examples = Arrays.asList("abc def", "def gh", "a bcd", "b dddd", "cdd aaa", "ddd eee", "ess s", "ff ddddd", "d HHHH", "H ss", "EE CC");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[A-Za-z]+ [A-Za-z]+";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }

    @Test
    void findCommonRegex_whenManyTextsTwoWordsAndSpaceAndMinToken3AndExactCharCounts_thenReturnsRegularExpression() {
        this.parameters.setMinimumTokensInRegex(3);
        this.parameters.setFindMaxCharactersCount(true);
        List<String> examples = Arrays.asList("abc def", "def gh", "a bcd", "b dddd", "cdd aaa", "ddd eee", "ess s", "ff ddddd", "d HHHH", "H ss", "EE CC");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[A-Za-z]{1,3} [A-Za-z]{1,5}";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }

    @Test
    void findCommonRegex_whenManyTextsTwoOrThreeWordsAndSpaceAndMinToken3_thenReturnsRegularExpressionWithOptionalComponent() {
        this.parameters.setMinimumTokensInRegex(3);
        List<String> examples = Arrays.asList("abc def", "def DD ddD", "a bcd", "b dddd DD", "cdd aaa", "ddd eee", "ess s aa", "ff ddddd", "d HHHH", "H ss", "EE CC");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[A-Za-z]+ [A-Za-z]+( [A-Za-z]+)?";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }

    @Test
    void findCommonRegex_whenManyTextsTwoWordsAndSpecialCharactersThatNeedQuotingAndMinToken3_thenReturnsRegularExpression() {
        this.parameters.setMinimumTokensInRegex(3);
        List<String> examples = Arrays.asList("abc.def", "def.gh", "a.bcd", "b.dddd", "cdd.aaa", "ddd.EEE");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[a-z]+\\.[A-Za-z]+";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }

    @Test
    void findCommonRegex_whenManyTextsTwoWordsAndSpecialCharactersThatDoNotNeedQuotingAndMinToken3_thenReturnsRegularExpression() {
        this.parameters.setMinimumTokensInRegex(3);
        List<String> examples = Arrays.asList("abc/def", "def/gh", "a/bcd", "b/dddd", "cdd/aaa", "ddd/EEE");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[a-z]+/[A-Za-z]+";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }

    @Test
    void findCommonRegex_whenTwoAlternativeVariantsOfExpressions_thenReturnsRegularExpressionAsOrOfAlternatives() {
        this.parameters.setMinimumTokensInRegex(3);
        List<String> examples = Arrays.asList("abc/def", "def/gh", "a/bcd", "b/dddd", "cdd/aaa", "ddd/EEE", "cdd.aaa", "ddd.EEE");
        String commonRegex = CommonRegexPatternAnalyzer.findCommonRegex(examples, parameters);
        Assertions.assertNotNull(commonRegex);
        String regex = "[a-z]+/[A-Za-z]+|[a-z]+\\.[A-Za-z]+";
        Assertions.assertEquals(regex, commonRegex);
        assertSamplesWithRegex(regex, examples);
    }
}
