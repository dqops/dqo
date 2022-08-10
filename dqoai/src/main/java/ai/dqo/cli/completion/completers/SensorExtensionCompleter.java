package ai.dqo.cli.completion.completers;

import ai.dqo.cli.commands.SensorFileExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class SensorExtensionCompleter implements Iterable<String> {
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		try {
			ArrayList<Object> extensions = new ArrayList<>(EnumSet.allOf(SensorFileExtension.class));
			ArrayList<String> fileExtensions = new ArrayList<>();
			for(Object extension: extensions) {
				fileExtensions.add(extension.toString());
			}
			return fileExtensions.iterator();
		}
		catch(Exception ex) {
			return Collections.emptyIterator();
		}
	}
}
