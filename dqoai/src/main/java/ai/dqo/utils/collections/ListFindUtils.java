package ai.dqo.utils.collections;

import java.util.List;
import java.util.function.Predicate;

/**
 * Helper class to operate on lists.
 */
public class ListFindUtils {
    /**
     * Finds an element in the list that matches the given predicate.
     * @param list List of items.
     * @param predicate Predicate to match.
     * @return Found element or null when the element was not found.
     * @param <T>
     */
    public static <T> T findElement(List<T> list, Predicate<T> predicate) {
        for (T item : list) {
            if (predicate.test(item)) {
                return item;
            }
        }

        return null;
    }
}
