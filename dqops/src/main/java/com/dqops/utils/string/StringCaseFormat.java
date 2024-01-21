/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.string;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * Utility class extending com.google.common.base.CaseFormat;
 */
public enum StringCaseFormat {
    LOWER_HYPHEN,
    LOWER_UNDERSCORE,
    LOWER_CAMEL,
    UPPER_CAMEL,
    UPPER_UNDERSCORE,
    LOWER_UNDERSCORE_SEPARATE_NUMBER {
        @Override
        String convert(StringCaseFormat format, String s) {
            CaseFormat caseFormat = customToGoogleCaseFormat.get(format);

            String[] sSplit = s.split(UNDERSCORE);
            String number = sSplit[sSplit.length - 1];
            String sNew = String.join(UNDERSCORE, Arrays.copyOfRange(sSplit, 0, sSplit.length - 2)) + number;
            return CaseFormat.LOWER_UNDERSCORE.to(caseFormat, sNew);
        }
    };

    private static Map<StringCaseFormat, CaseFormat> customToGoogleCaseFormat = Map.of(
            LOWER_HYPHEN, CaseFormat.LOWER_HYPHEN,
            LOWER_UNDERSCORE, CaseFormat.LOWER_UNDERSCORE,
            LOWER_CAMEL, CaseFormat.LOWER_CAMEL,
            UPPER_CAMEL, CaseFormat.UPPER_CAMEL,
            UPPER_UNDERSCORE, CaseFormat.UPPER_UNDERSCORE
    );

    public final String to(StringCaseFormat format, String str) {
        Preconditions.checkNotNull(format);
        Preconditions.checkNotNull(str);
        return format == this ? str : this.convert(format, str);
    }

    private static String UNDERSCORE = "_";

    String convert(StringCaseFormat format, String s) {
        CaseFormat sourceFormat = customToGoogleCaseFormat.get(this);

        if (format == LOWER_UNDERSCORE_SEPARATE_NUMBER) {
            String sNew = sourceFormat.to(CaseFormat.LOWER_UNDERSCORE, s);
            if (NumberUtils.isParsable(Character.toString(sNew.charAt(sNew.length() - 1)))) {
                return ensureUnderscoreBeforeNumber(sNew);
            }

            return sNew;
        }

        CaseFormat caseFormat = customToGoogleCaseFormat.get(format);
        return sourceFormat.to(caseFormat, s);
    }

    protected static String ensureUnderscoreBeforeNumber(String s) {
        int i = s.length() - 1;
        while (i >= 0) {
            String cS = Character.toString(s.charAt(i));
            if (!NumberUtils.isParsable(cS)) {
                if (cS.equals(UNDERSCORE)) {
                    i = -1;
                }
                break;
            }
            --i;
        }

        StringBuilder result = new StringBuilder(s);
        if (i >= 0) {
            result.insert(i + 1, UNDERSCORE);
        }

        return result.toString();
    }
}
