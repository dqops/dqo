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
  isUpdated,
  isUpdatedFilters
}: {
  configuration: CheckMiningParametersModel;
  onChangeConfiguration: (
    configuration: Partial<CheckMiningParametersModel>
  ) => void;
  proposeChecks: () => Promise<void>;
  applyChecks: () => Promise<void>;
  isUpdated: boolean;
  isUpdatedFilters: boolean;
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
          className="text-sm"
        />
      </div>
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-x-5">
          <Checkbox
            label="Copy failed profiling checks"
            checked={configuration.copy_failed_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_failed_profiling_checks: e })
            }
          />
          <Checkbox
            label="Copy disabled profiling checks"
            checked={configuration.copy_disabled_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_disabled_profiling_checks: e })
            }
          />
        </div>
        <div className="flex items-center gap-x-4">
          <Button
            label="Propose"
            onClick={proposeChecks}
            color={isUpdatedFilters ? 'primary' : 'secondary'}
          />
          <Button
            label="Apply"
            onClick={applyChecks}
            color={isUpdated ? 'primary' : 'secondary'}
          />
        </div>
      </div>
    </div>
  );
}
