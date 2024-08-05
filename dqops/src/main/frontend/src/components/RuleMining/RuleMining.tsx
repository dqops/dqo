import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckMiningParametersModel,
  CheckMiningProposalModel
} from '../../api';
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

const getRuleMiningConfigurationLocalStorage = () : CheckMiningParametersModel => {
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

  const [configuration, setConfiguration] =
  
    useState<CheckMiningParametersModel>(
      getRuleMiningConfigurationLocalStorage() ?? {
        severity_level: 'error',
        fail_checks_at_percent_error_rows: 2.0,
        copy_failed_profiling_checks: false,
        copy_disabled_profiling_checks: false,
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
        propose_accepted_values_checks: true,
        propose_custom_checks: true
      } as CheckMiningParametersModel
    );
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
  const proposeChecks = async () => {
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
      setChecksUI(checks);
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
            getShouldUserCollectStatisitcs(response.data);
            getRuleParametersConfiguredChecks(response.data);
          })
          .finally(() => {
            setLoading(false);
          });
        break;
      case CheckTypes.MONITORING:
        await RuleMiningApiClient.proposeTablePartitionedChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          configurationWithPrefix
        )
          .then((response) => {
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
          proposeChecks();
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
          proposeChecks();
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
          proposeChecks();
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
    </div>
  );
}
