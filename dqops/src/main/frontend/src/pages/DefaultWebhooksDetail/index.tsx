import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { FilteredNotificationModel, IncidentNotificationSpec } from '../../api';
import Button from '../../components/Button';
import CreateNotificationPattern from '../../components/Connection/ConnectionView/NotificationPattern/CreateNotificationPattern';
import NotificationPatternTable from '../../components/Connection/ConnectionView/NotificationPattern/NotificationPatternTable';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { updateTabLabel } from '../../redux/actions/definition.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelSensorState } from '../../redux/selectors';
import {
  FilteredNotificationsConfigurationsClient,
  SettingsApi
} from '../../services/apiClient';
import { sortPatterns } from '../../utils';
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
export default function DefaultWebhooksDetail() {
  const { activeTab } = useSelector((state: IRootState) => state.definition);
  const { incidentFilters } = useSelector(getFirstLevelSensorState);
  const [defaultWebhooksConfiguration, setDefaultWebhooksConfiguration] =
    useState<IncidentNotificationSpec>();
  const [addNotificationPattern, setAddNotificationPattern] = useState(false);
  const [patternNameEdit, setPatternNameEdit] = useState('');
  const [filteredNotifications, setFilteredNotifications] = useState<
    Array<TNotificationPattern>
  >([]);
  const [patternProp, setPatternProp] = useState<
    FilteredNotificationModel | undefined
  >();
  const [isUpdated, setIsUpdated] = useState(false);
  const dispatch = useActionDispatch();
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
          checkType: incidentFilters.checkType,
          highestSeverity: incidentFilters.highestSeverity
        }
      });
      setAddNotificationPattern(true);
    }
  }, [incidentFilters]);
  const getDefaultWebhooksConfiguration = async () => {
    await SettingsApi.getDefaultWebhooks().then((res) =>
      setDefaultWebhooksConfiguration(res.data)
    );
  };

  const onChangeWebhooks = (obj: Partial<IncidentNotificationSpec>) => {
    setDefaultWebhooksConfiguration((prevState) => ({
      ...prevState,
      ...obj
    }));
    setIsUpdated(true);
  };

  const updateDefaultWebhooksConfiguration = async () => {
    await SettingsApi.updateDefaultWebhooks(defaultWebhooksConfiguration).then(
      () => setIsUpdated(false)
    );
  };

  const getFilteredNotifications = () => {
    FilteredNotificationsConfigurationsClient.getDefaultFilteredNotificationsConfigurations().then(
      (response) => {
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

        setFilteredNotifications(sortedPatterns);
      }
    );
  };

  useEffect(() => {
    getFilteredNotifications();
    getDefaultWebhooksConfiguration();
  }, []);

  const createNotificationPattern = () => {
    setAddNotificationPattern(true);
    dispatch(updateTabLabel('New default notification', activeTab ?? ''));
  };

  const onBack = () => {
    dispatch(updateTabLabel('Global incident notifications', activeTab ?? ''));

    setPatternNameEdit('');
    setAddNotificationPattern(false);
    getFilteredNotifications();
  };

  const onCHangePatternNameToEdit = (patternName: string) => {
    setPatternNameEdit(patternName);
    dispatch(updateTabLabel(patternName, activeTab ?? ''));
  };

  return (
    <>
      {addNotificationPattern || patternNameEdit ? (
        <div className="relative p-4">
          <div className="flex space-x-4 items-center absolute right-42">
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
            onBack={onBack}
            patternNameEdit={patternNameEdit}
            defaultConnectionAdressess={defaultWebhooksConfiguration}
            onChangeConnectionDefaultAdresses={onChangeWebhooks}
            onUpdateDefaultPattern={updateDefaultWebhooksConfiguration}
            patternProp={patternProp}
          />
        </div>
      ) : (
        <div className="">
          <div className="flex justify-between px-4 border-b border-gray-300 h-14 items-center flex-shrink-0">
            <div className="flex items-center justify-between w-full">
              <div className="text-lg font-semibold truncate">
                Global incident notification configuration
              </div>
            </div>
          </div>
          <div className="flex flex-col px-4 py-2">
            <NotificationPatternTable
              filteredNotificationsConfigurations={filteredNotifications}
              onChange={setFilteredNotifications}
              setPatternNameEdit={onCHangePatternNameToEdit}
            />
            <Button
              label="Add notification filter"
              onClick={createNotificationPattern}
              color="primary"
              className="!w-50 !my-5"
            />
          </div>
        </div>
      )}
    </>
  );
}
