package ai.dqo.cli.commands.rule.impl;

import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.RuleFileExtension;

/**
 * Service called from the "rule" cli commands to edit a template.
 */
public interface RuleService {
	CliOperationStatus editTemplate(String sensorName, RuleFileExtension ext);
}
