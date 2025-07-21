/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands;

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
	public void setSuccessMessage(String message) {
		setSuccess(true);
		this.message = message;
	}
}
