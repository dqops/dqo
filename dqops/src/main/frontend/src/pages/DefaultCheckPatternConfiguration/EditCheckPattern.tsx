import { AxiosPromise, AxiosResponse } from 'axios';
import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckContainerModel,
  ColumnQualityPolicyListModel,
  TableQualityPolicyListModel,
  TargetColumnPatternSpec,
  TargetTablePatternSpec
} from '../../api';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { IRootState } from '../../redux/reducers';
import {
  ColumnQualityPoliciesApiClient,
  TableQualityPoliciesApiClient
} from '../../services/apiClient';
import { CheckRunMonitoringScheduleGroup } from '../../shared/enums/scheduling.enum';
import CopyCheckPatternDialog from './CopyCheckPatternDialog';
import DefaultCheckTargetConfiguration from './DefaultCheckTargetConfiguration';

const tabsTableChecks = [
  {
    label: 'Target table',
    value: 'table-target'
  },
  {
    label: 'Profiling',
    value: CheckRunMonitoringScheduleGroup.profiling
  },
  {
    label: 'Monitoring Daily',
    value: CheckRunMonitoringScheduleGroup.monitoring_daily
  },
  {
    label: 'Monitoring Monthly',
    value: CheckRunMonitoringScheduleGroup.monitoring_monthly
  },
  {
    label: 'Partition Daily',
    value: CheckRunMonitoringScheduleGroup.partitioned_daily
  },
  {
    label: 'Partition Monthly',
    value: CheckRunMonitoringScheduleGroup.partitioned_monthly
  }
];

const tabsColumnChecks = [
  {
    label: 'Target column',
    value: 'table-target'
  },
  {
    label: 'Profiling',
    value: CheckRunMonitoringScheduleGroup.profiling
  },
  {
    label: 'Monitoring Daily',
    value: CheckRunMonitoringScheduleGroup.monitoring_daily
  },
  {
    label: 'Monitoring Monthly',
    value: CheckRunMonitoringScheduleGroup.monitoring_monthly
  },
  {
    label: 'Partition Daily',
    value: CheckRunMonitoringScheduleGroup.partitioned_daily
  },
  {
    label: 'Partition Monthly',
    value: CheckRunMonitoringScheduleGroup.partitioned_monthly
  }
];

const tabsTableChecksFreeTrial = [
  {
    label: 'Target table',
    value: 'table-target'
  },
  {
    label: 'Profiling',
    value: CheckRunMonitoringScheduleGroup.profiling
  },
  {
    label: 'Monitoring',
    value: CheckRunMonitoringScheduleGroup.monitoring_daily
  },
  {
    label: 'Partition',
    value: CheckRunMonitoringScheduleGroup.partitioned_daily
  }
];

const tabsColumnChecksFreeTrial = [
  {
    label: 'Target column',
    value: 'table-target'
  },
  {
    label: 'Profiling',
    value: CheckRunMonitoringScheduleGroup.profiling
  },
  {
    label: 'Monitoring',
    value: CheckRunMonitoringScheduleGroup.monitoring_daily
  },
  {
    label: 'Partition',
    value: CheckRunMonitoringScheduleGroup.partitioned_daily
  }
];

type TCheckTypes =
  | 'profiling'
  | 'monitoring_daily'
  | 'monitoring_monthly'
  | 'partitioned_daily'
  | 'partitioned_monthly';

type TCheckContainerDiverse = {
  [key in TCheckTypes]: CheckContainerModel | undefined;
};

type TTarget = ColumnQualityPolicyListModel | TableQualityPolicyListModel;
type TTargetSpec = TargetColumnPatternSpec | TargetTablePatternSpec;
type TEditCheckPatternProps = {
  type: 'table' | 'column';
  policy_name: string;
  create: boolean;
};
const initialState: TCheckContainerDiverse = {
  profiling: undefined,
  monitoring_daily: undefined,
  monitoring_monthly: undefined,
  partitioned_daily: undefined,
  partitioned_monthly: undefined
};

