import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckMiningParametersModel,
  CheckMiningProposalModel
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setJobAllert } from '../../redux/actions/job.actions';
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
  (): CheckMiningParametersModel => {
    const configuration = localStorage.getItem('ruleMiningConfiguration');
    return configuration ? JSON.parse(configuration) : null;
  };
const setRuleMiningConfigurationLocalStorage = (
  configuration: CheckMiningParametersModel
) => {
  localStorage.setItem(
    'ruleMiningConfiguration',
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

  const defaultParameters: CheckMiningParametersModel = {
    severity_level: 'error',
    fail_checks_at_percent_error_rows: 2.0,
    copy_failed_profiling_checks: false,
    copy_disabled_profiling_checks: false,
    copy_profiling_checks: true,
    propose_default_checks: true,
    propose_minimum_row_count: true,
    propose_column_count: true,
    propose_timeliness_checks: true,
    propose_nulls_checks: true,
    propose_not_nulls_checks: true,
    propose_column_exists: true,
    propose_text_values_data_type: true,
    propose_uniqueness_checks: true,
    propose_numeric_ranges: true,
    propose_text_length_ranges: true,
    propose_date_checks: true,
    propose_bool_percent_checks: true,
    propose_values_in_set_checks: true,
    propose_top_values_checks: true,
    propose_text_conversion_checks: true,
    propose_standard_pattern_checks: true,
    propose_whitespace_checks: true,
    apply_pii_checks: true,
    values_in_set_treat_rare_values_as_invalid: true,
    propose_custom_checks: true
  };
  const { runPartitionedChecks } = useTree();
  const [configuration, setConfiguration] =
    useState<CheckMiningParametersModel>({
      ...defaultParameters,
      ...(getRuleMiningConfigurationLocalStorage() ?? {})
    });
  const [runChecksDialogOpened, setRunChecksDialogOpened] = useState(false);
  const [checksUI, setChecksUI] = useState<CheckMiningProposalModel>({});
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdatedFilters, setIsUpdatedFilters] = useState(false);
  const [loading, setLoading] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const dispatch = useActionDispatch();
  const onChangeConfiguration = (conf: any) => {
    const newConfiguration = { ...configuration, ...conf };
    setConfiguration(newConfiguration);
    setRuleMiningConfigurationLocalStorage(newConfiguration);
    setIsUpdatedFilters(true);
  };

  const onChangeChecksUI = (checks: CheckMiningProposalModel) => {
    setChecksUI(checks);
    setIsUpdated(true);
  };

  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const proposeChecks = async (runChecks?: boolean) => {
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
      if (
        checksUI &&
        checksUI.table_checks?.categories?.length === 0 &&
        Object.keys(checksUI?.column_checks ?? {}).length === 0
      ) {
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
      }
    };

    const configurationWithPrefix: CheckMiningParametersModel = {
      ...configuration,
      category_filter: addPrefix(configuration.category_filter ?? ''),
      column_name_filter: addPrefix(configuration.column_name_filter ?? ''),
      check_name_filter: addPrefix(configuration.check_name_filter ?? '')
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
            console.log(response, runChecks);
            if (runChecks) {
              runPartitionedChecks({
                check_search_filters: response.data.run_checks_job
              }).then(() => {
                proposeChecks();
              });
            }
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
            if (runChecks) {
              runPartitionedChecks({
                check_search_filters: response.data.run_checks_job
              }).then(() => {
                proposeChecks();
              });
            }
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
            if (runChecks) {
              runPartitionedChecks({
                check_search_filters: response.data.run_checks_job
              }).then(() => {
                proposeChecks();
              });
            }
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
      <div>
        <RuleMiningFilters
          configuration={configuration}
          onChangeConfiguration={onChangeConfiguration}
          proposeChecks={proposeChecks}
          applyChecks={applyChecks}
          isUpdated={isUpdated}
          isUpdatedFilters={isUpdatedFilters}
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
        message="Do you want to run the checks?"
        onConfirm={() => {
          setRunChecksDialogOpened(false);
          proposeChecks(true);
        }}
      />
    </div>
  );
}
