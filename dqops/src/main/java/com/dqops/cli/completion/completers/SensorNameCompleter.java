/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.cli.completion.completers;

import com.dqops.cli.completion.completers.cache.CliCompleterCacheKey;
import com.dqops.cli.completion.completers.cache.CliCompletionCache;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextCache;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.StaticBeanFactory;
import org.springframework.beans.factory.BeanFactory;

import java.util.*;

/**
 * Sensor list CLI parameter autocompletion source that should be applied on CLI command parameters
 * that accept a list of sensors.
 */
public class SensorNameCompleter implements Iterable<String> {
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
				new CliCompleterCacheKey(this.getClass()),
				() -> {
					try {
						BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
						UserHomeContextCache userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
						UserHomeContext userHomeContext = userHomeContextCache.getCachedLocalUserHome();
						UserHome userHome = userHomeContext.getUserHome();

						DqoHomeContextFactory dqoHomeContextCache = beanFactory.getBean(DqoHomeContextFactory.class);
						DqoHomeContext dqoHomeContext = dqoHomeContextCache.openLocalDqoHome();
						DqoHome dqoHome = dqoHomeContext.getDqoHome();

						SensorDefinitionList userHomeSensors = userHome.getSensors();
						SensorDefinitionList localDqoSensors = dqoHome.getSensors();
						Set<String> sensorNames = new LinkedHashSet<>();

						for (SensorDefinitionWrapper sensor : userHomeSensors) {
							sensorNames.add(sensor.getName());
						}
						for (SensorDefinitionWrapper sensor : localDqoSensors) {
							sensorNames.add(sensor.getName());
						}

						ArrayList<String> sensors = new ArrayList<>();
						for (String sensorName : sensorNames) {
							sensors.add(sensorName);
						}
						Collections.sort(sensors);
						return sensors.iterator();
					} catch (Exception ex) {
						return Collections.emptyIterator();
					}
				});

		return cachedCompletionCandidates;
	}
}
