import React, { useEffect, useState } from 'react';
import { FilteredNotificationModel } from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
import NotificationPatternTable from './NotificationPatternTable';

export default function NotificationPattern({
  connection
}: {
  connection: string;
}) {
  const [
    filteredNotificationsConfigurations,
    setFilteredNotificationsConfigurations
  ] = useState<Array<FilteredNotificationModel>>([]);

  useEffect(() => {
    FilteredNotificationsConfigurationsClient.getConnectionFilteredNotificationsConfigurations(
      connection
    ).then((response) => {
      setFilteredNotificationsConfigurations(response.data);
    });
  }, [connection]);

  return (
    <div>
      <div>NotificationPattern</div>
      <NotificationPatternTable
        filteredNotificationsConfigurations={
          filteredNotificationsConfigurations
        }
      />
    </div>
  );
}
