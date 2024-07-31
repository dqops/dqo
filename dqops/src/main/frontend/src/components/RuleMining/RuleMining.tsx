import { Tabs } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckMiningParametersModel,
  CheckMiningProposalModel
} from '../../api';
import { IRootState } from '../../redux/reducers';
import { RuleMiningApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
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

const getRuleMiningConfigurationLocalStorage = () => {
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
      getRuleMiningConfigurationLocalStorage() ?? { severity_level: 'error' }
    );
  const [checksUI, setChecksUI] = useState<CheckMiningProposalModel>({});
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdatedFilters, setIsUpdatedFilters] = useState(false);
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
    switch (checkTypes) {
      case CheckTypes.PROFILING:
        await RuleMiningApiClient.proposeTableProfilingChecks(
          connection,
          schema,
          table,
          configuration
        ).then((response) => {
          setChecksUI(response.data);
        });
        break;
      case CheckTypes.PARTITIONED:
        await RuleMiningApiClient.proposeTablePartitionedChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          configuration
        ).then((response) => {
          setChecksUI(response.data);
        });
        break;
      case CheckTypes.MONITORING:
        await RuleMiningApiClient.proposeTablePartitionedChecks(
          connection,
          schema,
          table,
          timePartitioned ?? 'daily',
          configuration
        ).then((response) => {
          setChecksUI(response.data);
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
  }, []);

  return (
    <div>
      {' '}
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
      <RuleMiningChecksContainer
        onUpdate={() => undefined}
        checksUI={checksUI}
        onChange={onChangeChecksUI}
        loading={false}
        timePartitioned={timePartitioned}
        setTimePartitioned={setTimePartitioned}
      />{' '}
    </div>
  );
}
