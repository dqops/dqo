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

import ai.dqo.data.storage.TablesawParquetSupportFix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * CLI entry point class.
 */
@SpringBootApplication(scanBasePackages = "ai.dqo")
public class CliApplication {
	private static final Logger LOG = LoggerFactory.getLogger(CliApplication.class);

	private static boolean runningInteractiveMode;

	/**
	 * Filter the non-parameter arguments from the args given to main.
	 * A non-parameter argument doesn't start with '-'.
	 * @param args Arguments supplied to the main function.
	 * @return List of arguments that are non-parameter.
	 */
	protected static List<String> filterNonParameterArguments(String[] args) {
		return Arrays.stream(args)
				.filter(a -> !a.startsWith("-"))
				.collect(Collectors.toList());
	}

	/**
	 * Parse the command line parameters and check if the command will require a web server to start, or it is just a single shot command
	 * that we don't want to delay by starting a web server (that will be shutdown instantly).
	 * @param args Command line arguments.
	 * @return true when the web server should be started, false otherwise.
	 */
	public static boolean isCommandThatRequiresWebServer(String[] args) {
		if (args == null || args.length == 0) {
			// starting just "dqo" in the shell mode, we are starting the web server because we have time
			return true;
		}

		List<String> nonParameterArguments = filterNonParameterArguments(args);

		if (nonParameterArguments.size() > 0) {
			// possibly some commands
			if (!Objects.equals(nonParameterArguments.get(0), "run")) {
				return false; // it is any other command than "run", so it is a one shot command, we don't want to delay it by starting the web server
			}
		}

		return true; // no parameters, just the shell mode, so we start the web server
	}

	public static boolean isOneShotMode(String[] args) {
		if (args == null || args.length == 0) {
			// running just "dqo" in shell starts the interactive mode
			return false;
		}

		List<String> nonParameterArguments = filterNonParameterArguments(args);

		if (nonParameterArguments.size() > 0) {
			// running "dqo ..." with non-parameter arguments means a one-shot mode
			return true;
		}

		// running "dqo" with some parameters results in interactive mode
		return false;
	}

	/**
	 * Main entry method for the DQO CLI application.
	 * @param args Arguments.
	 */
	public static void main(String[] args) {
		try {
			TablesawParquetSupportFix.ensureInitialized();

			boolean commandThatRequiresWebServer = isCommandThatRequiresWebServer(args);
			runningInteractiveMode = !isOneShotMode(args);

			SpringApplication springApplication = new SpringApplication(CliApplication.class);
			springApplication.setAdditionalProfiles("cli");
			springApplication.setWebApplicationType(commandThatRequiresWebServer ? WebApplicationType.REACTIVE : WebApplicationType.NONE);
			springApplication.run(args);

			// calls CliMainCommandRunner and calls commands in io.dqo.cli.command, find the right command there if you want to know what happens now
		}
	    catch (Throwable t) {
			LOG.error("Error at starting the application: " + t.getMessage(), t);
			t.printStackTrace();
		}
	}
}
