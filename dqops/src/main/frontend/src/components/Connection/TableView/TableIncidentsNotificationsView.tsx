import clsx from "clsx";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum
} from "../../../api";
import { useActionDispatch } from "../../../hooks/useActionDispatch";
import {
  getTableIncidentGrouping,
  setUpdateIncidentGroup,
  updateTableIncidentGrouping
} from "../../../redux/actions/source.actions";
import { IRootState } from "../../../redux/reducers";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";
import Checkbox from "../../Checkbox";
import NumberInput from "../../NumberInput";
import Select from "../../Select";
import TableActionGroup from "./TableActionGroup";
import { useDecodedParams } from "../../../utils";


const minimumSeverityOptions = Object.values(ConnectionIncidentGroupingSpecMinimumSeverityEnum).map((item) => ({
  label: item.charAt(0).toUpperCase() + item.slice(1),
  value: item
}));

export const TableIncidentsNotificationsView = () => {

  const { connection, schema, table, checkTypes }: { connection: string, schema: string, table: string, checkTypes: CheckTypes } = useDecodedParams();
  const dispatch = useActionDispatch();
  const { incidentGrouping, isUpdatedIncidentGroup, isUpdating } = useSelector(getFirstLevelState(checkTypes));
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getTableIncidentGrouping(checkTypes, firstLevelActiveTab, connection, schema, table));
  }, [connection, schema, table, checkTypes, firstLevelActiveTab]);

  const onChange = (obj: Partial<ConnectionIncidentGroupingSpec>) => {
    dispatch(setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
      ...incidentGrouping || {},
      ...obj,
    }));
  };
  const groupLevelOptions = Object.values(ConnectionIncidentGroupingSpecGroupingLevelEnum).map((item) => ({
    label: item
    .replace(/_(?=dimension)/, " / Quality ")
    .replace(/_(?=(name|type|category))/g, " / Check ")
    .replace(/^\w/g, c => c.toUpperCase()),
    value: item
  }));  

  const onUpdate = () => {
    dispatch(updateTableIncidentGrouping(checkTypes, firstLevelActiveTab, connection, schema, table, incidentGrouping));
  };

  return (
    <div className="px-8 py-6">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedIncidentGroup}
        isUpdating={isUpdating}
      />
      <div className={clsx("flex flex-col", userProfile.can_manage_data_sources ? "" : "cursor-not-allowed pointer-events-none")}>
        <div className="flex mb-4">
          <Select
            label="Data quality incident grouping level:"
            options={groupLevelOptions}
            value={incidentGrouping?.grouping_level}
            prefix="By"
            onChange={(value) => onChange({ grouping_level: value })}
            disabled={userProfile.can_manage_data_sources !== true}
            className="min-w-110 text-sm"
          />
        </div>
        <div className="flex mb-4">
          <Select
            label="Minimum severity level:"
            options={minimumSeverityOptions}
            value={incidentGrouping?.minimum_severity}
            onChange={(value) => onChange({ minimum_severity: value })}
            disabled={userProfile.can_manage_data_sources !== true}
            className="min-w-110 text-sm"
          />
        </div>
        <div className="flex gap-4 items-center mb-4 text-sm">
          <p>Create separate incidents for each data group:</p>
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
              onChange={(value) => onChange({ max_incident_length_days: value })}
              disabled={userProfile.can_manage_data_sources !== true}
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
              disabled={userProfile.can_manage_data_sources !== true}
            />
            <span> days. If the incident is muted, DQOps will not create a new one.</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TableIncidentsNotificationsView;
