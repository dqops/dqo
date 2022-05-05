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
package ai.dqo.cli.commands.status;

import tech.tablesaw.api.Table;

/**
 * Cli operation status object.
 */
public class CliOperationStatus {
	private boolean success;
	private String message;
	private Table table;

	/**
	 * Default constructor.
	 */
	public CliOperationStatus() {

	}

	/**
	 * Complex constructor.
	 */
	public CliOperationStatus(boolean success, String message, Table table) {
		this.success = success;
		this.message = message;
		this.table = table;
	}

	/**
	 * Returns the table.
	 * @return Table.
	 */
	public Table getTable() {
		return this.table;
	}

	/**
	 * Sets the table.
	 * @param table Table.
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Returns the success value.
	 * @return Succes value.
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * Sets the succes value.
	 * @param success Successs value.
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Returns the message.
	 * @return Message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Sets the message.
	 * @param message Message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the message and sets success to false.
	 * @param message Message.
	 */
	public void setFailedMessage(String message) {
		setSuccess(false);
		this.message = message;
	}

	/**
	 * Sets the message and sets success to true.
	 * @param message Message.
	 */
	public void setSuccesMessage(String message) {
		setSuccess(true);
		this.message = message;
	}
}
