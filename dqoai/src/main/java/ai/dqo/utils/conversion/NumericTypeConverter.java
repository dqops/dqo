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
package ai.dqo.utils.conversion;

/**
 * Static classes to cast or convert objects to a desired numeric type.
 */
public class NumericTypeConverter {
    /**
     * Converts a given object to an integer value.
     * @param obj Object that could be of any numeric type or a string.
     * @return Integer value.
     */
    public static Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Integer) {
            return (Integer) obj;
        }

        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }

        if (obj instanceof Short) {
            return ((Short) obj).intValue();
        }

        if (obj instanceof Byte) {
            return ((Byte) obj).intValue();
        }

        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1 : 0;
        }

        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }

        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }

        String asString = obj.toString();
        return Integer.valueOf(asString);
    }
}
