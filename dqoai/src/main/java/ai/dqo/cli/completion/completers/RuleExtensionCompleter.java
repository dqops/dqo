package ai.dqo.cli.completion.completers;

import ai.dqo.cli.commands.RuleFileExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class RuleExtensionCompleter implements Iterable<String> {
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		try {
			ArrayList<Object> extensions = new ArrayList<>(EnumSet.allOf(RuleFileExtension.class));
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
