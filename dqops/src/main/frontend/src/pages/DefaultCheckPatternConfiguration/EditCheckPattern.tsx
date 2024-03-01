import React, { useEffect, useState } from 'react';
import Button from '../../components/Button';
import DataQualityChecks from '../../components/DataQualityChecks';
import SvgIcon from '../../components/SvgIcon';
import DefaultCheckTargetConfiguration from './DefaultCheckTargetConfiguration';
import Tabs from '../../components/Tabs';
import { AxiosPromise, AxiosResponse } from 'axios';
import {
  CheckContainerModel,
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel,
  TargetColumnPatternSpec,
  TargetTablePatternSpec
} from '../../api';
import {
  DefaultColumnCheckPatternsApiClient,
  DefaultTableCheckPatternsApiClient
} from '../../services/apiClient';
import { CheckRunMonitoringScheduleGroup } from '../../shared/enums/scheduling.enum';

const tabs = [
  {
    label: 'Table target',
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

type TCheckTypes =
  | 'profiling'
  | 'monitoring_daily'
  | 'monitoring_monthly'
  | 'partitioned_daily'
  | 'partitioned_monthly';

type TCheckContainerDiverse = {
  [key in TCheckTypes]: CheckContainerModel | undefined;
};

type TTarget =
  | DefaultColumnChecksPatternListModel
  | DefaultTableChecksPatternListModel;
type TTargetSpec = TargetColumnPatternSpec | TargetTablePatternSpec;
type TEditCheckPatternProps = {
  type: 'table' | 'column';
  pattern_name: string;
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
  pattern_name,
  create
}: TEditCheckPatternProps) {
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';

  const [activeTab, setActiveTab] = useState('table-target');
  const [checkContainers, setCheckContainers] =
    useState<TCheckContainerDiverse>(initialState);
  const [target, setTarget] = useState<TTarget>({});
  const [isUpdated, setIsUpdated] = useState(false);
  const onChangeTab = (tab: any) => {
    setActiveTab(tab);
  };

  const onChangeTarget = (
    updatedTarget: Partial<TTarget> | Partial<TTargetSpec>
  ) => {
    if ('pattern_name' in updatedTarget || 'priority' in updatedTarget) {
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
    if (!pattern_name) return;
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
          DefaultColumnCheckPatternsApiClient.updateDefaultProfilingColumnChecksPattern,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          DefaultColumnCheckPatternsApiClient.updateDefaultMonitoringDailyColumnChecksPattern,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          DefaultColumnCheckPatternsApiClient.updateDefaultMonitoringMonthlyColumnChecksPattern,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          DefaultColumnCheckPatternsApiClient.updateDefaultPartitionedDailyColumnChecksPattern,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          DefaultColumnCheckPatternsApiClient.updateDefaultPartitionedMonthlyColumnChecksPattern
      },
      table: {
        [CheckRunMonitoringScheduleGroup.profiling]:
          DefaultTableCheckPatternsApiClient.updateDefaultProfilingTableChecksPattern,
        [CheckRunMonitoringScheduleGroup.monitoring_daily]:
          DefaultTableCheckPatternsApiClient.updateDefaultMonitoringDailyTableChecksPattern,
        [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
          DefaultTableCheckPatternsApiClient.updateDefaultMonitoringMonthlyTableChecksPattern,
        [CheckRunMonitoringScheduleGroup.partitioned_daily]:
          DefaultTableCheckPatternsApiClient.updateDefaultPartitionedDailyTableChecksPattern,
        [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
          DefaultTableCheckPatternsApiClient.updateDefaultPartitionedMonthlyTableChecksPattern
      }
    };

    const apiClient = type === 'column' ? apiClients.column : apiClients.table;

    Object.keys(apiClient).forEach((key) => {
      if (checkContainers?.[key as TCheckTypes]) {
        const apiFunction = apiClient[key as TCheckTypes];
        apiFunction(pattern_name, checkContainers[key as TCheckTypes]);
      }
    });

    if (type === 'column') {
      DefaultColumnCheckPatternsApiClient.updateDefaultColumnChecksPatternTarget(
        pattern_name,
        target
      );
    } else {
      DefaultTableCheckPatternsApiClient.updateDefaultTableChecksPatternTarget(
        pattern_name,
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

  useEffect(() => {
    if (!pattern_name) return;

    const getTarget = () => {
      if (type === 'column') {
        DefaultColumnCheckPatternsApiClient.getDefaultColumnChecksPatternTarget(
          pattern_name
        ).then((res) => setTarget(res?.data));
      } else {
        DefaultTableCheckPatternsApiClient.getDefaultTableChecksPatternTarget(
          pattern_name
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
            DefaultColumnCheckPatternsApiClient.getDefaultProfilingColumnChecksPattern,
          [CheckRunMonitoringScheduleGroup.monitoring_daily]:
            DefaultColumnCheckPatternsApiClient.getDefaultMonitoringDailyColumnChecksPattern,
          [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
            DefaultColumnCheckPatternsApiClient.getDefaultMonitoringMonthlyColumnChecksPattern,
          [CheckRunMonitoringScheduleGroup.partitioned_daily]:
            DefaultColumnCheckPatternsApiClient.getDefaultPartitionedDailyColumnChecksPattern,
          [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
            DefaultColumnCheckPatternsApiClient.getDefaultPartitionedMonthlyColumnChecksPattern
        },
        table: {
          [CheckRunMonitoringScheduleGroup.profiling]:
            DefaultTableCheckPatternsApiClient.getDefaultProfilingTableChecksPattern,
          [CheckRunMonitoringScheduleGroup.monitoring_daily]:
            DefaultTableCheckPatternsApiClient.getDefaultMonitoringDailyTableChecksPattern,
          [CheckRunMonitoringScheduleGroup.monitoring_monthly]:
            DefaultTableCheckPatternsApiClient.getDefaultMonitoringMonthlyTableChecksPattern,
          [CheckRunMonitoringScheduleGroup.partitioned_daily]:
            DefaultTableCheckPatternsApiClient.getDefaultPartitionedDailyTableChecksPattern,
          [CheckRunMonitoringScheduleGroup.partitioned_monthly]:
            DefaultTableCheckPatternsApiClient.getDefaultPartitionedMonthlyTableChecksPattern
        }
      };

      const apiClient =
        type === 'column' ? apiClients.column : apiClients.table;

      const apiCall = apiClient[activeTab as CheckRunMonitoringScheduleGroup];
      if (apiCall) {
        await apiCall(pattern_name).then(callBack);
      }
    };

    getTarget();
    getChecks();
  }, [pattern_name, create, activeTab]);

  function getCheckOverview(): void {}

  return (
    <>
      <div className="px-4">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="grid" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">
              Check pattern editor
            </div>
          </div>
          <Button
            label="Save"
            color={isUpdated ? 'primary' : 'secondary'}
            className="pl-14 pr-14"
            onClick={updateChecks}
          />
        </div>
        <div className="border-b border-gray-300">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
        </div>
        <div>
          {activeTab !== 'table-target' ? (
            <DataQualityChecks
              checksUI={checkContainers?.[activeTab as TCheckTypes]}
              onUpdate={updateChecks}
              onChange={handleChange}
              checkResultsOverview={[]}
              getCheckOverview={getCheckOverview}
              isDefaultEditing={true}
            />
          ) : (
            <DefaultCheckTargetConfiguration
              type={type}
              target={target}
              onChangeTarget={onChangeTarget}
            />
          )}
        </div>
      </div>
    </>
  );
}
