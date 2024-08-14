import React, { useEffect, useState } from 'react';
import {
  FilteredNotificationModel,
  IncidentNotificationSpec,
  IncidentNotificationTargetSpec,
  NotificationFilterSpec
} from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
import Button from '../../../Button';
import AddressesNotificationsWrapper from './AddressesNotificationsWrapper';
import DefaultPatternTarget from './DefaultPatternTarget';

export default function CreateNotificationPattern({
  connection,
  onBack,
  patternNameEdit,
  defaultConnectionAdressess,
  onChangeConnectionDefaultAdresses,
  onUpdateDefaultPattern
}: {
  connection: string;
  onBack: () => void;
  patternNameEdit?: string;
  defaultConnectionAdressess: any;
  onChangeConnectionDefaultAdresses: (
    obj: Partial<IncidentNotificationSpec>
  ) => void;
  onUpdateDefaultPattern: () => void;
}) {
  const isDefaultPattern = patternNameEdit === 'default';

  const [pattern, setPattern] = useState<FilteredNotificationModel>({});
  const [isNewDefaultPattern, setIsNewDefaultPattern] = useState(false);

  useEffect(() => {
    if (!patternNameEdit) return;

    // if (isDefaultPattern) {
    //   FilteredNotificationsConfigurationsClient.getDefaultFilteredNotificationConfiguration(
    //     connection,
    //     {
    //       validateStatus: (status: number) =>
    //         validate404Status(status, () => setIsNewDefaultPattern(true))
    //     }
    //   )
    //     .then((res) => {
    //       if (!res.data) return;
    //       setPattern(res.data);
    //     })
    //     .catch(() => setPattern({}));
    // } else {
    if (isDefaultPattern) return;
    FilteredNotificationsConfigurationsClient.getConnectionFilteredNotificationConfiguration(
      connection,
      patternNameEdit
    ).then((res) => setPattern(res.data));
    // }
  }, [patternNameEdit]);

  const onChangePattern = (val: Partial<FilteredNotificationModel>) => {
    setPattern({
      ...pattern,
      ...val
    });
  };

  const onChangePatternFilter = (val: Partial<NotificationFilterSpec>) => {
    setPattern({
      ...pattern,
      filter: {
        ...pattern.filter,
        ...val
      }
    });
  };

  const onChangePatternTarget = (
    val: Partial<IncidentNotificationTargetSpec>
  ) => {
    setPattern({
      ...pattern,
      target: {
        ...pattern.target,
        ...val
      }
    });
  };

  const savePattern = () => {
    if (!patternNameEdit) {
      FilteredNotificationsConfigurationsClient.createConnectionFilteredNotificationConfiguration(
        connection,
        pattern
      ).then(() => onBack());
    } else {
      FilteredNotificationsConfigurationsClient.updateConnectionFilteredNotificationConfiguration(
        connection,
        patternNameEdit,
        pattern
      ).then(() => onBack());
    }
  };

  // handle saving default pattern for connection
  const saveDefaultPattern = () => {
    onUpdateDefaultPattern();
    onBack();
    // if (isNewDefaultPattern) {
    //   FilteredNotificationsConfigurationsClient.createDefaultFilteredNotificationConfiguration(
    //     pattern
    //   ).then(() => onBack());
    //   return;
    // } else {
    //   FilteredNotificationsConfigurationsClient.updateDefaultFilteredNotificationConfiguration(
    //     pattern
    //   ).then(() => onBack());
    // }
  };

  const handleSave = () => {
    isDefaultPattern ? saveDefaultPattern() : savePattern();
  };

  return (
    <div>
      <div className="flex space-x-4 items-center absolute right-2 top-2">
        <Button
          label="Save"
          color="primary"
          className="!w-30 !mr-5"
          onClick={handleSave}
        />
      </div>
      {!isDefaultPattern && (
        <DefaultPatternTarget
          pattern={pattern}
          create={!patternNameEdit}
          onChange={onChangePattern}
          onChangePatternFilter={onChangePatternFilter}
        />
      )}
      <AddressesNotificationsWrapper
        target={isDefaultPattern ? defaultConnectionAdressess : pattern.target}
        onChangePatternTarget={
          isDefaultPattern
            ? onChangeConnectionDefaultAdresses
            : onChangePatternTarget
        }
      />
    </div>
  );
}
