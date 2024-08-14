import React, { useEffect, useState } from 'react';
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
  onBack,
  patternNameEdit
}: {
  connection: string;
  onBack: () => void;
  patternNameEdit?: string;
}) {
  const [pattern, setPattern] = useState<FilteredNotificationModel>({});

  useEffect(() => {
    if (patternNameEdit) {
      FilteredNotificationsConfigurationsClient.getConnectionFilteredNotificationConfiguration(
        connection,
        patternNameEdit
      ).then((res) => setPattern(res.data));
    }
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
    console.log(pattern);
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
        create={!patternNameEdit}
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
