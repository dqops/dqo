import React from 'react';
import {
  CheckMiningParametersModel,
  CheckMiningParametersModelSeverityLevelEnum
} from '../../api';
import Button from '../Button';
import Checkbox from '../Checkbox';
import Input from '../Input';
import Select from '../Select';

export default function RuleMiningFilters({
  configuration,
  onChangeConfiguration,
  proposeChecks,
  applyChecks,
  isUpdated
}: {
  configuration: CheckMiningParametersModel;
  onChangeConfiguration: (
    configuration: Partial<CheckMiningParametersModel>
  ) => void;
  proposeChecks: () => Promise<void>;
  applyChecks: () => Promise<void>;
  isUpdated: boolean;
}) {
  return (
    <div className="p-4">
      <div className="flex items-center gap-x-4 mb-4">
        <Input
          label="Category"
          value={configuration.category_filter}
          onChange={(e) =>
            onChangeConfiguration({ category_filter: e.target.value })
          }
        />
        <Input
          label="Check name"
          value={configuration.check_name_filter}
          onChange={(e) =>
            onChangeConfiguration({ check_name_filter: e.target.value })
          }
        />
        <Input
          label="Column name"
          value={configuration.column_name_filter}
          onChange={(e) =>
            onChangeConfiguration({ column_name_filter: e.target.value })
          }
        />
        <Select
          label="Default severity level"
          options={Object.values(
            CheckMiningParametersModelSeverityLevelEnum
          ).map((value) => ({
            label: value,
            value
          }))}
          value={configuration.severity_level}
          onChange={(e) => onChangeConfiguration({ severity_level: e })}
          menuClassName="!top-14"
        />
      </div>
      <div className="flex items-center justify-between">
        <div>
          <Checkbox
            label="Copy failed profiling checks"
            checked={configuration.copy_failed_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_failed_profiling_checks: e })
            }
          />
        </div>
        <div className="flex items-center gap-x-4">
          <Button label="Propose" onClick={proposeChecks} color="primary" />
          <Button
            label="Apply"
            onClick={applyChecks}
            color={isUpdated ? 'primary' : 'secondary'}
          />
        </div>
        {/* <Checkbox
          label="Copy disabled profiling checks"
          checked={configuration.}
          onChange={() => undefined}
        /> */}
      </div>
    </div>
  );
}
