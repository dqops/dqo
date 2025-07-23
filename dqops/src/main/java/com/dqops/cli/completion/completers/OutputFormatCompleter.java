/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion.completers;

import com.dqops.cli.commands.TabularOutputFormat;

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
