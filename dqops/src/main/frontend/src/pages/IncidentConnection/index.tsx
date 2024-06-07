import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import Button from '../../components/Button';
import Input from '../../components/Input';
import SvgIcon from '../../components/SvgIcon';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  getIncidentsByConnection,
  setIncidentsFilter,
  updateIncident
} from '../../redux/actions/incidents.actions';
import { addFirstLevelTab as addSourceFirstLevelTab } from '../../redux/actions/source.actions';
import { getFirstLevelIncidentsState } from '../../redux/selectors';
import StatusSelect from './StatusSelect';

import { IconButton, Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import { IncidentModel, IncidentModelStatusEnum } from '../../api';
import { Pagination } from '../../components/Pagination';
import Select from '../../components/Select';
import { Table } from '../../components/Table';
import useDebounce from '../../hooks/useDebounce';
import { IRootState } from '../../redux/reducers';
import { IncidentFilter } from '../../redux/reducers/incidents.reducer';
import { IncidentsApi } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getDaysString, useDecodedParams } from '../../utils';
import AddIssueUrlDialog from './AddIssueUrlDialog';
import { SortableColumn } from './SortableColumn';

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
    icon: <SvgIcon name="info-filled" className="text-red-900 w-6 h-6" />
  },
  {
    label: 'ACKNOWLEDGED',
    value: IncidentModelStatusEnum.acknowledged,
    icon: <div className="w-5 h-5 rounded-full bg-black" />
  },
  {
    label: 'RESOLVED',
    value: IncidentModelStatusEnum.resolved,
    icon: <SvgIcon name="check-circle" className="text-primary w-6 h-6" />
  },
  {
    label: 'MUTED',
    value: IncidentModelStatusEnum.muted,
    icon: <SvgIcon name="stop" className="w-6 h-6" />
  }
];

