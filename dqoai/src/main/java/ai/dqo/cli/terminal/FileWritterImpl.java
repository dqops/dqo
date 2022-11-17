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
package ai.dqo.cli.terminal;

import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class FileWritterImpl implements FileWritter {
	private final UserHomeContextFactory userHomeContextFactory;
	private final TerminalReader terminalReader;

	@Autowired
	public FileWritterImpl(TerminalReader terminalReader, UserHomeContextFactory userHomeContextFactory) {
		this.terminalReader = terminalReader;
		this.userHomeContextFactory = userHomeContextFactory;
	}

	/**
	 * Return an ISO 8601 combined date and time string for specified date/time
	 * @param date
	 * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	private static String getISO8601StringForCurrentDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}

	/**
	 * Writes string content to a file
	 * @param content String content.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus writeStringToFile(String content) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();
		Path userHomeFolderPath = this.userHomeContextFactory.openLocalUserHome().getHomeRoot().getPhysicalAbsolutePath();

		boolean response = this.terminalReader.promptBoolean("Do you want to use default file name?", true, false);
		try {
			if (response) {
				String newTableFileName = getISO8601StringForCurrentDate(new Date()).replaceAll("\\s+","")
						.replaceAll(":", "").replaceAll("-", "") + ".txt";
				File exportDirectory = new File(userHomeFolderPath + "/export/");
				exportDirectory.mkdir();
				File newTableFile = new File(exportDirectory.getAbsolutePath() + "/" + newTableFileName);
				newTableFile.createNewFile();
				FileWriter myWriter = new FileWriter(newTableFile.getAbsolutePath());
				myWriter.write(content);
				myWriter.close();

				cliOperationStatus.setSuccesMessage("Content saved to:\n" + newTableFile.getAbsolutePath());

				return cliOperationStatus;
			}
			String filePath = this.terminalReader.prompt("Write full file path:\n", "", false);
			File newTableFile = new File(filePath);
			FileWriter myWriter = new FileWriter(newTableFile);
			myWriter.write(content);
			myWriter.close();

			cliOperationStatus.setSuccesMessage("Content saved to:\n" + newTableFile.getAbsolutePath());
		} catch (Exception e) {
			cliOperationStatus.setFailedMessage("Cannot save content to file:\n" + e);
		}
		return cliOperationStatus;
	}
}
