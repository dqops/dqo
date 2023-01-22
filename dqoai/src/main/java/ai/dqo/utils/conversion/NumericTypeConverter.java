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
