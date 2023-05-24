import React, { useEffect } from "react";
import Select from "../../Select";
import { CheckTypes } from "../../../shared/routes";
import { useParams } from "react-router-dom";
import { useActionDispatch } from "../../../hooks/useActionDispatch";
import { useSelector } from "react-redux";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import {
  getConnectionIncidentGrouping,
  setUpdateIncidentGroup,
  updateConnectionIncidentGrouping
} from "../../../redux/actions/source.actions";
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum, IncidentWebhookNotificationsSpec
} from "../../../api";
import Checkbox from "../../Checkbox";
import NumberInput from "../../NumberInput";
import SectionWrapper from "../../Dashboard/SectionWrapper";
import Input from "../../Input";
import ConnectionActionGroup from "./ConnectionActionGroup";

const groupLevelOptions = Object.values(ConnectionIncidentGroupingSpecGroupingLevelEnum).map((item) => ({
  label: item.replace(/_/g, " ").replace(/\b\w/g, c => c.toUpperCase()),
  value: item
}));

const minimumSeverityOptions = Object.values(ConnectionIncidentGroupingSpecMinimumSeverityEnum).map((item) => ({
  label: item.charAt(0).toUpperCase() + item.slice(1),
  value: item
}));

export const IncidentsNotificationsView = () => {
  const { connection, checkTypes }: { connection: string, checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const { incidentGrouping, isUpdatedIncidentGroup, isUpdating } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getConnectionIncidentGrouping(checkTypes, firstLevelActiveTab, connection));
  }, [connection, checkTypes, firstLevelActiveTab]);

  const onChange = (obj: Partial<ConnectionIncidentGroupingSpec>) => {
    dispatch(setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
      ...incidentGrouping || {},
      ...obj,
    }));
  };

  const onChangeWebhooks = (obj: Partial<IncidentWebhookNotificationsSpec>) => {
    dispatch(setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
      ...incidentGrouping || {},
      webhooks: {
        ...incidentGrouping?.webhooks || {},
        ...obj
      }
    }));
  };

  const onUpdate = () => {
    dispatch(updateConnectionIncidentGrouping(checkTypes, firstLevelActiveTab, connection, incidentGrouping));
  };

  return (
    <div className="px-8 py-6">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedIncidentGroup}
        isUpdating={isUpdating}
      />
      <div className="flex flex-col">
        <div className="flex mb-4">
          <Select
            label="Data quality incident grouping level:"
            options={groupLevelOptions}
            value={incidentGrouping?.grouping_level}
            prefix="By"
            onChange={(value) => onChange({ grouping_level: value })}
          />
        </div>
        <div className="flex mb-4">
          <Select
            label="Minimum severity level:"
            options={minimumSeverityOptions}
            value={incidentGrouping?.minimum_severity}
            onChange={(value) => onChange({ minimum_severity: value })}
          />
        </div>
        <div className="w-1/2 flex justify-between items-center mb-4">
          <p>Create separate incidents for each data stream:</p>
          <div className="w-6 h-6">
            <Checkbox
              checked={incidentGrouping?.divide_by_data_stream}
              onChange={(value) => onChange({ divide_by_data_stream: value })}
            />
          </div>
        </div>
        <div className="w-1/2 flex justify-between items-center mb-4">
          <p>Create another incident when an incident is open or acknowledged for:</p>
          <div className="flex gap-2 items-center">
            <NumberInput
              value={incidentGrouping?.max_incident_length_days}
              onChange={(value) => onChange({ max_incident_length_days: value })}
            />
            <span>days</span>
          </div>
        </div>
        <div className="w-1/2 flex justify-between items-center mb-4">
          <p>Do not create a new incident similar to a muted incident for:</p>
          <div className="flex gap-2 items-center">
            <NumberInput
              value={incidentGrouping?.mute_for_days}
              onChange={() => {}}
            />
            <span>days</span>
          </div>
        </div>

        <SectionWrapper
          title="Webhooks for notifications of an incident state change"
          className="mt-8"
        >
          <Input
            className="mb-4"
            label="A new incident was opened(detected):"
            value={incidentGrouping?.webhooks?.incident_opened_webhook_url}
            onChange={(e) => onChangeWebhooks({ incident_opened_webhook_url: e.target.value })}
          />
          <Input
            className="mb-4"
            label="An incident was acknowledged:"
            value={incidentGrouping?.webhooks?.incident_acknowledged_webhook_url}
            onChange={(e) => onChangeWebhooks({ incident_acknowledged_webhook_url: e.target.value })}
          />
          <Input
            className="mb-4"
            label="An incident was resolved:"
            value={incidentGrouping?.webhooks?.incident_resolved_webhook_url}
            onChange={(e) => onChangeWebhooks({ incident_resolved_webhook_url: e.target.value })}
          />
          <Input
            className="mb-4"
            label="An incident was muted:"
            value={incidentGrouping?.webhooks?.incident_muted_webhook_url}
            onChange={(e) => onChangeWebhooks({ incident_muted_webhook_url: e.target.value })}
          />
        </SectionWrapper>
      </div>
    </div>
  );
};
