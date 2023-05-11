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

package ai.dqo.testutils;

/**
 * Helper method for converting any values to an expected data type.
 */
public class ValueConverter {
    /**
     * Converts any type of object to an integer. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to an integer.
     */
    public static Integer toInteger(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Integer) {
            return (Integer)obj;
        }

        if (obj instanceof Long) {
            return ((Long)obj).intValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).intValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).intValue();
        }

        if (obj instanceof Double) {
            return ((Double)obj).intValue();
        }

        if (obj instanceof String) {
            return Integer.valueOf((String)obj);
        }

        return Integer.valueOf(obj.toString());
    }

    /**
     * Converts any type of object to a long. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to a long.
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Long) {
            return (Long)obj;
        }

        if (obj instanceof Integer) {
            return ((Integer)obj).longValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).longValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).longValue();
        }

        if (obj instanceof Double) {
            return ((Double)obj).longValue();
        }

        if (obj instanceof String) {
            return Long.valueOf((String)obj);
        }

        return Long.valueOf(obj.toString());
    }

    /**
     * Converts any type of object to a double. Throws exception when the conversion fails.
     * @param obj Object to convert.
     * @return Value converted to an double.
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Double) {
            return (Double)obj;
        }

        if (obj instanceof Long) {
            return ((Long)obj).doubleValue();
        }

        if (obj instanceof Short) {
            return ((Short)obj).doubleValue();
        }

        if (obj instanceof Float) {
            return ((Float)obj).doubleValue();
        }

        if (obj instanceof Integer) {
            return ((Integer)obj).doubleValue();
        }

        if (obj instanceof String) {
            return Double.valueOf((String)obj);
        }

        return Double.valueOf(obj.toString());
    }
}