export const IncidentConnection = () => {
  const { connection }: { connection: string } = useDecodedParams();
  const {
    incidents,
    isEnd,
    filters = {}
  } = useSelector(getFirstLevelIncidentsState);
  const { activeTab } = useSelector((state: IRootState) => state.incidents);
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearchTerm = useDebounce(searchTerm, 300);
  const [open, setOpen] = useState(false);
  const [selectedIncident, setSelectedIncident] = useState<IncidentModel>();

  const onChangeIncidentStatus = async (
    row: IncidentModel,
    status: IncidentModelStatusEnum
  ) => {
    dispatch(
      updateIncident(
        incidents.map((item: IncidentModel) =>
          item.incidentId === row.incidentId
            ? {
                ...row,
                status
              }
            : item
        )
      )
    );
    await IncidentsApi.setIncidentStatus(
      row.connection || '',
      row.year || 0,
      row.month || 0,
      row.incidentId || '',
      status
    );
  };

  const handleAddIssueUrl = async (issueUrl: string) => {
    dispatch(
      updateIncident(
        incidents.map((item: IncidentModel) =>
          item.incidentId === selectedIncident?.incidentId
            ? {
                ...selectedIncident,
                issueUrl
              }
            : item
        )
      )
    );
    await IncidentsApi.setIncidentIssueUrl(
      selectedIncident?.connection || '',
      selectedIncident?.year || 0,
      selectedIncident?.month || 0,
      selectedIncident?.incidentId || '',
      issueUrl
    );
  };

  const addIssueUrl = (row: IncidentModel) => {
    setSelectedIncident(row);
    setOpen(true);
  };

  const openIncidentDetail = (row: IncidentModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.INCIDENT_DETAIL(
          row.connection || '',
          row.year || 0,
          row.month || 0,
          row.incidentId || ''
        ),
        value: ROUTES.INCIDENT_DETAIL_VALUE(
          row.connection || '',
          row.year || 0,
          row.month || 0,
          row.incidentId || ''
        ),
        state: {},
        label: row.incidentId
      })
    );
    history.push(
      ROUTES.INCIDENT_DETAIL_VALUE(
        row.connection || '',
        row.year || 0,
        row.month || 0,
        row.incidentId || ''
      )
    );
  };

  const handleSortChange = (
    orderBy: string,
    orderDirection?: 'asc' | 'desc'
  ) => {
    onChangeFilter({
      sortBy: orderBy as any,
      ...(filters.sortBy !== orderBy
        ? {
            sortDirection: 'asc',
            page: 1
          }
        : { sortDirection: orderDirection })
    });
  };

  const columns = [
    {
      label: 'Resolution status',
      className: 'text-left py-2 px-4 text-sm',
      value: 'status',
      render: (value: string, row: IncidentModel) => {
        return (
          <div className="flex items-center">
            <Select
              className="!text-sm"
              value={value}
              options={statusOptions}
              onChange={(status) => onChangeIncidentStatus(row, status)}
            />
          </div>
        );
      }
    },
    {
      header: () => (
        <SortableColumn
          className="justify-end text-sm"
          label="Total issues"
          order="failedChecksCount"
          direction={
            filters.sortBy === 'failedChecksCount'
              ? filters.sortDirection
              : undefined
          }
          onChange={handleSortChange}
        />
      ),
      label: 'Total issues',
      className: 'text-right text-sm py-2 px-4',
      value: 'failedChecksCount'
    },
    {
      label: 'Schema',
      className: 'text-left text-sm py-2 px-4',
      value: 'schema'
    },
    {
      header: () => (
        <SortableColumn
          className="text-sm"
          label="Table"
          order="table"
          direction={
            filters.sortBy === 'table' ? filters.sortDirection : undefined
          }
          onChange={handleSortChange}
        />
      ),
      label: 'Table',
      className:
        'text-left text-sm py-2 px-4 max-w-40 min-w-35 whitespace-normal break-all',
      value: 'table',
      render: (value: string) => {
        return <div className="cursor-pointer text-sm text-start">{value}</div>;
      }
    },
    {
      header: () => (
        <SortableColumn
          className="text-sm"
          label="Data quality issue grouping"
          order="qualityDimension"
          direction={
            filters.sortBy === 'qualityDimension'
              ? filters.sortDirection
              : undefined
          }
          onChange={handleSortChange}
        />
      ),
      label: 'Data quality issue grouping',
      className: 'text-left py-2 px-4',
      value: 'checkName',
      alwaysVisible: true,
      render: (value: string, row: IncidentModel) => {
        const values = [
          row.qualityDimension,
          row.checkCategory,
          row.checkType,
          row.checkName
        ].filter((item) => !!item);

        return (
          <a
            className="text-blue-700 cursor-pointer text-sm"
            onClick={() => openIncidentDetail(row)}
          >
            {values.join(', ')} (more)
          </a>
        );
      }
    },
    {
      header: () => (
        <SortableColumn
          className="text-sm"
          label="First seen"
          order="firstSeen"
          direction={
            filters.sortBy === 'firstSeen' ? filters.sortDirection : undefined
          }
          onChange={handleSortChange}
        />
      ),
      label: 'First seen',
      className: 'text-left py-2 px-4',
      value: 'firstSeen',
      render: (value: string) => (
        <div className="text-sm">
          <div>{moment(value).format('YYYY-MM-DD')}</div>
          {getDaysString(value)}
        </div>
      )
    },
    {
      header: () => (
        <SortableColumn
          className="text-sm"
          label="Last seen"
          order="lastSeen"
          direction={
            filters.sortBy === 'lastSeen' ? filters.sortDirection : undefined
          }
          onChange={handleSortChange}
        />
      ),
      label: 'Last seen',
      className: 'text-left py-2 px-4',
      value: 'lastSeen',
      render: (value: string) => (
        <div className="text-sm">
          <div>{moment(value).format('YYYY-MM-DD')}</div>
          {getDaysString(value)}
        </div>
      )
    },
    {
      label: 'Issue Link',
      className: 'text-center issueUrl py-2 px-4 text-sm',
      value: 'issueUrl',
      render: (value: string, row: IncidentModel) => {
        return (
          <div className="flex justify-center">
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
                    className="max-w-80 py-2 px-2 bg-gray-800 delay-300"
                    placement="top-start"
                  >
                    {value.length > 15
                      ? '...' + value.substring(value.length - 15)
                      : value}
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
        );
      }
    }
  ];

  useEffect(() => {
    if (activeTab && activeTab?.length > 0) {
      dispatch(
        getIncidentsByConnection({
          connection
        })
      );
    }
  }, [connection, activeTab]);

  useEffect(() => {
    onChangeFilter({
      optionalFilter: debouncedSearchTerm,
      page: 1,
      openIncidents: true,
      acknowledgedIncidents: true
    });
  }, [debouncedSearchTerm]);

  const onChangeFilter = (obj: Partial<IncidentFilter>) => {
    dispatch(
      setIncidentsFilter({
        ...(filters || {}),
        ...obj
      })
    );
    dispatch(
      getIncidentsByConnection({
        ...(filters || {}),
        ...obj,
        connection
      })
    );
  };

  const goToConfigure = () => {
    dispatch(
      addSourceFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.CONNECTION_DETAIL(
          CheckTypes.SOURCES,
          connection,
          'incidents'
        ),
        value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
        state: {},
        label: connection
      })
    );
    history.push(
      ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'incidents')
    );
  };

  return (
    <>
      <div className="relative">
        <div className="flex items-center justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
              Data quality incidents on {connection || ''}
            </div>
          </div>
          <div className="flex items-center">
            <div className="mr-20">
              <StatusSelect onChangeFilter={onChangeFilter} />
            </div>
            <Button onClick={goToConfigure} color="primary" label="Configure" />
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
                color={
                  o.value === (filters?.numberOfMonth || 3)
                    ? 'primary'
                    : undefined
                }
                onClick={() =>
                  onChangeFilter({ numberOfMonth: o.value, page: 1 })
                }
                className="text-sm"
              />
            ))}
          </div>
        </div>
        <div className="p-4">
          <Table
            columns={columns}
            data={incidents || []}
            className="w-full mb-8"
          />

          <Pagination
            page={filters.page || 1}
            pageSize={filters.pageSize || 10}
            isEnd={isEnd}
            totalPages={10}
            onChange={(page, pageSize) =>
              onChangeFilter({
                page,
                pageSize
              })
            }
          />
        </div>
      </div>

      <AddIssueUrlDialog
        open={open}
        onClose={() => setOpen(false)}
        onSubmit={handleAddIssueUrl}
        incident={selectedIncident}
      />
    </>
  );
};

export default IncidentConnection;
