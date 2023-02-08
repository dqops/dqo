package ai.dqo.metadata.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Parameter object for search visitors. Used to collect custom result objects.
 * @param <R> Collected object type.
 */
public class FoundResultsCollector<R> {
    private List<R> results = new ArrayList<>();

    /**
     * Adds a result to the collector.
     * @param result Result object.
     */
    public void add(R result) {
        assert result != null;
        this.results.add(result);
    }

    /**
     * Returns the list of results that were collected.
     * @return Collected results.
     */
    public List<R> getResults() {
        return results;
    }
}
