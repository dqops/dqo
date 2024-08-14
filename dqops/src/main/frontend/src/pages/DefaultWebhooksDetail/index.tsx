import React, { useEffect, useState } from 'react';
import { FilteredNotificationModel, IncidentNotificationSpec } from '../../api';
import Button from '../../components/Button';
import CreateNotificationPattern from '../../components/Connection/ConnectionView/NotificationPattern/CreateNotificationPattern';
import NotificationPatternTable from '../../components/Connection/ConnectionView/NotificationPattern/NotificationPatternTable';
import SvgIcon from '../../components/SvgIcon';
import {
  FilteredNotificationsConfigurationsClient,
  SettingsApi
} from '../../services/apiClient';
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
  const [defaultWebhooksConfiguration, setDefaultWebhooksConfiguration] =
    useState<IncidentNotificationSpec>();
  const [addNotificationPattern, setAddNotificationPattern] = useState(false);
  const [patternNameEdit, setPatternNameEdit] = useState('');
  const [filteredNotifications, setFilteredNotifications] = useState<
    Array<TNotificationPattern>
  >([]);
  const [isUpdated, setIsUpdated] = useState(false);

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
            checkType: x.filter?.checkType || '',
            highestSeverity: x.filter?.highestSeverity
          };
        });
        setFilteredNotifications(patterns);
      }
    );
  };

  useEffect(() => {
    getFilteredNotifications();
    getDefaultWebhooksConfiguration();
  }, []);

  const createNotificationPattern = () => {
    setAddNotificationPattern(true);
  };

  const onBack = () => {
    setPatternNameEdit('');
    setAddNotificationPattern(false);
    getFilteredNotifications();
  };

  return (
    <>
      {addNotificationPattern || patternNameEdit ? (
        <div className="relative p-4">
          <div>
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
          />
        </div>
      ) : (
        <div className="py-2">
          <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0">
            <div className="flex items-center justify-between w-full">
              <div className="text-lg font-semibold truncate">
                Default incident notification configuration
              </div>
            </div>
          </div>
          <div className="flex flex-col px-4">
            <NotificationPatternTable
              filteredNotificationsConfigurations={filteredNotifications}
              onChange={setFilteredNotifications}
              setPatternNameEdit={setPatternNameEdit}
            />
            <Button
              label="Add notification pattern"
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
