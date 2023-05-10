import React, { useEffect } from "react";
import IncidentsLayout from "../../components/IncidentsLayout";
import SvgIcon from "../../components/SvgIcon";
import { useHistory, useParams } from "react-router-dom";
import Input from "../../components/Input";
import Button from "../../components/Button";
import StatusSelect from "./StatusSelect";
import { useSelector } from "react-redux";
import { getFirstLevelIncidentsState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getIncidentsByConnection, setIncidentsFilter } from "../../redux/actions/incidents.actions";
import { Table } from "../../components/Table";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { Pagination } from "../../components/Pagination";

const options = [
  {
    label: 'Current month',
    value: 1
  },
  {
    label: 'Last 2 months',
    value: 2
  },
  {
    label: 'Last 3 months',
    value: 3
  }
];

const columns = [
  {
    label: 'Resolution status',
    className: 'text-left py-2 px-4',
    value: 'status',
    render: (value: string) => {
      return (
        <div className="w-6 h-6 rounded-full flex items-center justify-center bg-primary">
          <SvgIcon name="check" className="text-white" />
          {value}
        </div>
      )
    }
  },
  {
    label: 'Failed checks count',
    className: 'text-left py-2 px-4',
    value: 'failedChecksCount'
  },
  {
    label: 'Schema',
    className: 'text-left py-2 px-4',
    value: 'schema'
  },
  {
    label: 'Table',
    className: 'text-left py-2 px-4',
    value: 'table'
  },
  {
    label: 'Checks',
    className: 'text-left py-2 px-4',
    value: 'checkName'
  },
  {
    label: 'First seen',
    className: 'text-left py-2 px-4',
    value: 'firstSeen'
  },
  {
    label: 'Last seen',
    className: 'text-left py-2 px-4',
    value: 'lastSeen'
  },
  {
    label: 'Issue Link',
    className: 'text-left py-2 px-4',
    value: 'issueUrl'
  }
];

export const IncidentDetail = () => {
  const { connection }: { connection: string } = useParams();
  const { incidents, filters = {} } = useSelector(getFirstLevelIncidentsState);
  const dispatch = useActionDispatch();
  const history = useHistory();

  useEffect(() => {
    dispatch(getIncidentsByConnection({
      connection,
      ...filters
    }));
  }, [connection, filters]);

  const onChangeFilter = (obj: any) => {
    dispatch(setIncidentsFilter({
      ...filters || {},
      ...obj
    }));
  };

  const goToConfigure = () => {
    history.push(ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'incidents'));
  };

  return (
    <IncidentsLayout>
      <div className="relative">
        <div className="flex items-center justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">Data quality incidents on {connection || ''}</div>
          </div>
          <StatusSelect />

          <Button
            onClick={goToConfigure}
            color="primary"
            label="Configure"
          />
        </div>

        <div className="flex items-center p-4 gap-6 mb-4">
          <div className="grow">
            <Input
              value={filters.optionalFilter || ""}
              onChange={(e) => onChangeFilter({ optionalFilter: e.target.value, page: 1 })}
              placeholder="Filter incidents"
              className="!h-12"
            />
          </div>

          <div className="flex gap-2">
            {options.map((o, index) => (
              <Button
                key={index}
                label={o.label}
                color={o.value === (filters?.numberOfMonth || 3) ? 'primary' : undefined}
                onClick={() => onChangeFilter({ numberOfMonth: o.value, page: 1 })}
              />
            ))}
          </div>
        </div>
        <div className="p-4">
          <Table
            columns={columns}
            data={incidents || []}
            className="w-full"
          />

          <Pagination
            page={filters.page || 1}
            pageSize={filters.pageSize || 5}
            totalPages={10}
            onChange={(page, pageSize) => onChangeFilter({
              page,
              pageSize
            })}
          />
        </div>
      </div>
    </IncidentsLayout>
  );
};

export default IncidentDetail;
