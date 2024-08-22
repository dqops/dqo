import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum,
  FilteredNotificationModel,
  IncidentNotificationSpec
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  getConnectionIncidentGrouping,
  setActiveFirstLevelTab,
  setUpdateIncidentGroup,
  updateConnectionIncidentGrouping
} from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { FilteredNotificationsConfigurationsClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { sortPatterns, useDecodedParams } from '../../../utils';
import Button from '../../Button';
import Checkbox from '../../Checkbox';
import Loader from '../../Loader';
import NumberInput from '../../NumberInput';
import Select from '../../Select';
import SvgIcon from '../../SvgIcon';
import ConnectionActionGroup from './ConnectionActionGroup';
import CreateNotificationPattern from './NotificationPattern/CreateNotificationPattern';
import NotificationPatternTable from './NotificationPattern/NotificationPatternTable';
type TNotificationPattern = FilteredNotificationModel & {
  connection?: string;
  schema?: string;
  table?: string;
  qualityDimension?: string;
  checkCategory?: string;
  checkName?: string;
  checkType?: string;
  highestSeverity?: number;
};
const groupLevelOptions = Object.values(
  ConnectionIncidentGroupingSpecGroupingLevelEnum
).map((item) => ({
  label: item
    .replace(/_(?=dimension)/, ' / Quality ')
    .replace(/_(?=(name|type|category))/g, ' / Check ')
    .replace(/^\w/g, (c) => c.toUpperCase()),
  value: item
}));

const minimumSeverityOptions = Object.values(
  ConnectionIncidentGroupingSpecMinimumSeverityEnum
).map((item) => ({
  label: item.charAt(0).toUpperCase() + item.slice(1),
  value: item
}));

export const IncidentsNotificationsView = () => {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useDecodedParams();
  const dispatch = useActionDispatch();
  const {
    incidentGrouping,
    isUpdatedIncidentGroup,
    isUpdating,
    incidentFilters
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [patternNameEdit, setPatternNameEdit] = useState('');
  const [loading, setLoading] = useState(false);

  const [
    filteredNotificationsConfigurations,
    setFilteredNotificationsConfigurations
  ] = useState<Array<TNotificationPattern>>([]);
  const [addNotificationPattern, setAddNotificationPattern] = useState(false);
  const [patternProp, setPatternProp] = useState<
    FilteredNotificationModel | undefined
  >();

  useEffect(() => {
    if (!incidentFilters) return;
    if (incidentFilters.notificationName) {
      setPatternNameEdit(incidentFilters.notificationName);
    } else {
      setPatternProp({
        name: '',
        filter: {
          connection: incidentFilters.connection,
          schema: incidentFilters.schema,
          table: incidentFilters.table,
          qualityDimension: incidentFilters.qualityDimension,
          checkCategory: incidentFilters.checkCategory,
          checkName: incidentFilters.check,
          checkType: incidentFilters.checkType
        },
        priority: 1000
      });
      dispatch(
        addFirstLevelTab(checkTypes, {
          value: firstLevelActiveTab,
          state: {
            incidentFilters: undefined
          }
        })
      );
      setAddNotificationPattern(true);
    }
  }, [incidentFilters]);

  const getConnectionFilteredNotificationsConfigurations = async () => {
    setLoading(true);
    FilteredNotificationsConfigurationsClient.getConnectionFilteredNotificationsConfigurations(
      connection
    )
      .then((response) => {
        const patterns: TNotificationPattern[] = response.data.map((x) => {
          return {
            ...x,
            connection: x.filter?.connection || '',
            schema: x.filter?.schema || '',
            table: x.filter?.table || '',
            qualityDimension: x.filter?.qualityDimension || '',
            checkCategory: x.filter?.checkCategory || '',
            checkName: x.filter?.checkName || '',
            checkType: x.filter?.checkType || ''
          };
        });
        const sortedPatterns = sortPatterns(patterns, 'priority', 'asc');
        setFilteredNotificationsConfigurations(sortedPatterns);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    getConnectionFilteredNotificationsConfigurations();
  }, [connection]);

  const createNotificationPattern = () => {
    setAddNotificationPattern(true);
  };
  const onBack = () => {
    if (connection) {
      dispatch(setActiveFirstLevelTab(checkTypes, firstLevelActiveTab));
    }
    setPatternNameEdit('');
    setPatternProp(undefined);
    setAddNotificationPattern(false);
    getConnectionFilteredNotificationsConfigurations();
  };

  useEffect(() => {
    dispatch(
      getConnectionIncidentGrouping(checkTypes, firstLevelActiveTab, connection)
    );
  }, [connection, checkTypes, firstLevelActiveTab]);

  const onChange = (obj: Partial<ConnectionIncidentGroupingSpec>) => {
    dispatch(
      setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
        ...(incidentGrouping || {}),
        ...obj
      })
    );
  };

  const onChangeConnectionDefaultAdresses = (
    obj: Partial<IncidentNotificationSpec>
  ) => {
    dispatch(
      setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
        ...(incidentGrouping || {}),
        incident_notification: {
          ...(incidentGrouping?.incident_notification || {}),
          ...obj
        }
      })
    );
  };

  const onUpdate = () => {
    dispatch(
      updateConnectionIncidentGrouping(
        checkTypes,
        firstLevelActiveTab,
        connection,
        incidentGrouping
      )
    );
  };

  const defaultConnectionAdressess = incidentGrouping?.incident_notification;
  if (loading) {
    return (
      <div className="flex justify-center h-100">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  return (
    <div
      className={clsx(
        'px-8 py-2',
        userProfile.can_manage_scheduler !== true
          ? 'pointer-events-none cursor-not-allowed'
          : ''
      )}
    >
      {!addNotificationPattern && !patternNameEdit && (
        <ConnectionActionGroup
          onUpdate={onUpdate}
          isUpdated={isUpdatedIncidentGroup}
          isUpdating={isUpdating}
        />
      )}

      <div className="flex flex-col">
        {addNotificationPattern || patternNameEdit ? (
          <div>
            <div className="flex space-x-4 items-center absolute right-10 top-30">
              <Button
                label="Back"
                color="primary"
                variant="text"
                className="px-0"
                leftIcon={
                  <SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />
                }
                onClick={onBack}
              />
            </div>
            <CreateNotificationPattern
              connection={connection}
              onBack={onBack}
              patternNameEdit={patternNameEdit}
              defaultConnectionAdressess={defaultConnectionAdressess}
              onChangeConnectionDefaultAdresses={
                onChangeConnectionDefaultAdresses
              }
              onUpdateDefaultPattern={onUpdate}
              patternProp={patternProp}
            />
          </div>
        ) : (
          <>
            <div className="flex mb-4">
              <Select
                label="Data quality incident grouping level"
                options={groupLevelOptions}
                value={incidentGrouping?.grouping_level}
                onChange={(value) => onChange({ grouping_level: value })}
                className="text-sm"
                menuClassName="!top-14"
              />
            </div>
            <div className="flex mb-4">
              <Select
                label="Minimum severity level"
                options={minimumSeverityOptions}
                value={incidentGrouping?.minimum_severity}
                onChange={(value) => onChange({ minimum_severity: value })}
                className="text-sm"
                menuClassName="!top-14"
              />
            </div>
            <div className="flex gap-4 items-center mb-4 text-sm">
              <p>Create separate incidents for each data group</p>
              <div className="w-6 h-6">
                <Checkbox
                  checked={incidentGrouping?.divide_by_data_groups}
                  onChange={(value) =>
                    onChange({ divide_by_data_groups: value })
                  }
                />
              </div>
            </div>
            <div className="flex items-center mb-4 gap-2 text-sm">
              <p>Maximum incident duration</p>
              <div className="flex gap-2 items-center">
                <NumberInput
                  value={incidentGrouping?.max_incident_length_days}
                  onChange={(value) =>
                    onChange({ max_incident_length_days: value })
                  }
                />
                <span>
                  days. After this time, DQOps creates a new incident.
                </span>
              </div>
            </div>
            <div className="flex items-center mb-8 gap-2 text-sm">
              <p>Mute data quality issues for</p>
              <div className="flex gap-2 items-center">
                <NumberInput
                  value={incidentGrouping?.mute_for_days}
                  onChange={(value) => onChange({ mute_for_days: value })}
                />
                <span>
                  {' '}
                  days. If the incident is muted, DQOps will not create a new
                  one.
                </span>
              </div>
            </div>
            <NotificationPatternTable
              filteredNotificationsConfigurations={
                filteredNotificationsConfigurations
              }
              onChange={setFilteredNotificationsConfigurations}
              setPatternNameEdit={setPatternNameEdit}
              connection={connection}
            />
            <Button
              label="Add notification filter"
              onClick={createNotificationPattern}
              color="primary"
              className="!w-50 !my-5"
            />
          </>
        )}
      </div>
    </div>
  );
};
