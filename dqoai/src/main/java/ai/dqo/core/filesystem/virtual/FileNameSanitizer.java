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
package ai.dqo.core.filesystem.virtual;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;

/**
 * Helper class that sanitizes (encodes and decodes) file and folder names to avoid using special characters.
 */
public final class FileNameSanitizer {
    private static final HashSet<Character> INVALID_CHARACTERS_MAP = createInvalidCharactersMap();
    private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private static HashSet<Character> createInvalidCharactersMap() {
        HashSet<Character> invalidChars = new HashSet<>();
        invalidChars.add('/');
        invalidChars.add('\\');
        invalidChars.add('<');
        invalidChars.add('>');
        invalidChars.add(':');
        invalidChars.add('"');
        invalidChars.add('\'');
        invalidChars.add('|');
        invalidChars.add('?');
        invalidChars.add('*');
        invalidChars.add('&');
        invalidChars.add('%');  // our escape character

        return invalidChars;
    }

    /**
     * Encodes a file or folder name to be safer for the file system. Special characters like \ .. / ? and similar are encoded.
     * @param unsafeObjectName Unsafe file path to be encoded.
     * @return Encoded file path.
     */
    public static String encodeForFileSystem(String unsafeObjectName) {
        if (unsafeObjectName == null || Objects.equals(unsafeObjectName, "") || StringUtils.isAllBlank(unsafeObjectName)) {
            return "default";
        }

        return URLEncoder.encode(unsafeObjectName, StandardCharsets.UTF_8);

//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < unsafeObjectName.length(); i++) {
//            char c = unsafeObjectName.charAt(i);
//            if (INVALID_CHARACTERS_MAP.contains(c) || (c == '.' && (i == 0 || i == unsafeObjectName.length() - 1))) {
//                sb.append('%');
//                int firstDigit = (c >> 4) & 0xF;
//                sb.append(HEX_DIGITS[firstDigit]);
//                int secondDigit = c & 0xF;
//                sb.append(HEX_DIGITS[secondDigit]);
//            } else {
//                sb.append(c);
//            }
//        }
//
//        return sb.toString();
    }

    /**
     * Decodes a file system name that was previously encoded. Restores the original file name.
     * @param safeFileSystemName Safe file system name.
     * @return Decoded name.
     */
    public static String decodeFileSystemName(String safeFileSystemName) {
        return URLDecoder.decode(safeFileSystemName, StandardCharsets.UTF_8);

//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < safeFileSystemName.length(); i++) {
//            char c = safeFileSystemName.charAt(i);
//            if (c == '%' && i < safeFileSystemName.length() - 2) {
//                String hexString = safeFileSystemName.substring(i + 1, i + 3);
//                try {
//                    int charCode = Integer.parseUnsignedInt(hexString, 16);
//                    sb.append(Character.toChars(charCode));
//                    i += 2;
//                }
//                catch (NumberFormatException ex) {
//                    sb.append('%');
//                }
//            } else {
//                sb.append(c);
//            }
//        }
//
//        return sb.toString();
    }
}
