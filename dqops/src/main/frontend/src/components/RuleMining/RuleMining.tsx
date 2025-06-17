import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckMiningParametersModel,
  CheckMiningProposalModel,
  CheckSearchFilters
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setJobAllert, toggleMenu } from '../../redux/actions/job.actions';
import { setRuleParametersConfigured } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { RuleMiningApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { getRuleParametersConfigured, useDecodedParams } from '../../utils';
import Tabs from '../Tabs';
import RuleMiningChecksContainer from './RuleMiningChecksContainer';
import RuleMiningConfirmDialog from './RuleMiningConfirmDialog';
import RuleMiningFilters from './RuleMiningFilters';
const tabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

const getRuleMiningConfigurationLocalStorage =
  (checkType: CheckTypes): CheckMiningParametersModel => {
    const configuration = localStorage.getItem('ruleMiningConfiguration-' + checkType);
    return configuration ? JSON.parse(configuration) : null!;
  };
const setRuleMiningConfigurationLocalStorage = (
  checkType: CheckTypes,
  configuration: CheckMiningParametersModel
) => {
  localStorage.setItem(
    'ruleMiningConfiguration-' + checkType,
    JSON.stringify(configuration)
  );
};
export default function RuleMining({
  timePartitioned,
  setTimePartitioned
}: {
  timePartitioned?: 'daily' | 'monthly';
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
}) {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();

  const ruleEnabledInProfilingOnly : boolean = checkTypes == CheckTypes.PROFILING ? true : false;

  const defaultParameters: CheckMiningParametersModel = {
    severity_level: 'error',
    fail_checks_at_percent_error_rows: 2.0,
    copy_failed_profiling_checks: true,
    copy_disabled_profiling_checks: false,
    copy_profiling_checks: true,
    reconfigure_policy_enabled_checks: ruleEnabledInProfilingOnly,
    propose_minimum_row_count: ruleEnabledInProfilingOnly,
    propose_column_count: ruleEnabledInProfilingOnly,
    propose_timeliness_checks: ruleEnabledInProfilingOnly,
    propose_nulls_checks: ruleEnabledInProfilingOnly,
    propose_not_nulls_checks: ruleEnabledInProfilingOnly,
    propose_column_exists: ruleEnabledInProfilingOnly,
    propose_text_values_data_type: ruleEnabledInProfilingOnly,
    propose_uniqueness_checks: ruleEnabledInProfilingOnly,
    propose_numeric_ranges: ruleEnabledInProfilingOnly,
    propose_percentile_ranges: false, // intentional false
    propose_text_length_ranges: ruleEnabledInProfilingOnly,
    propose_word_count_ranges: ruleEnabledInProfilingOnly,
    propose_date_checks: ruleEnabledInProfilingOnly,
    propose_bool_percent_checks: ruleEnabledInProfilingOnly,
    propose_values_in_set_checks: ruleEnabledInProfilingOnly,
    propose_top_values_checks: false, // intentional false
    propose_text_conversion_checks: ruleEnabledInProfilingOnly,
    propose_standard_pattern_checks: ruleEnabledInProfilingOnly,
    detect_regular_expressions: ruleEnabledInProfilingOnly,
    propose_whitespace_checks: ruleEnabledInProfilingOnly,
    apply_pii_checks: ruleEnabledInProfilingOnly,
    values_in_set_treat_rare_values_as_invalid: ruleEnabledInProfilingOnly,
    propose_custom_checks: ruleEnabledInProfilingOnly
  };
  const { runPartitionedChecks } = useTree();

  const [configuration, setConfiguration] =
    useState<CheckMiningParametersModel>({
      ...defaultParameters,
      ...(getRuleMiningConfigurationLocalStorage(checkTypes) ?? {})
    });
  const [runChecksDialogOpened, setRunChecksDialogOpened] = useState(false);
  const [checksUI, setChecksUI] = useState<CheckMiningProposalModel>({});
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdatedFilters, setIsUpdatedFilters] = useState(false);
  const [loading, setLoading] = useState(false);
  const [runChecksJobTemplate, setRunChecksJobTemplate] = useState<
    CheckSearchFilters | undefined
  >({});
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const dispatch = useActionDispatch();
  const onChangeConfiguration = (conf: any) => {
    const newConfiguration = { ...configuration, ...conf };
    setConfiguration(newConfiguration);
    setRuleMiningConfigurationLocalStorage(checkTypes, newConfiguration);
    setIsUpdatedFilters(true);
  };

  const onChangeChecksUI = (checks: CheckMiningProposalModel) => {
    setChecksUI(checks);
    setIsUpdated(true);
  };

  const { userProfile, isOpen } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const toggleOpen = () => {
    if (!isOpen) {
      dispatch(toggleMenu(true));
    }
  };

  const proposeChecks = async (flash?: boolean) => {
    const addPrefix = (key: string) => {
      if (key.includes('*') || key.length === 0) {
        return key;
      } else {
        return `*${key}*`;
      }
    };

    const getRuleParametersConfiguredChecks = (
      checks: CheckMiningProposalModel
    ) => {
      const sortedColumns = Object.keys(checks.column_checks ?? {})
        .sort()
        .reduce((sortedObj, key) => {
          if (!checks.column_checks) return {};
          (sortedObj as any)[key] = checks.column_checks[key];
          return sortedObj;
        }, {});
      setChecksUI({ ...checks, column_checks: sortedColumns });
      const tableChecksChecked = getRuleParametersConfigured(
        checks.table_checks
      );
      let columnChecksChecked = false;

      Object.values(checks.column_checks ?? {}).forEach((columnCheck) => {
        if (getRuleParametersConfigured(columnCheck)) {
          columnChecksChecked = true;
        }
      });
      dispatch(
        setRuleParametersConfigured(
          checkTypes,
          firstLevelActiveTab,
          tableChecksChecked || columnChecksChecked
        )
      );
    };

    const getShouldUserCollectStatisitcs = (
      checksUI: CheckMiningProposalModel
    ) => {
      if (checksUI.missing_current_statistics) {
        if (checkTypes === CheckTypes.PROFILING) {
          dispatch(
            setJobAllert({
              activeTab: firstLevelActiveTab,
              action: 'statistics',
              tooltipMessage:
                'The rule miner cannot propose rules because the statistics about the table were not collected. Please go to the profiling section and collect statistics for the table'
            })
          );
        } else {
          dispatch(
            setJobAllert({
              activeTab: firstLevelActiveTab,
              action: 'profiling',
              tooltipMessage:
                'Missing basic data statistics and the rule minner is limited to copying configured profiling checks. Please go to the profiling section and collect statistics for the table'
            })
          );
        }
      } else {
        if (flash && typeof flash === 'boolean') {
          dispatch(
            setJobAllert({
              activeTab: firstLevelActiveTab,
              action: 'table-quality-status',
              tooltipMessage:
                'When the "run checks" job finishes, the table quality status will show the status of all passed and failed data quality checks.'
            })
          );
        }
      }
    };

    const configurationWithPrefix: CheckMiningParametersModel = {
      ...configuration,
      category_filter: addPrefix(configuration.category_filter ?? ''),
      column_name_filter: addPrefix(configuration.column_name_filter ?? ''),
      check_name_filter: addPrefix(configuration.check_name_filter ?? ''),
      propose_checks_from_statistics: checkTypes === CheckTypes.PROFILING
    };
    setLoading(true);
    switch (checkTypes) {
      case CheckTypes.PROFILING:
        await RuleMiningApiClient.proposeTableProfilingChecks(
          connection,
          schema,
          table,
          configurationWithPrefix
        )
          .then((response) => {
            setRunChecksJobTemplate(response.data.run_checks_job);
            getShouldUserCollectStatisitcs(response.data);
            getRuleParametersConfiguredChecks(response.data);
          })
          .finally(() => {
            setLoading(false);
          });
        break;
      case CheckTypes.PARTITIONED:
        await RuleMiningApiClient.proposeTablePartitionedChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          configurationWithPrefix
        )
          .then((response) => {
            setRunChecksJobTemplate(response.data.run_checks_job);
            getShouldUserCollectStatisitcs(response.data);
            getRuleParametersConfiguredChecks(response.data);
          })
          .finally(() => {
            setLoading(false);
          });
        break;
      case CheckTypes.MONITORING:
        await RuleMiningApiClient.proposeTableMonitoringChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          configurationWithPrefix
        )
          .then((response) => {
            setRunChecksJobTemplate(response.data.run_checks_job);
            getShouldUserCollectStatisitcs(response.data);
            getRuleParametersConfiguredChecks(response.data);
          })
          .finally(() => {
            setLoading(false);
          });
    }
    setIsUpdatedFilters(false);
  };

  const applyChecks = async () => {
    switch (checkTypes) {
      case CheckTypes.PROFILING:
        await RuleMiningApiClient.applyProposedProfilingChecks(
          connection,
          schema,
          table,
          checksUI
        ).then(() => {
          setRunChecksDialogOpened(true);
        });
        break;
      case CheckTypes.PARTITIONED:
        await RuleMiningApiClient.applyProposedPartitionedChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          checksUI
        ).then(() => {
          setRunChecksDialogOpened(true);
        });
        break;
      case CheckTypes.MONITORING:
        await RuleMiningApiClient.applyProposedMonitoringChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          checksUI
        ).then(() => {
          setRunChecksDialogOpened(true);
        });
    }
    setIsUpdated(false);
  };

  useEffect(() => {
    proposeChecks();
  }, [checkTypes, connection, schema, table, timePartitioned]);

  return (
    <div>
      {timePartitioned &&
        userProfile &&
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at && (
          <div className="border-b border-gray-300">
            <Tabs
              tabs={tabs}
              activeTab={timePartitioned}
              onChange={setTimePartitioned}
              className="pt-2"
            />
          </div>
        )}
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="text-sm px-8 pt-4">
          The number of propositions shown depends on the activated checks in
          the Profiling section.
          <br />
          To increase the number of propositions, you can either activate more
          profiling checks manually or use the data quality rule miner in the Profiling
          section.
        </div>
      )}
      <div>
        <RuleMiningFilters
          checkTypes={checkTypes}
          configuration={configuration}
          onChangeConfiguration={onChangeConfiguration}
          proposeChecks={proposeChecks}
          applyChecks={applyChecks}
          isUpdated={isUpdated}
          isUpdatedFilters={isUpdatedFilters}
          isApplyDisabled={
            checksUI &&
            checksUI.table_checks?.categories?.length === 0 &&
            Object.keys(checksUI?.column_checks ?? {}).length === 0
          }
        />
      </div>
      {checksUI &&
      checksUI.table_checks?.categories?.length === 0 &&
      Object.keys(checksUI?.column_checks ?? {}).length === 0 ? (
        <div className="p-4">
          No new data quality checks are suggested. Please configure additional
          profiling checks manually to detect other data quality issues and get
          a new rule proposal
        </div>
      ) : (
        <RuleMiningChecksContainer
          onUpdate={() => undefined}
          checksUI={checksUI}
          onChange={onChangeChecksUI}
          loading={loading}
          timePartitioned={timePartitioned}
          setTimePartitioned={setTimePartitioned}
        />
      )}
      <RuleMiningConfirmDialog
        open={runChecksDialogOpened}
        onClose={() => {
          proposeChecks();
          setRunChecksDialogOpened(false);
        }}
        message="The proposed configuration has been applied. Do you want to run activated checks?"
        onConfirm={() => {
          setRunChecksDialogOpened(false);
          runPartitionedChecks({
            check_search_filters: runChecksJobTemplate
          }).then(() => {
            proposeChecks(true);
          });
          toggleOpen();
        }}
      />
    </div>
  );
}
