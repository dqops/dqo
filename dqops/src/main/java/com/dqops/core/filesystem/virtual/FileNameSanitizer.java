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
package com.dqops.core.filesystem.virtual;

import jodd.util.ArraysUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;

/**
 * Helper class that sanitizes (encodes and decodes) file and folder names to avoid using special characters.
 */
public final class FileNameSanitizer {
    private static final boolean[] encodedCharacters = createEncodedCharactersBitmap();
    private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    /**
     * Creates a bitmap for characters that should be encoded. Indexes are ascii codes of characters.
     * A true value means that the character should be encoded.
     * @return Encoded characters bitmap.
     */
    private static boolean[] createEncodedCharactersBitmap() {
        boolean[] encodedCharacters = new boolean[128];
        encodedCharacters['/'] = true;
        encodedCharacters['\\'] = true;
        encodedCharacters['<'] = true;
        encodedCharacters['>'] = true;
        encodedCharacters[':'] = true;
        encodedCharacters['"'] = true;
        encodedCharacters['\''] = true;
        encodedCharacters['|'] = true;
        encodedCharacters['?'] = true;
        encodedCharacters['*'] = true;;
        encodedCharacters['&'] = true;
        encodedCharacters['%'] = true;  // our escape character
        return encodedCharacters;
    }

    /**
     * Encodes a file or folder name to be safer for the file system. Special characters like \ .. / ? and similar are encoded.
     * This method is different than a regular URL encoding, because spaces are not encoded.
     * @param unsafeObjectName Unsafe file path to be encoded.
     * @return Encoded file path.
     */
    public static String encodeForFileSystem(String unsafeObjectName) {
        if (unsafeObjectName == null || unsafeObjectName.length() == 0 || StringUtils.isAllBlank(unsafeObjectName)) {
            return "default";
        }

        int i = 0;
        for (i = 0; i < unsafeObjectName.length(); i++) {
            char c = unsafeObjectName.charAt(i);
            if (c > 0 && c < encodedCharacters.length && encodedCharacters[c]) {
                break;
            }
        }

        if (i == unsafeObjectName.length()) {
            return unsafeObjectName;
        }

        StringBuilder sb = new StringBuilder();
        for (i = 0; i < unsafeObjectName.length(); i++) {
            char c = unsafeObjectName.charAt(i);
            if (c > 0 && c < encodedCharacters.length && encodedCharacters[c]) {
                sb.append('%');
                int firstDigit = (c >> 4) & 0xF;
                sb.append(HEX_DIGITS[firstDigit]);
                int secondDigit = c & 0xF;
                sb.append(HEX_DIGITS[secondDigit]);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Decodes a file system name that was previously encoded. Restores the original file name.
     * @param safeFileSystemName Safe file system name.
     * @return Decoded name.
     */
    public static String decodeFileSystemName(String safeFileSystemName) {
        if (safeFileSystemName.indexOf('%') < 0) {
            return safeFileSystemName;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < safeFileSystemName.length(); i++) {
            char c = safeFileSystemName.charAt(i);
            if (c == '%' && i < safeFileSystemName.length() - 2) {
                String hexString = safeFileSystemName.substring(i + 1, i + 3);
                try {
                    int charCode = Integer.parseUnsignedInt(hexString, 16);
                    sb.append(Character.toChars(charCode));
                    i += 2;
                }
                catch (NumberFormatException ex) {
                    sb.append('%');
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Converts a path that contains raw path component names (not encoded) into a path that contains encoded names: some characters encoded.
     * @param path Source path.
     * @return Path with encoded components.
     */
    public static Path convertRawPathToEncodedPath(Path path) {
        if (path == null) {
            return null;
        }

        String[] pathElements = StringUtils.split(path.toString(), '/');
        String[] encodedPathElements = new String[pathElements.length];

        for (int i = 0; i < pathElements.length; i++) {
            encodedPathElements[i] = encodeForFileSystem(pathElements[i]);
        }

        String[] secondAndLaterElements = ArraysUtil.subarray(encodedPathElements, 1, encodedPathElements.length - 1);
        Path encodedPath = Path.of(encodedPathElements[0], secondAndLaterElements);
        return encodedPath;
    }

    /**
     * Converts a path with DQO encoded names (spaces are not encoded) to a fully URL encoded name that is used for downloading or uploading a file from/to a bucket.
     * @param path DQO encoded path (with not encoded spaces, but other components encoded).
     * @return Encoded path.
     */
    public static Path convertEncodedPathToFullyUrlEncodedPath(Path path) {
        if (path == null) {
            return null;
        }

        String[] pathElements = StringUtils.split(path.toString(), '/');
        String[] urlEncodedPathElements = new String[pathElements.length];

        for (int i = 0; i < pathElements.length; i++) {
            String rawName = decodeFileSystemName(pathElements[i]);
            urlEncodedPathElements[i] = URLEncoder.encode(rawName, StandardCharsets.UTF_8);
        }

        String[] secondAndLaterElements = ArraysUtil.subarray(urlEncodedPathElements, 1, urlEncodedPathElements.length - 1);
        Path encodedPath = Path.of(urlEncodedPathElements[0], secondAndLaterElements);
        return encodedPath;
    }

    /**
     * Converts a path with DQO encoded names (spaces are not encoded) to an unencoded path.
     * @param path DQO encoded path (with not encoded spaces, but other components encoded).
     * @return Unencoded path.
     */
    public static Path convertEncodedPathToRawPath(Path path) {
        if (path == null) {
            return null;
        }

        String[] pathElements = StringUtils.split(path.toString(), '/');
        String[] decodedPathElements = new String[pathElements.length];

        for (int i = 0; i < pathElements.length; i++) {
            String rawName = decodeFileSystemName(pathElements[i]);
            decodedPathElements[i] = rawName;
        }

        String[] secondAndLaterElements = ArraysUtil.subarray(decodedPathElements, 1, decodedPathElements.length - 1);
        Path encodedPath = Path.of(decodedPathElements[0], secondAndLaterElements);
        return encodedPath;
    }
}
