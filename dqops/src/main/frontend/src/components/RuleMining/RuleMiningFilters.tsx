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
            tooltipText="Copy the configuration of profiling checks that failed durign the last execution. The preferred approach is to review the profiling checks, disable false-positive checks, and enable this configuration to copy the reviewed checks to the monitoring and partitioned checks for continous monitoring."
            checked={configuration.copy_failed_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_failed_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Copy disabled profiling checks"
            tooltipText="Copy the configuration of disabled profiling checks. This option is effective for monitoring and partitioned checks only. By default it is disabled, leaving failed or incorrectly configured profiling checks only in the profiling section to avoid decreasing the data quality KPI."
            checked={configuration.copy_disabled_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_disabled_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            label="Reconfigure default checks"
            tooltipText="Reconfigure the rule thresholds of data quality checks that were activated using data observability rule patterns (data quality policies)"
            checked={configuration.propose_default_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_default_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure the minimum row count based on the statistics"
            label="Minimum row count"
            checked={configuration.propose_minimum_row_count}
            onChange={(e) =>
              onChangeConfiguration({ propose_minimum_row_count: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure a table schema check that ensures that the count of column stays the same as the count of columns detected during data profiling."
            label="Column count"
            checked={configuration.propose_column_count}
            onChange={(e) =>
              onChangeConfiguration({ propose_column_count: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure a column exists check for each column to report when the column is no longer present"
            label="Column exists"
            checked={configuration.propose_column_exists}
            onChange={(e) =>
              onChangeConfiguration({ propose_column_exists: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure all types of table's timeliness checks (freshness, staleness, ingestion delay). This option works only when the table is correctly configured to be analyzable by timeliness check, and has the timestamp column selected in the configuration of the table in the Data Sources section."
            label="Timeliness checks"
            checked={configuration.propose_timeliness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_timeliness_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure data quality checks that detect columns that have some null values in columns. When the percentage of null columns is below the value of the 'Error rate (% rows)' field, DQOps will raise a data quality issue when any null values are detected."
            label="Nulls (prohibit nulls)"
            checked={configuration.propose_nulls_checks}
            onChange={(e) => onChangeConfiguration({ propose_nulls_checks: e })}
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure data quality checks that require that columns already containing null values will contain some null values. This option could be desirable in some rare cases."
            label="Not nulls (require nulls)"
            checked={configuration.propose_not_nulls_checks}
            onChange={(e) => onChangeConfiguration({ propose_not_nulls_checks: e })}
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure a check that verifies all values in a text column and detects a common data type. If all column values matched the same data type, such as integers, floats, dates or timestamps, DQOps will configure a check that will monitor if any values not matching that data type appear in the column. This check is usable for raw tables in the landing zone."
            label="Detected datatype in texts"
            checked={configuration.propose_text_values_data_type}
            onChange={(e) => onChangeConfiguration({ propose_text_values_data_type: e })}
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure uniqueness checks that detect columns with just a few duplicate values, and columns with a relatively low number of distinct values. DQOps will monitor if duplicate values appear, or the number of distinct values increases."
            label="Uniqueness checks"
            checked={configuration.propose_uniqueness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_uniqueness_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Validate the values in numeric columns to detect if the values fall within the ranges that were observed during data profiling. DQOps will try to configure the 'min', 'max', 'mean' and 'median' checks."
            label="Numeric ranges"
            checked={configuration.propose_numeric_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_numeric_ranges: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure the checks that analyze text statistics of text columns, such as the minimum and maximum length of text values."
            label="Text length ranges"
            checked={configuration.propose_text_length_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_text_length_ranges: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure the checks that analyze boolean values and will configure a range percent check for the less common value (true or false)."
            label="Percent of true/false"
            checked={configuration.propose_bool_percent_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_bool_percent_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Configure the checks in the 'Accepted values' category to validate that the values in the column come from a predefined dictionary. DQOps will find columns with a low number of unique values and will propose a data dictionary which matches almost all found values. In order to detect data quality issues, DQOps will not include values that represent less than the 'Error rate (% rows)' rows, assuming that these rare values are invalid values."
            label="Accepted values"
            checked={configuration.propose_accepted_values_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_accepted_values_checks: e })
            }
          />
          <Checkbox
            className="p-2"
            tooltipText="Custom data quality checks must use DQOps built-in data quality rules, such as max_percent, min_percent or max_count to find invalid values."
            label="Custom checks"
            checked={configuration.propose_custom_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_custom_checks: e })
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
