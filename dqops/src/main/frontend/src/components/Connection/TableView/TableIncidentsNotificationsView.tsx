import clsx from 'clsx';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  ConnectionIncidentGroupingSpec,
  ConnectionIncidentGroupingSpecGroupingLevelEnum,
  ConnectionIncidentGroupingSpecMinimumSeverityEnum
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableIncidentGrouping,
  setUpdateIncidentGroup,
  updateTableIncidentGrouping
} from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Checkbox from '../../Checkbox';
import Select from '../../Select';
import TableActionGroup from './TableActionGroup';

const minimumSeverityOptions = [
  ...Object.values(ConnectionIncidentGroupingSpecMinimumSeverityEnum).map(
    (item) => ({
      label: item.charAt(0).toUpperCase() + item.slice(1),
      value: item
    })
  ),
  {
    label: 'Apply the severity filter at the connection level',
    value: undefined
  }
];

export const TableIncidentsNotificationsView = () => {
  const {
    connection,
    schema,
    table,
    checkTypes
  }: {
    connection: string;
    schema: string;
    table: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const dispatch = useActionDispatch();
  const { incidentGrouping, isUpdatedIncidentGroup, isUpdating } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getTableIncidentGrouping(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table
      )
    );
  }, [connection, schema, table, checkTypes, firstLevelActiveTab]);

  const onChange = (obj: Partial<ConnectionIncidentGroupingSpec>) => {
    dispatch(
      setUpdateIncidentGroup(checkTypes, firstLevelActiveTab, {
        ...(incidentGrouping || {}),
        ...obj
      })
    );
  };
  const groupLevelOptions = [
    ...Object.values(ConnectionIncidentGroupingSpecGroupingLevelEnum).map(
      (item) => ({
        label: item
          .replace(/_(?=dimension)/, ' / Quality ')
          .replace(/_(?=(name|type|category))/g, ' / Check ')
          .replace(/^\w/g, (c) => c.toUpperCase()),
        value: item
      })
    ),
    {
      label: 'Apply incident grouping configured at the connection level',
      value: undefined
    }
  ];

  const onUpdate = () => {
    dispatch(
      updateTableIncidentGrouping(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        incidentGrouping
      )
    );
  };

  return (
    <div className="px-8 py-6">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedIncidentGroup}
        isUpdating={isUpdating}
      />
      <div
        className={clsx(
          'flex flex-col',
          userProfile.can_manage_data_sources
            ? ''
            : 'cursor-not-allowed pointer-events-none'
        )}
      >
        <div className="flex mb-4">
          <Select
            label="Data quality incident grouping level:"
            options={groupLevelOptions}
            value={incidentGrouping?.grouping_level}
            onChange={(value) => onChange({ grouping_level: value })}
            disabled={userProfile.can_manage_data_sources !== true}
            className="min-w-110 text-sm"
            menuClassName="top-[55px]"
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
            menuClassName="top-[55px]"
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
      </div>
    </div>
  );
};

export default TableIncidentsNotificationsView;
