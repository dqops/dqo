import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum,
  IncidentWebhookNotificationsSpec
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionIncidentGrouping,
  setUpdateIncidentGroup,
  updateConnectionIncidentGrouping
} from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { SettingsApi } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Checkbox from '../../Checkbox';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Input from '../../Input';
import NumberInput from '../../NumberInput';
import Select from '../../Select';
import ConnectionActionGroup from './ConnectionActionGroup';

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
  const { incidentGrouping, isUpdatedIncidentGroup, isUpdating } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [defaultWebhooksConfiguration, setDefaultWebhooksConfiguration] =
    useState<IncidentWebhookNotificationsSpec>();

  const getDefaultWebhooksConfiguration = async () => {
    await SettingsApi.getDefaultWebhooks().then((res) =>
      setDefaultWebhooksConfiguration(res.data)
    );
  };

  useEffect(() => {
    dispatch(
      getConnectionIncidentGrouping(checkTypes, firstLevelActiveTab, connection)
    );
  }, [connection, checkTypes, firstLevelActiveTab]);

  useEffect(() => {
    getDefaultWebhooksConfiguration();
  }, []);

  const onChange = (obj: Partial<ConnectionIncidentGroupingSpec>) => {
    dispatch(
      setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
        ...(incidentGrouping || {}),
        ...obj
      })
    );
  };

  const onChangeWebhooks = (obj: Partial<IncidentWebhookNotificationsSpec>) => {
    dispatch(
      setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
        ...(incidentGrouping || {}),
        webhooks: {
          ...(incidentGrouping?.webhooks || {}),
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

  return (
    <div
      className={clsx(
        'px-8 py-6',
        userProfile.can_manage_scheduler !== true
          ? 'pointer-events-none cursor-not-allowed'
          : ''
      )}
    >
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedIncidentGroup}
        isUpdating={isUpdating}
      />
      <div className="flex flex-col">
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
              onChange={(value) => onChange({ divide_by_data_groups: value })}
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
            <span>days. After this time, DQOps creates a new incident.</span>
          </div>
        </div>
        <div className="flex items-center mb-4 gap-2 text-sm">
          <p>Mute data quality issues for</p>
          <div className="flex gap-2 items-center">
            <NumberInput
              value={incidentGrouping?.mute_for_days}
              onChange={() => {}}
            />
            <span>
              {' '}
              days. If the incident is muted, DQOps will not create a new one.
            </span>
          </div>
        </div>

        <SectionWrapper
          title="Webhooks for notifications of an incident state change"
          className="mt-8"
        >
          <Input
            className="mb-4"
            label="A new incident was opened (detected)"
            value={incidentGrouping?.webhooks?.incident_opened_webhook_url}
            onChange={(e) =>
              onChangeWebhooks({ incident_opened_webhook_url: e.target.value })
            }
            placeholder={
              defaultWebhooksConfiguration?.incident_opened_webhook_url
            }
          />
          <Input
            className="mb-4"
            label="An incident was acknowledged"
            value={
              incidentGrouping?.webhooks?.incident_acknowledged_webhook_url
            }
            onChange={(e) =>
              onChangeWebhooks({
                incident_acknowledged_webhook_url: e.target.value
              })
            }
            placeholder={
              defaultWebhooksConfiguration?.incident_acknowledged_webhook_url
            }
          />
          <Input
            className="mb-4"
            label="An incident was resolved"
            value={incidentGrouping?.webhooks?.incident_resolved_webhook_url}
            onChange={(e) =>
              onChangeWebhooks({
                incident_resolved_webhook_url: e.target.value
              })
            }
            placeholder={
              defaultWebhooksConfiguration?.incident_resolved_webhook_url
            }
          />
          <Input
            className="mb-4"
            label="An incident was muted"
            value={incidentGrouping?.webhooks?.incident_muted_webhook_url}
            onChange={(e) =>
              onChangeWebhooks({ incident_muted_webhook_url: e.target.value })
            }
            placeholder={
              defaultWebhooksConfiguration?.incident_muted_webhook_url
            }
          />
        </SectionWrapper>
      </div>
    </div>
  );
};
