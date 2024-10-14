import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { AutoImportTablesSpec, CronScheduleSpec } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionSchedulingGroup,
  resetConnectionSchedulingGroup,
  setIsUpdatedSchedulingGroup,
  setUpdatedSchedulingGroup,
  updateConnectionSchedulingGroup
} from '../../../redux/actions/connection.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { ConnectionApiClient } from '../../../services/apiClient';
import { CheckRunMonitoringScheduleGroup } from '../../../shared/enums/scheduling.enum';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Input from '../../Input';
import ScheduleView from '../../ScheduleView';
import Tabs from '../../Tabs';
import ConnectionActionGroup from './ConnectionActionGroup';

const ScheduleDetail = () => {
  const {
    connection,
    checkTypes
  }: { checkTypes: CheckTypes; connection: string } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job);

  const getPageTabs = () => {
    switch (checkTypes) {
      case CheckTypes.PROFILING: {
        return [
          {
            label: 'Profiling',
            value: CheckRunMonitoringScheduleGroup.profiling
          }
        ];
      }
      case CheckTypes.PARTITIONED: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
              {
                label: 'Partition Daily',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              },
              {
                label: 'Partition Monthly',
                value: CheckRunMonitoringScheduleGroup.partitioned_monthly
              }
            ]
          : [
              {
                label: 'Partition',
                value: CheckRunMonitoringScheduleGroup.partitioned_daily
              }
            ];
      }
      case CheckTypes.MONITORING: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
              {
                label: 'Monitoring Daily',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              },
              {
                label: 'Monitoring Monthly',
                value: CheckRunMonitoringScheduleGroup.monitoring_monthly
              }
            ]
          : [
              {
                label: 'Monitoring',
                value: CheckRunMonitoringScheduleGroup.monitoring_daily
              }
            ];
      }
      default: {
        return userProfile &&
          userProfile.license_type &&
          userProfile.license_type?.toLowerCase() !== 'free' &&
          !userProfile.trial_period_expires_at
          ? [
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
              },
              {
                label: 'Import tables',
                value: 'import_tables'
              }
            ]
          : [
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
              },
              {
                label: 'Import tables',
                value: 'import_tables'
              }
            ];
      }
    }
  };

  const [tabs, setTabs] = useState(getPageTabs());
  const [autoImportParameters, setAutoImportParameters] =
    useState<AutoImportTablesSpec>({});
  const [tableImportLimit, setTableImportLimit] = useState<number | undefined>(
    0
  );
  const updateCron = (schedule: CronScheduleSpec) => {
    setAutoImportParameters({
      ...autoImportParameters,
      schedule
    });
    dispatch(
      setIsUpdatedSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        activeTab,
        true
      )
    );
  };
  const dispatch = useActionDispatch();
  const location = useLocation() as any;
  const { activeTab = CheckRunMonitoringScheduleGroup.profiling } = qs.parse(
    location.search
  ) as any;
  const history = useHistory();

  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const {
    scheduleGroups,
    isUpdating
  }: {
    scheduleGroups?: any;
    isUpdating?: boolean;
  } = useSelector(getFirstLevelState(checkTypes));

  const updatedSchedule = scheduleGroups?.[activeTab]?.updatedSchedule;
  const isUpdatedSchedule = scheduleGroups?.[activeTab]?.isUpdatedSchedule;

  const onChangeTab = (tab: CheckRunMonitoringScheduleGroup) => {
    history.push(`${location.pathname}?activeTab=${tab}`);
  };

  const handleChange = (obj: any) => {
    dispatch(
      setIsUpdatedSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        activeTab,
        true
      )
    );
    dispatch(
      setUpdatedSchedulingGroup(checkTypes, firstLevelActiveTab, activeTab, {
        ...updatedSchedule,
        ...obj
      })
    );
  };

  useEffect(() => {
    if (activeTab === 'import_tables') {
      ConnectionApiClient.getConnectionAutoImport(connection).then((res) => {
        setAutoImportParameters(res.data);
      });
    } else {
      if (updatedSchedule === null || updatedSchedule === undefined) {
        dispatch(
          getConnectionSchedulingGroup(
            checkTypes,
            firstLevelActiveTab,
            connection,
            activeTab
          )
        );
      }
    }
  }, [connection, activeTab, updatedSchedule]);

  const onUpdate = async () => {
    if (activeTab === 'import_tables') {
      await ConnectionApiClient.updateConnectionAutoImport(
        connection,
        autoImportParameters
      );
    } else {
      if (updatedSchedule === null || updatedSchedule === undefined) {
        return;
      }
      await dispatch(
        updateConnectionSchedulingGroup(
          checkTypes,
          firstLevelActiveTab,
          connection,
          activeTab,
          updatedSchedule
        )
      );
      await dispatch(
        getConnectionSchedulingGroup(
          checkTypes,
          firstLevelActiveTab,
          connection,
          activeTab
        )
      );
    }
    dispatch(
      setIsUpdatedSchedulingGroup(
        checkTypes,
        firstLevelActiveTab,
        activeTab,
        false
      )
    );
  };

  useEffect(() => {
    setTabs((prev) =>
      prev.map((tab) =>
        tab.value === activeTab ? { ...tab, isUpdated: isUpdatedSchedule } : tab
      )
    );
  }, [isUpdatedSchedule, activeTab]);

  useEffect(() => {
    setTabs((prev) => prev.map((tab) => ({ ...tab, isUpdate: false })));
    dispatch(resetConnectionSchedulingGroup(checkTypes, firstLevelActiveTab));
  }, [connection]);

  return (
    <div className="py-2">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <div className="border-b border-gray-300 px-0">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div className="px-8">
        {activeTab === 'import_tables' && (
          <div className="flex flex-col gap-y-4 text-sm mt-4">
            <div className="flex items-center">
              <div className="w-41">Schema filter</div>
              <Input
                value={autoImportParameters.schema_filter}
                onChange={(e) => {
                  setAutoImportParameters({
                    ...autoImportParameters,
                    schema_filter: e.target.value
                  });
                  dispatch(
                    setIsUpdatedSchedulingGroup(
                      checkTypes,
                      firstLevelActiveTab,
                      activeTab,
                      true
                    )
                  );
                }}
              />
            </div>
            <div className="flex items-center">
              <div className="w-41">Table name contains</div>
              <Input
                value={autoImportParameters.table_name_contains}
                onChange={(e) => {
                  setAutoImportParameters({
                    ...autoImportParameters,
                    table_name_contains: e.target.value
                  });
                  dispatch(
                    setIsUpdatedSchedulingGroup(
                      checkTypes,
                      firstLevelActiveTab,
                      activeTab,
                      true
                    )
                  );
                }}
              />
            </div>
            {/* <div className="flex items-center">
              <div className="w-41">Table import limit</div>
              <Input
                value={tableImportLimit}
                onChange={(e) => {
                  setTableImportLimit(
                    e.target.value.length !== 0
                      ? Number(e.target.value)
                      : undefined
                  );
                  dispatch(
                    setIsUpdatedSchedulingGroup(
                      checkTypes,
                      firstLevelActiveTab,
                      activeTab,
                      true
                    )
                  );
                }}
              />
            </div> */}
          </div>
        )}
        {activeTab !== 'import_tables' ? (
          <ScheduleView
            handleChange={handleChange}
            schedule={updatedSchedule}
            importTables={activeTab === 'import_tables'}
          />
        ) : (
          <ScheduleView
            handleChange={updateCron}
            schedule={autoImportParameters.schedule}
            importTables
          />
        )}
      </div>
    </div>
  );
};

export default ScheduleDetail;
