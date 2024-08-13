import React, { useState } from 'react';
import {
  FilteredNotificationModel,
  IncidentNotificationTargetSpec,
  NotificationFilterSpec
} from '../../../../api';
import AddressesNotificationsWrapper from './AddressesNotificationsWrapper';
import DefaultPatternTarget from './DefaultPatternTarget';

export default function CreateNotificationPattern({
  connection
}: {
  connection: string;
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

  return (
    <div>
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
