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
package com.dqops.cli;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
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
@SpringBootApplication(scanBasePackages = "com.dqops")
@Slf4j
public class CliApplication {
	private static boolean runningOneShotMode;
	private static boolean requiredWebServer;
	private static boolean silentEnabledByArgument;

	/**
	 * Returns true if DQOps was started in a mode to run one command and exit.
	 * @return True - command was given in the argument list, it will execute and the application will exit.
	 */
	public static boolean isRunningOneShotMode() {
		return runningOneShotMode;
	}

	/**
	 * Returns true if the web server must be started.
	 * @return True - webserver must be started.
	 */
	public static boolean isRequiredWebServer() {
		return requiredWebServer;
	}

	/**
	 * Returns true if "--silent" or --silent=true was given on the command line.
	 * @return True when a silent mode is enabled.
	 */
	public static boolean isSilentEnabledByArgument() {
		return silentEnabledByArgument;
	}

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

	/**
	 * Detects a presence of a command in the argument list.
	 * @param args Argument list.
	 * @return True - a command to run and exit was found.
	 */
	public static boolean hasArgumentsForOneShot(String[] args) {
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
	 * Main entry method for the DQOps CLI application.
	 * @param args Arguments.
	 */
	public static void main(String[] args) {
		try {
			requiredWebServer = isCommandThatRequiresWebServer(args);
			runningOneShotMode = hasArgumentsForOneShot(args);
			boolean silentDisabledAsArgument = Arrays.stream(args)
					.takeWhile(a -> a.startsWith("-"))
					.anyMatch(a -> a.equals("--silent=false"));

			silentEnabledByArgument = Arrays.stream(args)
					.takeWhile(a -> a.startsWith("-"))
					.anyMatch(a -> a.equals("--silent") || a.equals("--silent=true")) ||
					(!silentDisabledAsArgument &&
							(Objects.equals(System.getProperty("silent"), "true") ||
					         Objects.equals(System.getenv("SILENT"), "true")));

			SpringApplication springApplication = new SpringApplication(CliApplication.class);
			springApplication.setAdditionalProfiles("cli");
			springApplication.setLogStartupInfo(false);
			springApplication.setBannerMode(silentEnabledByArgument ? Banner.Mode.OFF : Banner.Mode.CONSOLE);
			springApplication.setWebApplicationType(requiredWebServer ? WebApplicationType.REACTIVE : WebApplicationType.NONE);
			springApplication.run(args);

			// calls CliMainCommandRunner and calls commands in io.dqo.cli.command, find the right command there if you want to know what happens now
		}
	    catch (Throwable t) {
			if (t instanceof IllegalStateException && t.getCause() instanceof org.jline.reader.EndOfFileException) {
				System.err.println("DQOps cannot open the terminal.");
				System.err.println("If you have started DQOps from docker and want to use the DQOps shell, please run the container with docker's \"-it\" parameter.");
				System.err.println("Alternatively, start DQOps from docker in a server headless mode (without the DQOps shell) using \"docker run dqops/dqo run\".");
				System.exit(-1);
			}
			log.error("Error at starting the application: " + t.getMessage(), t);
			t.printStackTrace();
		}
	}
}
