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

import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.parquet.Strings;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

/**
 * Parser that splits texts into tokens.
 */
public class RegexPatternParser {
    private static final List<RegexPatternTokenType> SPECIAL_CHARACTER_TOKENS = RegexPatternTokenType.getSpecialCharacters();

    /**
     * Parses a text into a sequence of tokens.
     * @param text Text to parse.
     * @param mixDigitsAndLetters Digits are treated as words, allowing to mix letters and digits.
     * @return First token, with a list of next tokens.
     */
    public static RegexPatternToken parseText(String text, boolean mixDigitsAndLetters) {
        if (Strings.isNullOrEmpty(text)) {
            return null;
        }

        Reader textReader = new StringReader(text);
        StreamTokenizer tokenizer = new StreamTokenizer(textReader);
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars(128 + 32, 255);
        tokenizer.eolIsSignificant(true);
        if (mixDigitsAndLetters) {
            tokenizer.wordChars('0', '9');
        } else {
            tokenizer.ordinaryChars('0', '9');
        }
        tokenizer.ordinaryChar(' ');
        tokenizer.ordinaryChar('\t');
        for (RegexPatternTokenType tokenType : SPECIAL_CHARACTER_TOKENS) {
            tokenizer.ordinaryChar(tokenType.getPattern().charAt(0));
        }

        RegexPatternToken firstToken = null;
        RegexPatternToken lastToken = null;

        try {
            for (int nextToken = tokenizer.nextToken(); nextToken != StreamTokenizer.TT_EOF; nextToken = tokenizer.nextToken()) {
                String tokenText;
                if (nextToken == StreamTokenizer.TT_WORD) {
                    tokenText = tokenizer.sval;
                } else {
                    tokenText = Character.toString((char) nextToken);
                }

                RegexPatternTokenType tokenType = RegexPatternTokenType.fromText(tokenText);
                if (tokenType == null) {
                    return null; // text not parsable, wrong characters
                }

                RegexPatternToken regexPatternToken = new RegexPatternToken(tokenType, tokenText.length());

                if (tokenType.isSupportsRepeats() && lastToken != null && lastToken.getTokenType() == tokenType) {
                    lastToken.setMaxRepeats(lastToken.getMaxRepeats() + 1);
                    Optional<RegexTokenOccurrence> previousOccurrence = lastToken.getTokenOccurrences().values().stream().findFirst();
                    lastToken.getTokenOccurrences().clear();

                    String longerOccurrence = previousOccurrence.get().getToken() + tokenText;
                    lastToken.getTokenOccurrences().put(longerOccurrence, new RegexTokenOccurrence(longerOccurrence, 1));
                    continue;
                }

                if (firstToken == null) {
                    firstToken = regexPatternToken;
                } else {
                    lastToken.getBranch().getNextTokens().put(tokenType, regexPatternToken);
                }

                lastToken = regexPatternToken;
                regexPatternToken.getTokenOccurrences().put(tokenText, new RegexTokenOccurrence(tokenText, 1));
            }
        }
        catch (IOException ex) {
            throw new DqoRuntimeException("Cannot parse a text into a sequence of tokens: " + text + ", error: " + ex.getMessage(), ex);
        }

        if (lastToken != null) {
            lastToken.setTerminalToken(true);
        }

        return firstToken;
    }
}
