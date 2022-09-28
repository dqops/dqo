package ai.dqo.cli.commands.sensor.impl;

import ai.dqo.cli.commands.SensorFileExtension;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.edit.EditorLaunchService;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.sensordefinitions.FileSensorDefinitionWrapperImpl;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.stereotype.Service;

import static ai.dqo.metadata.storage.localfiles.SpecFileNames.*;

/**
 * Service called from the "sensor" cli commands to edit a template.
 */
@Service
public class SensorServiceImpl implements SensorService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DqoHomeContextFactory dqoHomeContextFactory;
	private final EditorLaunchService editorLaunchService;

	public SensorServiceImpl(UserHomeContextFactory userHomeContextFactory, DqoHomeContextFactory dqoHomeContextFactory,
							 EditorLaunchService editorLaunchService) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.dqoHomeContextFactory = dqoHomeContextFactory;
		this.editorLaunchService = editorLaunchService;
	}

	@Override
	public CliOperationStatus editTemplate(String sensorName, ProviderType provider, SensorFileExtension ext) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
