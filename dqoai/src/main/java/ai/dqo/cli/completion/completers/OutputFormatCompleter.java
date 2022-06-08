package ai.dqo.cli.completion.completers;

import ai.dqo.cli.commands.TabularOutputFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class OutputFormatCompleter implements Iterable<String> {
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		try {
			ArrayList<Object> formats = new ArrayList<>(EnumSet.allOf(TabularOutputFormat.class));
			ArrayList<String> outputFormats = new ArrayList<>();
			for(Object outputFormat: formats) {
				outputFormats.add(outputFormat.toString());
			}
			return outputFormats.iterator();
		}
		catch(Exception ex) {
			return Collections.emptyIterator();
		}
	}
}
