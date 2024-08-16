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

/**
 * A type of a token identified in a regular expression.
 */
public enum RegexPatternTokenType {
    UPPER_CASE_LETTERS("[A-Z]"),
    LOWER_CASE_LETTERS("[a-z]"),
    ANY_CASE_LETTERS("[A-Za-z]"),
    DIGITS("[0-9]"),
    LETTERS_OR_DIGITS("[A-Za-z0-9]"),
    SPACE(" "),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    EXCLAMATION_MARK("!"),
    AT("@"),
    HYPHEN("-"),
    UNDERSCORE("_"),
    EQUAL("="),
    COLON(":"),
    SEMICOLON(";"),
    COMMA(","),
    DOT("."),
    LESS("<"),
    GREATER(">"),
    SLASH("/"),
    BACKSLASH("\\"),
    TILDE("~"),
    BACK_QUOTE("`"),
    PERCENT("%"),
    HASH("#"),
    DOLLAR("$"),
    CARET("^"),
    ASTERISK("*"),
    PLUS("+"),
    AMPERSAND("&"),
    PIPE("|"),
    QUOTE("'"),
    DOUBLE_QUOTE("\""),
    QUESTION_MARK("?"),
    SECTION("§"),
    EOL("\n"),
    TAB("\t");

    private String pattern;

    RegexPatternTokenType(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns a regular expression pattern for the token.
     * @return Token's pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Looks up the token type from the first character.
     * @param tokenText Token text.
     * @return Token type.
     */
    public static RegexPatternTokenType fromText(String tokenText) {
        for (int i = 0; i < tokenText.length(); i++) {
            char c = tokenText.charAt(i);
            if (!Character.isDigit(c)) {
                break;
            }

            if (i == tokenText.length() - 1) {
                return DIGITS;
            }
        }

        for (int i = 0; i < tokenText.length(); i++) {
            char c = tokenText.charAt(i);
            if (c < 'A' || c > 'Z') {
                break;
            }

            if (i == tokenText.length() - 1) {
                return UPPER_CASE_LETTERS;
            }
        }

        for (int i = 0; i < tokenText.length(); i++) {
            char c = tokenText.charAt(i);
            if (c < 'a' || c > 'z') {
                break;
            }

            if (i == tokenText.length() - 1) {
                return LOWER_CASE_LETTERS;
            }
        }

        for (int i = 0; i < tokenText.length(); i++) {
            char c = tokenText.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) {
                break;
            }

            if (i == tokenText.length() - 1) {
                return ANY_CASE_LETTERS;
            }
        }

        for (int i = 0; i < tokenText.length(); i++) {
            char c = tokenText.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !Character.isDigit(c)) {
                break;
            }

            if (i == tokenText.length() - 1) {
                return LETTERS_OR_DIGITS;
            }
        }

        char firstChar = tokenText.charAt(0);
        switch (firstChar) {
            case ' ':
                return SPACE;

            case '{':
                return LEFT_BRACE;

            case '}':
                return RIGHT_BRACE;

            case '(':
                return LEFT_PARENTHESIS;

            case ')':
                return RIGHT_PARENTHESIS;

            case '[':
                return LEFT_BRACKET;

            case ']':
                return RIGHT_BRACKET;

            case '!':
                return EXCLAMATION_MARK;

            case '@':
                return AT;

            case '-':
                return HYPHEN;

            case '_':
                return UNDERSCORE;

            case '=':
                return EQUAL;

            case ':':
                return COLON;

            case ';':
                return SEMICOLON;

            case ',':
                return COMMA;

            case '.':
                return DOT;

            case '<':
                return LESS;

            case '>':
                return GREATER;

            case '/':
                return SLASH;

            case '\\':
                return BACKSLASH;

            case '~':
                return TILDE;

            case '`':
                return BACK_QUOTE;

            case '%':
                return PERCENT;

            case '#':
                return HASH;

            case '$':
                return DOLLAR;

            case '^':
                return CARET;

            case '*':
                return ASTERISK;

            case '+':
                return PLUS;

            case '&':
                return AMPERSAND;

            case '|':
                return PIPE;

            case '\'':
                return QUOTE;

            case '"':
                return DOUBLE_QUOTE;

            case '?':
                return QUESTION_MARK;

            case '§':
                return SECTION;

            case '\n':
                return EOL;

            case '\t':
                return TAB;

            default:
                return null;
        }
    }
}
