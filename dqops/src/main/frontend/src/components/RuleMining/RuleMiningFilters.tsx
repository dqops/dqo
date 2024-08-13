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
            className="p-2 !w-62"
            label="Copy failed profiling checks"
            tooltipText="Copy the configuration of profiling checks that failed durign the last execution. The preferred approach is to review the profiling checks, disable false-positive checks, and enable this configuration to copy the reviewed checks to the monitoring and partitioned checks for continous monitoring."
            checked={configuration.copy_failed_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_failed_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            label="Copy disabled profiling checks"
            tooltipText="Copy the configuration of disabled profiling checks. This option is effective for monitoring and partitioned checks only. By default it is disabled, leaving failed or incorrectly configured profiling checks only in the profiling section to avoid decreasing the data quality KPI."
            checked={configuration.copy_disabled_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_disabled_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            label="Copy enabled profiling checks"
            tooltipText="Copy the configuration of enabled profiling checks to the monitoring and partitioned checks. This option is effective for monitoring and partitioned checks only. By default it is enabled, allowing to migrate configured profiling checks to the monitoring section to enable Data Observability of these checks."
            checked={configuration.copy_profiling_checks}
            onChange={(e) =>
              onChangeConfiguration({ copy_profiling_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            label="Reconfigure default checks"
            tooltipText="Reconfigure the rule thresholds of data quality checks that were activated using data observability rule patterns (data quality policies)"
            checked={configuration.propose_default_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_default_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure the minimum row count based on the statistics"
            label="Minimum row count"
            checked={configuration.propose_minimum_row_count}
            onChange={(e) =>
              onChangeConfiguration({ propose_minimum_row_count: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure a table schema check that ensures that the count of column stays the same as the count of columns detected during data profiling."
            label="Expected columns count"
            checked={configuration.propose_column_count}
            onChange={(e) => onChangeConfiguration({ propose_column_count: e })}
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure a column exists check for each column to report when the column is no longer present"
            label="Column exists"
            checked={configuration.propose_column_exists}
            onChange={(e) =>
              onChangeConfiguration({ propose_column_exists: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure all types of table's timeliness checks (freshness, staleness, ingestion delay). This option works only when the table is correctly configured to be analyzable by timeliness check, and has the timestamp column selected in the configuration of the table in the Data sources section."
            label="Timeliness checks"
            checked={configuration.propose_timeliness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_timeliness_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure data quality checks that detect columns that have some null values in columns. When the percentage of null columns is below the value of the 'Error rate (% rows)' field, DQOps will raise a data quality issue when any null values are detected."
            label="Nulls (prohibit nulls)"
            checked={configuration.propose_nulls_checks}
            onChange={(e) => onChangeConfiguration({ propose_nulls_checks: e })}
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure data quality checks that require that columns already containing null values will contain some null values. This option could be desirable in some rare cases."
            label="Not nulls (require nulls)"
            checked={configuration.propose_not_nulls_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_not_nulls_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure a check that verifies all values in a text column and detects a common data type. If all column values matched the same data type, such as integers, floats, dates or timestamps, DQOps will configure a check that will monitor if any values not matching that data type appear in the column. This check is usable for raw tables in the landing zone."
            label="Detected datatype in texts"
            checked={configuration.propose_text_values_data_type}
            onChange={(e) =>
              onChangeConfiguration({ propose_text_values_data_type: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure uniqueness checks that detect columns with just a few duplicate values, and columns with a relatively low number of distinct values. DQOps will monitor if duplicate values appear, or the number of distinct values increases."
            label="Uniqueness checks"
            checked={configuration.propose_uniqueness_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_uniqueness_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Validate the values in numeric columns to detect if the values fall within the ranges that were observed during data profiling. DQOps will try to configure the 'min', 'max', 'mean' and 'median' checks."
            label="Numeric values ranges"
            checked={configuration.propose_numeric_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_numeric_ranges: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Propose the default configuration of the median and percentile in range checks that validate the value at a given percentile, such as a value in middle of all column values. The default value of this parameter is 'false' because calculating the median requires running a separate query on the data source, which is not advisable for data observability."
            label="Median and percentile ranges"
            checked={configuration.propose_percentile_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_percentile_ranges: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure the checks that analyze text statistics of text columns, such as the minimum and maximum length of text values."
            label="Text length ranges"
            checked={configuration.propose_text_length_ranges}
            onChange={(e) =>
              onChangeConfiguration({ propose_text_length_ranges: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure the checks that analyze boolean values and will configure a range percent check for the less common value (true or false)."
            label="Percent of true/false"
            checked={configuration.propose_bool_percent_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_bool_percent_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure the checks that detect invalid dates that are far in the past, or far in the future."
            label="Dates out of range"
            checked={configuration.propose_date_checks}
            onChange={(e) => onChangeConfiguration({ propose_date_checks: e })}
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Proposes the configuration the categorical values checks 'value_in_set_percent' from the 'accepted_values' category. These checks will be configured to ensure that the column contains only sample values that were identified during data profiling."
            label="Values in a set (dictionary)"
            checked={configuration.propose_values_in_set_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_values_in_set_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Configure the 'value_in_set_percent' checks from the 'accepted_values' category to raise data quality issues for rare values. DQOps will not add rare categorical values to the list of expected values."
            label="Treat rare values as invalid"
            checked={configuration.values_in_set_treat_rare_values_as_invalid}
            onChange={(e) =>
              onChangeConfiguration({
                values_in_set_treat_rare_values_as_invalid: e
              })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Propose the configuration the texts_in_top_values check from the 'accepted_values' category. This check find the most common values in a table and ensures that they are the same values that were identified during data profiling."
            label="Texts in top values"
            checked={configuration.propose_top_values_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_top_values_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Proposes the configuration the data type conversion checks that verify if text values can be converted to more specific data types such as boolean, date, float or integer. This type of checks are used to verify raw tables in the landing zones."
            label="Texts parsable to data types"
            checked={configuration.propose_text_conversion_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_text_conversion_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Propose the default configuration for the patterns check that validate the format of popular text patterns, such as UUIDs, phone numbers, or emails. DQOps will configure these data quality checks when analyzed columns contain enough values matching a standard pattern."
            label="Standard text patterns"
            checked={configuration.propose_standard_pattern_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_standard_pattern_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Propose the default configuration for the whitespace detection checks. Whitespace checks detect common data quality issues with storing text representations of null values, such as 'null', 'None', 'n/a' and other texts that should be stored as regular NULL values."
            label="Whitespace checks"
            checked={configuration.propose_whitespace_checks}
            onChange={(e) =>
              onChangeConfiguration({ propose_whitespace_checks: e })
            }
          />
          <Checkbox
            className="p-2 !w-62"
            tooltipText="Apply the rules to the Personal Identifiable Information checks (sensitive data), but only when the checks were run as profiling checks activated manually, or by activating a data quality check pattern that scans all columns for PII data."
            label="Apply PII check rules"
            checked={configuration.apply_pii_checks}
            onChange={(e) => onChangeConfiguration({ apply_pii_checks: e })}
          />
          <Checkbox
            className="p-2 !w-62"
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
