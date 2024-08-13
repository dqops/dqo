import React, { useState } from 'react';
import {
  FilteredNotificationModel,
  IncidentNotificationTargetSpec,
  NotificationFilterSpec
} from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
import Button from '../../../Button';
import AddressesNotificationsWrapper from './AddressesNotificationsWrapper';
import DefaultPatternTarget from './DefaultPatternTarget';

export default function CreateNotificationPattern({
  connection,
  onBack
}: {
  connection: string;
  onBack: () => void;
}) {
  const [pattern, setPattern] = useState<FilteredNotificationModel>({});
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
    FilteredNotificationsConfigurationsClient.createConnectionFilteredNotificationConfiguration(
      connection,
      pattern
    ).then(() => onBack());
  };

  return (
    <div>
      <div className="flex space-x-4 items-center absolute right-2 top-2">
        <Button
          label="Save"
          color="primary"
          className="!w-30 !mr-5"
          onClick={savePattern}
        />
      </div>
      <DefaultPatternTarget
        pattern={pattern}
        create={true}
        onChange={onChangePattern}
        onChangePatternFilter={onChangePatternFilter}
      />
      <AddressesNotificationsWrapper
        pattern={pattern}
        onChangePatternTarget={onChangePatternTarget}
      />
    </div>
  );
}
