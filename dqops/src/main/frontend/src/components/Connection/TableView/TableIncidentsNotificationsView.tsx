import React, { useEffect } from "react";
import Select from "../../Select";
import { CheckTypes } from "../../../shared/routes";
import { useParams } from "react-router-dom";
import { useActionDispatch } from "../../../hooks/useActionDispatch";
import { useSelector } from "react-redux";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import {
  getTableIncidentGrouping,
  setUpdateIncidentGroup,
  updateTableIncidentGrouping
} from "../../../redux/actions/source.actions";
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum
} from "../../../api";
import Checkbox from "../../Checkbox";
import NumberInput from "../../NumberInput";
import TableActionGroup from "./TableActionGroup";
import clsx from "clsx";

const groupLevelOptions = Object.values(ConnectionIncidentGroupingSpecGroupingLevelEnum).map((item) => ({
  label: item.replace(/_/g, " ").replace(/\b\w/g, c => c.toUpperCase()),
  value: item
}));

const minimumSeverityOptions = Object.values(ConnectionIncidentGroupingSpecMinimumSeverityEnum).map((item) => ({
  label: item.charAt(0).toUpperCase() + item.slice(1),
  value: item
}));

export const TableIncidentsNotificationsView = ({canUserEdit} :  {canUserEdit ?: boolean}) => {
  const { connection, schema, table, checkTypes }: { connection: string, schema: string, table: string, checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const { incidentGrouping, isUpdatedIncidentGroup, isUpdating } = useSelector(getFirstLevelState(checkTypes));
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
      <div className={clsx("flex flex-col", canUserEdit ? "" : "cursor-not-allowed pointer-events-none")}>
        <div className="flex mb-4">
          <Select
            label="Data quality incident grouping level:"
            options={groupLevelOptions}
            value={incidentGrouping?.grouping_level}
            prefix="By"
            onChange={(value) => onChange({ grouping_level: value })}
            disabled={canUserEdit === false}
          />
        </div>
        <div className="flex mb-4">
          <Select
            label="Minimum severity level:"
            options={minimumSeverityOptions}
            value={incidentGrouping?.minimum_severity}
            onChange={(value) => onChange({ minimum_severity: value })}
            disabled={canUserEdit === false}
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
              disabled={canUserEdit === false}
            />
            <span>days. After this time, the DQO creates a new incident.</span>
          </div>
        </div>
        <div className="flex items-center mb-4 gap-2 text-sm">
          <p>Mute data quality issues for</p>
          <div className="flex gap-2 items-center">
            <NumberInput
              value={incidentGrouping?.mute_for_days}
              onChange={() => {}}
              disabled={canUserEdit === false}
            />
            <span> days. If the incident is muted, DQO will not create a new one.</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TableIncidentsNotificationsView;
