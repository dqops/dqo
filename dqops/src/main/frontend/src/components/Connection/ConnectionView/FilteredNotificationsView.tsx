import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  FilteredNotificationModel,
  IncidentNotificationSpec
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  getConnectionIncidentGrouping,
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
import Loader from '../../Loader';
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

export const FilteredNotificationsView = () => {
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
      dispatch(
        addFirstLevelTab(checkTypes, {
          value: firstLevelActiveTab,
          state: {
            incidentFilters: undefined
          }
        })
      );
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
            <div className="flex mb-4"></div>
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
