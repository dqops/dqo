/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.sensor.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.SensorFileExtension;
import com.dqops.cli.edit.EditorLaunchService;
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.stereotype.Service;

import static com.dqops.metadata.storage.localfiles.SpecFileNames.*;

/**
 * Service called from the "sensor" cli commands to edit a template.
 */
@Service
public class SensorServiceImpl implements SensorService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DqoHomeContextFactory dqoHomeContextFactory;
	private final EditorLaunchService editorLaunchService;
	private final DqoUserPrincipalProvider dqoUserPrincipalProvider;

	public SensorServiceImpl(UserHomeContextFactory userHomeContextFactory,
							 DqoHomeContextFactory dqoHomeContextFactory,
							 EditorLaunchService editorLaunchService,
							 DqoUserPrincipalProvider dqoUserPrincipalProvider) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.dqoHomeContextFactory = dqoHomeContextFactory;
		this.editorLaunchService = editorLaunchService;
		this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
	}

	@Override
	public CliOperationStatus editTemplate(String sensorName, ProviderType provider, SensorFileExtension ext) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipalForAdministrator = this.dqoUserPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipalForAdministrator.getDataDomainIdentity(), false);
		UserHome userHome = userHomeContext.getUserHome();

		DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
		DqoHome dqoHome = dqoHomeContext.getDqoHome();

		FileSensorDefinitionWrapperImpl sensorDefinitionWrapper = (FileSensorDefinitionWrapperImpl) userHome.getSensors()
				.getByObjectName(sensorName, true);
		FileSensorDefinitionWrapperImpl dqoSensorDefinitionWrapper = (FileSensorDefinitionWrapperImpl) dqoHome.getSensors()
				.getByObjectName(sensorName, true);

		if (sensorDefinitionWrapper == null && dqoSensorDefinitionWrapper == null) {
			cliOperationStatus.setFailedMessage("There are not any sensors with this name");
			return cliOperationStatus;
		}

		if (sensorDefinitionWrapper == null) {
			FileSensorDefinitionWrapperImpl newSensorDefinitionWrapper = (FileSensorDefinitionWrapperImpl)userHome.getSensors().createAndAddNew(sensorName);
			newSensorDefinitionWrapper.setSpec(dqoSensorDefinitionWrapper.getSpec());
			sensorDefinitionWrapper = newSensorDefinitionWrapper;
			sensorDefinitionWrapper.setStatus(InstanceStatus.ADDED);
			sensorDefinitionWrapper.flush();
			userHome.flush();
		}

		if (provider == null) {
			this.editorLaunchService.launchEditorForFile(sensorDefinitionWrapper.getSensorFolderNode().getPhysicalAbsolutePath()
					+ "/" + SENSOR_SPEC_FILE_NAME_YAML);
			cliOperationStatus.setSuccess(true);
			return cliOperationStatus;
		}

		if (ext == null || ext == SensorFileExtension.JINJA2) {
			this.editorLaunchService.launchEditorForFile(sensorDefinitionWrapper.getSensorFolderNode().getPhysicalAbsolutePath()
					+ "/" + provider.name() + PROVIDER_SENSOR_SQL_TEMPLATE_EXT);
			cliOperationStatus.setSuccess(true);
			return cliOperationStatus;
		}

		this.editorLaunchService.launchEditorForFile(sensorDefinitionWrapper.getSensorFolderNode().getPhysicalAbsolutePath()
				+ "/" + provider.name() + PROVIDER_SENSOR_SPEC_FILE_EXT_YAML);

		cliOperationStatus.setSuccess(true);
		return cliOperationStatus;
	}
}
