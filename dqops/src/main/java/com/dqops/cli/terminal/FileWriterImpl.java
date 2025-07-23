/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class FileWriterImpl implements FileWriter {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DqoUserPrincipalProvider dqoUserPrincipalProvider;
	private final TerminalFactory terminalFactory;

	@Autowired
	public FileWriterImpl(TerminalFactory terminalFactory,
						  UserHomeContextFactory userHomeContextFactory,
						  DqoUserPrincipalProvider dqoUserPrincipalProvider) {
		this.terminalFactory = terminalFactory;
		this.userHomeContextFactory = userHomeContextFactory;
		this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
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
		DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
		Path userHomeFolderPath = userHomeContext.getHomeRoot().getPhysicalAbsolutePath();

		boolean response = this.terminalFactory.getReader().promptBoolean("Do you want to use default file name?", true);
		try {
			if (response) {
				String newTableFileName = getISO8601StringForCurrentDate(new Date()).replaceAll("\\s+","")
						.replaceAll(":", "").replaceAll("-", "") + ".txt";
				File exportDirectory = new File(userHomeFolderPath + "/export/");
				exportDirectory.mkdir();
				File newTableFile = new File(exportDirectory.getAbsolutePath() + "/" + newTableFileName);
				newTableFile.createNewFile();
				try (java.io.FileWriter myWriter = new java.io.FileWriter(newTableFile.getAbsolutePath())) {
					myWriter.write(content);
				}

				cliOperationStatus.setSuccessMessage("Content saved to:\n" + newTableFile.getAbsolutePath());

				return cliOperationStatus;
			}
			String filePath = this.terminalFactory.getReader().prompt("Write full file path:\n", "", false);
			File newTableFile = new File(filePath);
			try (java.io.FileWriter myWriter = new java.io.FileWriter(newTableFile)) {
				myWriter.write(content);
			}

			cliOperationStatus.setSuccessMessage("Content saved to:\n" + newTableFile.getAbsolutePath());
		} catch (Exception e) {
			cliOperationStatus.setFailedMessage("Cannot save content to file:\n" + e);
		}
		return cliOperationStatus;
	}
}
