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
package ai.dqo.cli;

import ai.dqo.data.ParquetSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CLI entry point class.
 */
@SpringBootApplication(scanBasePackages = "ai.dqo")
public class CliApplication {
	private static final Logger LOG = LoggerFactory.getLogger(CliApplication.class);

	/**
	 * Main entry method for the DQO CLI application.
	 * @param args Arguments.
	 */
	public static void main(String[] args) {
		try {
			ParquetSupport.ensureInitialized();
			SpringApplication springApplication = new SpringApplication(CliApplication.class);
			springApplication.setAdditionalProfiles("cli");
			springApplication.run(args);

			// calls CliMainCommandRunner and calls commands in io.dqo.cli.command, find the right command there if you want to know what happens now
		}
	    catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