export default function EditCheckPattern({
  type,
  policy_name,
  create
}: TEditCheckPatternProps) {
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';
  const tabs =
    userProfile &&
    userProfile.license_type &&
    userProfile.license_type?.toLowerCase() !== 'free' &&
    !userProfile.trial_period_expires_at
      ? type === 'column'
        ? tabsColumnChecks
        : tabsTableChecks
      : type === 'column'
      ? tabsColumnChecksFreeTrial
      : tabsTableChecksFreeTrial;
  const history = useHistory();
  const dispatch = useActionDispatch();
  const { activeTab = 'table-target' } = qs.parse(location.search) as any;
  const [checkContainers, setCheckContainers] =
    useState<TCheckContainerDiverse>(initialState);
  const [target, setTarget] = useState<TTarget>({});
  const [isUpdated, setIsUpdated] = useState(false);
  const [copyPatternOpen, setCopyPatternOpen] = useState(false);
  const onChangeTab = (tab: string) => {
    history.push(`${location.pathname}?activeTab=${tab}`);
  };

  const onChangeTarget = (
    updatedTarget: Partial<TTarget> | Partial<TTargetSpec>
  ) => {
    if (
      'policy_name' in updatedTarget ||
      'priority' in updatedTarget ||
      'description' in updatedTarget ||
      'disabled' in updatedTarget
    ) {
      setTarget((prev) => ({
        ...prev,
        ...updatedTarget
      }));
    } else {
      setTarget((prev) => ({
        ...prev,
        [targetSpecKey]: {
          ...(target?.[targetSpecKey as keyof TTarget] as TTargetSpec),
          ...updatedTarget
        }
      }));
    }
    setIsUpdated(true);
  };

  const updateChecks = async () => {
    if (!policy_name) return;
    const apiClients: {
      column: Record<
        TCheckTypes,
        (
          patternName: string,
          body?: CheckContainerModel | undefined,
          options?: any
        ) => AxiosPromise<object>
      >;
      table: Record<
        TCheckTypes,
        (
          patternName: string,
          body?: CheckContainerModel | undefined,
          options?: any
        ) => AxiosPromise<object>
      >;
    } = {
      column: {
        [CheckRunMonitoringScheduleGroup.profiling]:
          ColumnQualityPoliciesApiClient.updateProfilingColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          ColumnQualityPoliciesApiClient.updateMonitoringDailyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          ColumnQualityPoliciesApiClient.updateMonitoringMonthlyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          ColumnQualityPoliciesApiClient.updatePartitionedDailyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          ColumnQualityPoliciesApiClient.updatePartitionedMonthlyColumnQualityPolicy
      },
      table: {
        [CheckRunMonitoringScheduleGroup.profiling]:
          TableQualityPoliciesApiClient.updateProfilingTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          TableQualityPoliciesApiClient.updateMonitoringDailyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          TableQualityPoliciesApiClient.updateMonitoringMonthlyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          TableQualityPoliciesApiClient.updatePartitionedDailyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          TableQualityPoliciesApiClient.updatePartitionedMonthlyTableQualityPolicy
      }
    };

    const apiClient = type === 'column' ? apiClients.column : apiClients.table;

    const promises: Promise<any>[] = [];

    Object.keys(apiClient).forEach((key) => {
      if (
        checkContainers?.[key as TCheckTypes] &&
        Object.keys(checkContainers?.[activeTab as TCheckTypes] ?? {})
          .length !== 0
      ) {
        const apiFunction = apiClient[key as TCheckTypes];
        promises.push(
          apiFunction(policy_name, checkContainers[key as TCheckTypes])
        );
      }
    });

    await Promise.all(promises);

    if (type === 'column') {
      await ColumnQualityPoliciesApiClient.updateColumnQualityPolicyTarget(
        policy_name,
        target
      );
    } else {
      await TableQualityPoliciesApiClient.updateTableQualityPolicyTarget(
        policy_name,
        target
      );
    }
    setIsUpdated(false);
  };

  const handleChange = (value: CheckContainerModel) => {
    setCheckContainers((prev) => ({
      ...prev,
      [activeTab as TCheckTypes]: value
    }));
    setIsUpdated(true);
  };

  const getTarget = () => {
    if (!policy_name) return;
    if (type === 'column') {
      ColumnQualityPoliciesApiClient.getColumnQualityPolicyTarget(
        policy_name
      ).then((res) => setTarget(res?.data));
    } else {
      TableQualityPoliciesApiClient.getTableQualityPolicyTarget(
        policy_name
      ).then((res) => setTarget(res?.data));
    }
  };
  const getChecks = async () => {
    const callBack = (value: AxiosResponse<CheckContainerModel, any>) => {
      setCheckContainers((prev) => ({
        ...prev,
        [activeTab as TCheckTypes]: value.data
      }));
    };

    const apiClients = {
      column: {
        [CheckRunMonitoringScheduleGroup.profiling]:
          ColumnQualityPoliciesApiClient.getProfilingColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          ColumnQualityPoliciesApiClient.getMonitoringDailyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          ColumnQualityPoliciesApiClient.getMonitoringMonthlyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          ColumnQualityPoliciesApiClient.getPartitionedDailyColumnQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          ColumnQualityPoliciesApiClient.getPartitionedMonthlyColumnQualityPolicy
      },
      table: {
        [CheckRunMonitoringScheduleGroup.profiling]:
          TableQualityPoliciesApiClient.getProfilingTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          TableQualityPoliciesApiClient.getMonitoringDailyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          TableQualityPoliciesApiClient.getMonitoringMonthlyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          TableQualityPoliciesApiClient.getPartitionedDailyTableQualityPolicy,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          TableQualityPoliciesApiClient.getPartitionedMonthlyTableQualityPolicy
      }
    };

    const apiClient = type === 'column' ? apiClients.column : apiClients.table;

    const apiCall = apiClient[activeTab as CheckRunMonitoringScheduleGroup];
    if (apiCall) {
      if (!policy_name) return;
      await apiCall(policy_name).then(callBack);
    }
  };

  useEffect(() => {
    if (!policy_name) return;
    if (
      !checkContainers?.[activeTab as TCheckTypes] ||
      Object.keys(checkContainers?.[activeTab as TCheckTypes] ?? {}).length ===
        0
    ) {
      getChecks();
    }
  }, [activeTab]);

  useEffect(() => {
    if (!policy_name) return;
    getChecks();
  }, [policy_name, create, type]);

  useEffect(() => {
    getTarget();
  }, [policy_name]);

  useEffect(() => {
    getTarget();
  }, [create]);

  function getCheckOverview(): void {}

  return (
    <>
      <div className="px-4">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
              {type?.replace(/./, (c) => c.toUpperCase())} check pattern{' '}
              {policy_name}
            </div>
          </div>
          <div className="flex items-center gap-x-4">
            <Button
              label="Copy"
              color="primary"
              className="pl-14 pr-14"
              onClick={() => setCopyPatternOpen(true)}
            />
            <Button
              label="Save"
              color={isUpdated ? 'primary' : 'secondary'}
              className="pl-14 pr-14"
              onClick={updateChecks}
            />
          </div>
        </div>
        <div className="border-b border-gray-300">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
        </div>
        <div>
          {activeTab === 'table-target' ? (
            <DefaultCheckTargetConfiguration
              type={type}
              target={target}
              onChangeTarget={onChangeTarget}
            />
          ) : (
            tabs.map((x, index) => {
              if (index === 0) return <></>;

              return x.value === activeTab ? (
                <DataQualityChecks
                  key={index}
                  checksUI={checkContainers?.[x.value as TCheckTypes]}
                  onUpdate={updateChecks}
                  onChange={handleChange}
                  checkResultsOverview={[]}
                  getCheckOverview={getCheckOverview}
                  isDefaultEditing={true}
                />
              ) : (
                <></>
              );
            })
          )}
        </div>
        <CopyCheckPatternDialog
          type={type}
          sourceTableName={policy_name}
          open={copyPatternOpen}
          setOpen={setCopyPatternOpen}
        />
      </div>
    </>
  );
}
