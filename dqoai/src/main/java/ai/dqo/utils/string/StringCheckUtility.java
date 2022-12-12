package ai.dqo.utils.string;

/**
 * Helper class that detects strings inside a given string.
 */
public class StringCheckUtility {
    /**
     * Checks if the <code>testedString</code> contains any of the <code>nestedStrings</code> strings.
     * @param testedString Tested string.
     * @param nestedStrings Array of possible values.
     * @return True - one of the alternative values was found. False - no string found.
     */
    public static boolean containsAny(String testedString, String... nestedStrings) {
        if (testedString == null || nestedStrings == null || nestedStrings.length == 0) {
            return false;
        }

        for (String nested : nestedStrings) {
            if (testedString.contains(nested)) {
                return true;
            }
        }

        return false;
    }
    
}
