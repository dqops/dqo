package ai.dqo.cli.terminal;

import ai.dqo.cli.commands.status.CliOperationStatus;

public interface FileWritter {
	/**
	 * Writes string content to a file
	 * @param content String content.
	 * @return Cli operation status.
	 */
	CliOperationStatus writeStringToFile(String content);
}
