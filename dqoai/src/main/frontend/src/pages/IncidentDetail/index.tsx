import React, { useEffect, useState } from "react";
import IncidentsLayout from "../../components/IncidentsLayout";
import SvgIcon from "../../components/SvgIcon";
import { useHistory, useParams } from "react-router-dom";
import Input from "../../components/Input";
import Button from "../../components/Button";
import StatusSelect from "./StatusSelect";
import { useSelector } from "react-redux";
import { getFirstLevelIncidentsState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { getIncidentsByConnection, setIncidentsFilter, updateIncident } from "../../redux/actions/incidents.actions";
import { Table } from "../../components/Table";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { Pagination } from "../../components/Pagination";
import moment from "moment";
import useDebounce from "../../hooks/useDebounce";
import { IncidentFilter } from "../../redux/reducers/incidents.reducer";
import { IncidentModel, IncidentModelStatusEnum } from "../../api";
import Select from "../../components/Select";
import { IncidentsApi } from "../../services/apiClient";
import { IconButton, Tooltip } from "@material-tailwind/react";
import AddIssueUrlDialog from "./AddIssueUrlDialog";

const getDaysString = (value: string) => {
  const daysDiff = moment().diff(moment(value), 'day');
  if (daysDiff === 0) return 'Today';
  if (daysDiff === 1) return '1 day ago';

  return `${daysDiff} days ago`;
}

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

const statusOptions = [
  {
    label: 'OPEN',
    value: IncidentModelStatusEnum.open,
    icon: <SvgIcon name="info-filled" className="text-red-900 w-6 h-6" />,
  },
  {
    label: 'ACKNOWLEDGED',
    value: IncidentModelStatusEnum.acknowledged,
    icon: <div className="w-5 h-5 rounded-full bg-black" />,
  },
  {
    label: 'RESOLVED',
    value: IncidentModelStatusEnum.resolved,
    icon: <SvgIcon name="check-circle" className="text-primary w-6 h-6" />,
  },
  {
    label: 'MUTED',
    value: IncidentModelStatusEnum.muted,
    icon: <SvgIcon name="stop" className="w-6 h-6" />,
  }
];

export const IncidentDetail = () => {
  const { connection }: { connection: string } = useParams();
  const { incidents, filters = {} } = useSelector(getFirstLevelIncidentsState);
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [searchTerm, setSearchTerm] = useState("");
  const debouncedSearchTerm = useDebounce(searchTerm, 300);
  const [open, setOpen] = useState(false);
  const [selectedIncident, setSelectedIncident] = useState<IncidentModel>();

  const onChangeIncidentStatus = async (row: IncidentModel, status: IncidentModelStatusEnum) => {
    dispatch(updateIncident(incidents.map((item: IncidentModel) => item.incidentId === row.incidentId ? ({
      ...row,
      status
    }) : item)));
    await IncidentsApi.setIncidentStatus(row.connection || "", row.year || 0, row.month || 0, row.incidentId || "", status);
  };

  const handleAddIssueUrl = async (issueUrl: string) => {
    dispatch(updateIncident(incidents.map((item: IncidentModel) => item.incidentId === selectedIncident?.incidentId ? ({
      ...selectedIncident,
      issueUrl
    }) : item)));
    await IncidentsApi.setIncidentIssueUrl(selectedIncident?.connection || "", selectedIncident?.year || 0, selectedIncident?.month || 0, selectedIncident?.incidentId || "", issueUrl);
  };

  const addIssueUrl = (row: IncidentModel) => {
    setSelectedIncident(row);
    setOpen(true);
  };

  const columns = [
    {
      label: 'Resolution status',
      className: 'text-left py-2 px-4',
      value: 'status',
      render: (value: string, row: IncidentModel) => {
        return (
          <div className="flex items-center">
            <Select
              value={value}
              options={statusOptions}
              onChange={(status) => onChangeIncidentStatus(row, status)}
            />
          </div>
        )
      }
    },
    {
      label: 'Failed checks count',
      className: 'text-left text-sm py-2 px-4',
      value: 'failedChecksCount'
    },
    {
      label: 'Schema',
      className: 'text-left text-sm py-2 px-4',
      value: 'schema'
    },
    {
      label: 'Table',
      className: 'text-left text-sm py-2 px-4',
      value: 'table'
    },
    {
      label: 'Checks',
      className: 'text-left py-2 px-4',
      value: 'checkName',
      render: (value: string, row: IncidentModel) => {
        const values = [row.qualityDimension, row.checkCategory, row.checkType, row.checkName].filter((item) => !!item);

        return (
          <div className="text-sm">{values.join(", ")}</div>
        )
      }
    },
    {
      label: 'First seen',
      className: 'text-left py-2 px-4',
      value: 'firstSeen',
      render: (value: string) => (
        <div className="text-sm">
          <div>{moment(value).format("YYYY-MM-DD")}</div>
          {getDaysString(value)}
        </div>
      )
    },
    {
      label: 'Last seen',
      className: 'text-left py-2 px-4',
      value: 'lastSeen',
      render: (value: string) => (
        <div className="text-sm">
          <div>{moment(value).format("YYYY-MM-DD")}</div>
          {getDaysString(value)}
        </div>
      )
    },
    {
      label: 'Issue Link',
      className: 'text-left issueUrl py-2 px-4',
      value: 'issueUrl',
      render: (value: string, row: IncidentModel) => {
        return (
          <div>
            {value ? (
              <div className="flex items-center space-x-2">
                <a
                  href={value}
                  target="_blank"
                  rel="noreferrer"
                  className="text-blue-600 underline"
                >
                  <Tooltip
                    content={value}
                    className="max-w-80 py-4 px-4 bg-gray-800 delay-300"
                    placement="top-start"
                  >
                    {value.length > 15 ? '...' + value.substring(value.length - 15) : value}
                  </Tooltip>
                </a>
                <IconButton
                  color="teal"
                  size="sm"
                  onClick={() => addIssueUrl(row)}
                  className="!shadow-none"
                >
                  <SvgIcon name="edit" className="w-4" />
                </IconButton>
              </div>
            ) : (
              <IconButton
                color="teal"
                size="sm"
                onClick={() => addIssueUrl(row)}
                className="!shadow-none"
              >
                <SvgIcon name="add" className="w-4" />
              </IconButton>
            )}
          </div>
        )
      }
    }
  ];


  useEffect(() => {
    dispatch(getIncidentsByConnection({
      connection,
    }));
  }, [connection]);

  useEffect(() => {
    onChangeFilter({
      optionalFilter: debouncedSearchTerm,
      page: 1
    })
  }, [debouncedSearchTerm]);

  const onChangeFilter = (obj: Partial<IncidentFilter>) => {
    dispatch(setIncidentsFilter({
      ...filters || {},
      ...obj
    }));
    dispatch(getIncidentsByConnection({
      ...filters || {},
      ...obj,
      connection,
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
          <div className="flex items-center">
            <div className="mr-20">
              <StatusSelect onChangeFilter={onChangeFilter} />
            </div>
            <Button
              onClick={goToConfigure}
              color="primary"
              label="Configure"
            />
          </div>
        </div>

        <div className="flex items-center p-4 gap-6 mb-4">
          <div className="grow">
            <Input
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
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
            pageSize={filters.pageSize || 50}
            totalPages={10}
            onChange={(page, pageSize) => onChangeFilter({
              page,
              pageSize
            })}
          />
        </div>
      </div>

      <AddIssueUrlDialog
        open={open}
        onClose={() => setOpen(false)}
        onSubmit={handleAddIssueUrl}
        incident={selectedIncident}
      />
    </IncidentsLayout>
  );
};

export default IncidentDetail;
