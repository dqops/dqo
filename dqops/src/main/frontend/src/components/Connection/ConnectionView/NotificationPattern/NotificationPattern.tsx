import React, { useEffect, useState } from 'react';
import { FilteredNotificationModel } from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
type TNotificationPattern = FilteredNotificationModel & {
  connection?: string;
  schema?: string;
  table?: string;
  column?: string;
};
export default function NotificationPattern({
  connection
}: {
  connection: string;
}) {
  const [
    filteredNotificationsConfigurations,
    setFilteredNotificationsConfigurations
  ] = useState<Array<TNotificationPattern>>([]);
  const [addNotificationPattern, setAddNotificationPattern] = useState(false);

  useEffect(() => {
    FilteredNotificationsConfigurationsClient.getConnectionFilteredNotificationsConfigurations(
      connection
    ).then((response) => {
      const patterns: TNotificationPattern[] = response.data.map((x) => {
        return {
          ...x,
          connection: x.filter?.connection || '',
          schema: x.filter?.schema || '',
          table: x.filter?.table || ''
        };
      });
      setFilteredNotificationsConfigurations(patterns);
    });
  }, [connection]);

  const createNotificationPattern = () => {
    setAddNotificationPattern(true);
  };
  const onBack = () => {
    setAddNotificationPattern(false);
  };
  return (
    <div>
      {/* {addNotificationPattern ? (
        <div>
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
          <CreateNotificationPattern connection={connection} />
        </div>
      ) : (
        <>
          <NotificationPatternTable
            filteredNotificationsConfigurations={
              filteredNotificationsConfigurations
            }
            onChange={setFilteredNotificationsConfigurations}
          />
          <Button
            label="Add notification filter"
            onClick={createNotificationPattern}
            color="primary"
          />
        </>
      )} */}
    </div>
  );
}
