/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringPatternComparerTests extends BaseTest {
    @Test
    void matchSearchPattern_whenTextNullAndPatternNull_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern(null, null));
    }

    @Test
    void matchSearchPattern_whenTextNullAndPatternNotNull_thenFalse() {
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern(null, "a"));
    }

    @Test
    void matchSearchPattern_whenNoWildcardAndTextMatchPatternSameCase_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("abc", "abc"));
    }

    @Test
    void matchSearchPattern_whenNoWildcardAndTextMatchPatternDifferentCase_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABC", "abc"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("Abc", "abC"));
    }

    @Test
    void matchSearchPattern_whenNoWildcardAndTextNotMatchingWildcard_thenFalse() {
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abc", "abcc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("xabc", "abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("ab", "abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("bc", "abc"));
    }

    @Test
    void matchSearchPattern_whenBeginWithWildcardAndTextMatch_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABC", "*abc"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("abC", "*ABC"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("sABC", "*abc"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("somethingABC", "*abc"));
    }

    @Test
    void matchSearchPattern_whenBeginWithWildcardAndTextNotMatch_thenFalse() {
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("AB", "*abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("BC", "*abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("bc", "*abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abbc", "*abc"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("aaabbc", "*abc"));
    }

    @Test
    void matchSearchPattern_whenEndWithWildcardAndTextMatch_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABC", "abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("abC", "ABC*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCs", "abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCsomething", "abc*"));
    }

    @Test
    void matchSearchPattern_whenEndWithWildcardAndTextNotMatch_thenFalse() {
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("AB", "abc*"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("BC", "abc*"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("bc", "abc*"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abaaaa", "abc*"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("bcdddd", "abc*"));
    }

    @Test
    void matchSearchPattern_whenWildcardInsideAndTextMatch_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCDE", "abc*de"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("abCDe", "ABC*dE"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCsde", "abc*de"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCsomethingdE", "abc*De"));
    }

    @Test
    void matchSearchPattern_whenWildcardInsideAndTextNotMatch_thenFalse() {
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("AB", "abc*de"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("BCDE", "abc*De"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abce", "abc*DE"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abcxxe", "abc*DE"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("abcaad", "abc*de"));
        Assertions.assertFalse(StringPatternComparer.matchSearchPattern("bcdddd", "abc*De"));
    }

    @Test
    void matchSearchPattern_whenStartAndEndWithWildcardAndTextMatch_thenTrue() {
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ŁŁŁ", "*łłł*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABC", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("abC", "*ABC*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCs", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("ABCsomething", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("sABC", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("somethingABC", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("sABCs", "*abc*"));
        Assertions.assertTrue(StringPatternComparer.matchSearchPattern("somethingABCsomething", "*abc*"));
    }

    @Test
    void isSearchPattern_whenTextWithoutWildcard_thenReturnsFalse() {
        Assertions.assertFalse(StringPatternComparer.isSearchPattern(""));
        Assertions.assertFalse(StringPatternComparer.isSearchPattern("a"));
        Assertions.assertFalse(StringPatternComparer.isSearchPattern("abc"));
    }

    @Test
    void isSearchPattern_whenWildcardCharacterPresent_thenReturnsTrue() {
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("*"));
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("**"));
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("*aaa"));
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("aaa*"));
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("aaa*ddd"));
        Assertions.assertTrue(StringPatternComparer.isSearchPattern("*aaaddd*"));
    }
}
