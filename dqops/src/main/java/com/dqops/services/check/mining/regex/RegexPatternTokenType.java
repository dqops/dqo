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

import java.util.ArrayList;
import java.util.List;

/**
 * A type of a token identified in a regular expression.
 */
public enum RegexPatternTokenType {
    UPPER_CASE_LETTERS("[A-Z]", true, false),
    LOWER_CASE_LETTERS("[a-z]", true, false),
    MIXED_CASE_LETTERS("[A-Za-z]", true, false),
    DIGITS("[0-9]", true, false),
    LETTERS_OR_DIGITS("[A-Za-z0-9]", true, false),
    SPACE(" ", true, false),

    LEFT_BRACE("{", false, true),
    RIGHT_BRACE("}", false, true),
    LEFT_PARENTHESIS("(", false, true),
    RIGHT_PARENTHESIS(")", false, true),
    LEFT_BRACKET("[", false, true),
    RIGHT_BRACKET("]", false, true),
    EXCLAMATION_MARK("!"),
    AT("@"),
    HYPHEN("-"),
    UNDERSCORE("_"),
    EQUAL("="),
    COLON(":"),
    SEMICOLON(";"),
    COMMA(","),
    DOT(".", false, true),
    LESS("<"),
    GREATER(">"),
    SLASH("/"),
    BACKSLASH("\\", false, true),
    TILDE("~"),
    BACK_QUOTE("`"),
    PERCENT("%"),
    HASH("#"),
    DOLLAR("$", false, true),
    CARET("^", false, true),
    ASTERISK("*", false, true),
    PLUS("+", false, true),
    AMPERSAND("&"),
    PIPE("|", false, true),
    QUOTE("'", false, true),
    DOUBLE_QUOTE("\""),
    QUESTION_MARK("?", false, true),
    SECTION("§"),
    EOL("\n", true, true),
    TAB("\t", false, true);

    private String pattern;
    private boolean supportsRepeats;
    private boolean requireEscape;

    RegexPatternTokenType(String pattern) {
        this(pattern, false, false);
    }

    RegexPatternTokenType(String pattern, boolean supportsRepeats, boolean requireEscape) {
        if (!supportsRepeats && pattern.length() != 1) {
            throw new RuntimeException("Invalid token format");
        }

        this.pattern = pattern;
        this.supportsRepeats = supportsRepeats;
        this.requireEscape = requireEscape;
    }

    /**
     * Returns a regular expression pattern for the token.
     * @return Token's pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Returns true if this token supports repeats. Letters, space and digits support repeating tokens, all other special characters are treated as single instances.
     * @return Token supports repeats.
     */
    public boolean isSupportsRepeats() {
        return supportsRepeats;
    }

    /**
     * Returns true if this token is a special regular expression token and requires escaping. For example: ().*?\[]
     * @return This token requires escaping.
     */
    public boolean isRequireEscape() {
        return requireEscape;
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
                return MIXED_CASE_LETTERS;
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

    /**
     * Returns a list of enum values that are character tokens.
     * @return Character tokens.
     */
    public static List<RegexPatternTokenType> getSpecialCharacters() {
        ArrayList<RegexPatternTokenType> characterTokens = new ArrayList<>();
        RegexPatternTokenType[] allEnumValues = values();

        for (RegexPatternTokenType tokenType : allEnumValues) {
            if (!tokenType.isSupportsRepeats()) {
                characterTokens.add(tokenType);
            }
        }

        return characterTokens;
    }
}
