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
        <Input
          label="Error rate (% rows)"
          tooltipText="The desired percentage of error rows. When a profiling data quality check identifies incorrect rows and their number is below this desired error rate, DQOps rule mining engine will configure the rule threshold make the check fail."
          value={configuration.fail_checks_at_percent_error_rows}
          type="number"
          onChange={(e) =>
            onChangeConfiguration({
              fail_checks_at_percent_error_rows:
                isNaN(Number(e.target.value)) || e.target.value === ''
                  ? undefined
                  : Number(e.target.value)
            })
          }
        />
      </div>
      <div className="flex justify-between">
        <div className="flex flex-wrap gap-x-5 mb-4">
          <Checkbox
            className="p-2"
            label="Copy failed profiling checks"
            checked={configuration.copy_failed_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_failed_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Copy disabled profiling checks"
            checked={configuration.copy_disabled_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_disabled_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Minimum row count"
            checked={configuration.propose_minimum_row_count}
            onChange={(e) =>
              onChangeConfiguration({ propose_minimum_row_count: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Column count"
            checked={configuration.propose_column_count}
            onChange={(e) =>
              onChangeConfiguration({ propose_column_count: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Column exists"
            checked={configuration.propose_column_exists}
            onChange={(e) =>
              onChangeConfiguration({ propose_column_exists: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Timeliness checks"
            checked={configuration.propose_timeliness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_timeliness_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Nulls (prohibit nulls)"
            checked={configuration.propose_nulls_checks}
            onChange={(e) => onChangeConfiguration({ propose_nulls_checks: e })}
          />
          <Checkbox
            className="p-2"
            label="Not nulls (require nulls)"
            checked={configuration.propose_not_nulls_checks}
            onChange={(e) => onChangeConfiguration({ propose_not_nulls_checks: e })}
          />
          <Checkbox
            className="p-2"
            label="Detected data type in texts"
            checked={configuration.propose_text_values_data_type}
            onChange={(e) => onChangeConfiguration({ propose_text_values_data_type: e })}
          />
          <Checkbox
            className="p-2"
            label="Uniqueness checks"
            checked={configuration.propose_uniqueness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_uniqueness_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Numeric ranges"
            checked={configuration.propose_numeric_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_numeric_ranges: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Text length ranges"
            checked={configuration.propose_text_length_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_text_length_ranges: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Accepted values"
            checked={configuration.propose_accepted_values_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_accepted_values_checks: e })
            }
          />
        </div>
        <div className="flex items-center justify-between">
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
    </div>
  );
}
