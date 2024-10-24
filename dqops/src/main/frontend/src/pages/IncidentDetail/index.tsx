import { IconButton, Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  IncidentModel,
  IncidentModelNotificationLocationEnum,
  IncidentModelStatusEnum,
  IssueHistogramModel
} from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import DataLineageGraph from '../../components/DataLineageGraph/DataLineageGraph';
import Input from '../../components/Input';
import { Pagination } from '../../components/Pagination';
import Select from '../../components/Select';
import SvgIcon from '../../components/SvgIcon';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import useDebounce from '../../hooks/useDebounce';
import { addFirstLevelTab as addFirstLevelConfigurationTab } from '../../redux/actions/definition.actions';
import {
  getIncidentsIssues,
  setIncidentNavigation,
  setIncidentsFilter
} from '../../redux/actions/incidents.actions';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IncidentIssueFilter } from '../../redux/reducers/incidents.reducer';
import { getFirstLevelIncidentsState } from '../../redux/selectors';
import { IncidentsApi } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getDaysString, useDecodedParams } from '../../utils';
import AddIssueUrlDialog from '../IncidentConnection/AddIssueUrlDialog';
import { HistogramChart } from './HistogramChart';
import { IncidentIssueList } from './IncidentIssueList';

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

const options = [
  {
    label: '1 Day',
    value: 1
  },
  {
    label: '7 Days',
    value: 7
  },
  {
    label: '30 Days',
    value: 30
  },
  {
    label: 'All issues',
    value: undefined
  }
];

export const IncidentDetail = () => {
  const {
    connection,
    year: strYear,
    month: strMonth,
    id: incidentId
  }: {
    connection: string;
    year: string;
    month: string;
    id: string;
  } = useDecodedParams();
  const year = parseInt(strYear, 10);
  const month = parseInt(strMonth, 10);
  const [incidentDetail, setIncidentDetail] = useState<IncidentModel>();
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearchTerm = useDebounce(searchTerm, 300);

  const [open, setOpen] = useState(false);
  const [disableDialog, setDisableDialog] = useState(false);
  const [recalibrateDialog, setRecalibrateDialog] = useState(false);
  const [createNotificationDialogOpen, setCreateNotificationDialogOpen] =
    useState(false);
  const [showDataLineage, setShowDataLineage] = useState(false);
  const dispatch = useActionDispatch();
  const { sidebarWidth } = useTree();
  const { issues, filters = {} } = useSelector(getFirstLevelIncidentsState);
  const history = useHistory();
  const { histograms }: { histograms: IssueHistogramModel } = useSelector(
    getFirstLevelIncidentsState
  );
  useEffect(() => {
    IncidentsApi.getIncident(connection, year, month, incidentId).then(
      (res) => {
        setIncidentDetail(res?.data);
      }
    );

    dispatch(
      getIncidentsIssues({
        connection,
        year,
        month,
        incidentId
      })
    );
  }, [connection, year, month, incidentId]);

  useEffect(() => {
    dispatch(
      setIncidentNavigation({
        connection: incidentDetail?.connection ?? '',
        schema: incidentDetail?.schema ?? '',
        table: incidentDetail?.table ?? ''
      })
    );
  }, [incidentDetail]);

  const onChangeIncidentStatus = async (status: IncidentModelStatusEnum) => {
    if (!incidentDetail) return;

    setIncidentDetail({
      ...incidentDetail,
      status
    });

    await IncidentsApi.setIncidentStatus(
      incidentDetail.connection || '',
      incidentDetail.year || 0,
      incidentDetail.month || 0,
      incidentDetail.incidentId || '',
      status
    );
  };

  const handleAddIssueUrl = async (issueUrl: string) => {
    if (!incidentDetail) return;

    setIncidentDetail({
      ...incidentDetail,
      issueUrl
    });
    await IncidentsApi.setIncidentIssueUrl(
      incidentDetail?.connection || '',
      incidentDetail?.year || 0,
      incidentDetail?.month || 0,
      incidentDetail?.incidentId || '',
      issueUrl
    );
  };

  const onChangeFilter = (obj: Partial<IncidentIssueFilter>) => {
    dispatch(
      setIncidentsFilter({
        ...(filters || {}),
        ...obj
      })
    );
    dispatch(
      getIncidentsIssues({
        ...(filters || {}),
        ...obj,
        connection,
        year,
        month,
        incidentId
      })
    );
  };

  const getWarnings = (minimumSeverity?: number) => {
    if (!minimumSeverity) return 'No warnings';
    if (minimumSeverity > 1) return `${minimumSeverity} Warnings`;

    return 'Warning';
  };

  const getSeverity = (highestSeverity?: number) => {
    if (!highestSeverity || highestSeverity / 3 < 1) return 'No fatals';
    if (Math.floor(highestSeverity / 3) > 1) return `${highestSeverity} Fatals`;

    return 'Fatal';
  };

  const goToConfigure = () => {
    const schema = incidentDetail?.schema || '';
    const table = incidentDetail?.table || '';
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'incident_configuration'
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {},
        label: 'Incident configuration'
      })
    );
    history.push(url);
  };

  const disableIncident = async () => {
    IncidentsApi.disableChecksForIncident(connection, year, month, incidentId);
    setDisableDialog(false);
  };

  const recalibrateIncident = async () => {
    IncidentsApi.recalibrateChecksForIncident(
      connection,
      year,
      month,
      incidentId
    );
    setRecalibrateDialog(false);
  };

  const createConfirmNotification = async () => {
    if (
      incidentDetail?.notificationLocation ===
      IncidentModelNotificationLocationEnum.global
    ) {
      dispatch(
        addFirstLevelConfigurationTab({
          url: ROUTES.WEBHOOKS_DEFAULT_DETAIL(),
          value: ROUTES.WEBHOOKS_DEFAULT_DETAIL_VALUE(),
          state: {
            incidentFilters: {
              ...filters,
              ...incidentDetail
            }
          },
          label: incidentDetail.notificationName ?? 'New default notifications'
        })
      );
      history.push(ROUTES.WEBHOOKS_DEFAULT_DETAIL());
    } else {
      const url = ROUTES.CONNECTION_DETAIL(
        CheckTypes.SOURCES,
        connection,
        'notifications'
      );
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url,
          value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
          state: {
            incidentFilters: {
              ...filters,
              ...incidentDetail
            }
          },
          label: connection ?? incidentDetail?.connection
        })
      );
      history.push(url);
    }
  };

  const createNotification = () => {
    if (incidentDetail?.notificationName) {
      createConfirmNotification();
    } else {
      setCreateNotificationDialogOpen(true);
    }
  };

  const configureSourceTables = () => {
    const schema = incidentDetail?.schema || '';
    const table = incidentDetail?.table || '';
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'source_tables'
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {
          showSourceTables: true
        },
        label: 'Source tables'
      })
    );
    history.push(url);
  };

  return (
    <>
      <div className="relative">
        <div className="flex items-center justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
              Data quality incident {`${year}/${month}/${incidentId}`}
            </div>
          </div>
          <div className="flex space-x-3">
            <div className="flex items-center gap-x-2">
              <Tooltip
                content={
                  'Disable data quality checks related to this check to avoid raising a similar incident again.'
                }
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <Button
                    leftIcon={<SvgIcon name="stop" className="w-5.5 h-5.5" />}
                    className="pr-1.5 py-1.5 pl-1.5 m-0 hover:bg-[#028770]"
                    color="primary"
                    onClick={() => setDisableDialog(true)}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={
                  'Reconfigure (decrease) the rule threshold for the data quality check that caused the incident by 30% to reduce the number of data quality issues.'
                }
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <Button
                    leftIcon={<SvgIcon name="minus" className="w-5.5 h-5.5" />}
                    className="pr-1.5 py-1.5 pl-1.5 m-0 hover:bg-[#028770]"
                    color="primary"
                    onClick={() => setRecalibrateDialog(true)}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={'Change incident configuration for the table.'}
                className="w-52 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <Button
                    leftIcon={<SvgIcon name="cog" className="w-5.5 h-5.5" />}
                    className="pr-1.5 py-1.5 pl-1.5 m-0 hover:bg-[#028770]"
                    color="primary"
                    onClick={goToConfigure}
                  />
                </div>
              </Tooltip>
              <Tooltip
                content={
                  'Configure notifications for this and similar incidents.'
                }
                className="w-52 py-2 px-2 bg-gray-800 delay-700"
              >
                <div className="text-white">
                  <Button
                    leftIcon={
                      <SvgIcon
                        name="letter"
                        className="w-5.5 h-5.5 min-w-4 shrink-0 p-0.5"
                      />
                    }
                    className="pr-1.5 py-1.5 pl-1.5 m-0 hover:bg-[#028770]"
                    color="primary"
                    onClick={createNotification}
                  />
                </div>
              </Tooltip>
            </div>
          </div>
        </div>
        <div className="flex items-center p-4 gap-6 text-sm">
          <div className="grow">
            <Input
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Filter issues: col*"
              className="!h-12"
            />
          </div>

          <div className="flex gap-2">
            {options.map((o, index) => (
              <Button
                key={index}
                label={o.label}
                color={o.value === filters?.days ? 'primary' : undefined}
                onClick={() => onChangeFilter({ days: o.value, page: 1 })}
                className="text-sm"
              />
            ))}
          </div>
        </div>
        <div className="pb-5 px-4 overflow-y-auto">
          {showDataLineage ? (
            <SectionWrapper
              title={'Collapse data lineage'}
              svgIcon
              onClick={() => setShowDataLineage(false)}
              className="mt-2"
            >
              <DataLineageGraph
                connection={incidentDetail?.connection || ''}
                schema={incidentDetail?.schema || ''}
                table={incidentDetail?.table || ''}
                configureSourceTables={configureSourceTables}
              />
            </SectionWrapper>
          ) : (
            <div className="flex items-center text-sm">
              <SvgIcon
                name="chevron-down"
                className="w-5 h-5"
                onClick={() => setShowDataLineage(true)}
              />
              <div> Expand data lineage</div>
            </div>
          )}
        </div>
        <div className="grid grid-cols-4 gap-4 px-4 text-sm">
          <SectionWrapper title="Table">
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Connection</div>
              <div className="flex-[2] font-bold whitespace-normal break-all">
                {incidentDetail?.connection}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Schema</div>
              <div className="flex-[2] font-bold whitespace-normal break-all">
                {incidentDetail?.schema}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Table</div>
              <div className="flex-[2] font-bold whitespace-normal break-all">
                {incidentDetail?.table}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Table priority</div>
              <div className="flex-[2] font-bold">
                {incidentDetail?.tablePriority}
              </div>
            </div>
          </SectionWrapper>
          <SectionWrapper title="Status and time range">
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Status:</div>
              <div className="flex-[2] font-bold">
                <Select
                  value={incidentDetail?.status}
                  options={statusOptions}
                  onChange={onChangeIncidentStatus}
                />
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">First seen:</div>
              <div className="flex-[2] font-bold">
                <span className="mr-2">
                  {moment(incidentDetail?.firstSeen).format('YYYY-MM-DD')}
                </span>
                {incidentDetail?.firstSeen &&
                  Number(
                    getDaysString(incidentDetail?.firstSeen).match(
                      /[-]{0,1}\d+/gm
                    )
                  ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.firstSeen) + ')'}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Last seen:</div>
              <div className="flex-[2] font-bold">
                <span className="mr-2">
                  {moment(incidentDetail?.lastSeen).format('YYYY-MM-DD')}
                </span>
                {incidentDetail?.lastSeen &&
                  Number(
                    getDaysString(incidentDetail?.lastSeen || 0).match(
                      /[-]{0,1}\d+/gm
                    )
                  ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.lastSeen) + ')'}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Valid until</div>
              <div className="flex-[2] font-bold">
                <span className="mr-2">
                  {moment(incidentDetail?.incidentUntil).format('YYYY-MM-DD')}
                </span>
                {incidentDetail?.incidentUntil &&
                  Number(
                    getDaysString(incidentDetail?.incidentUntil).match(
                      /[-]{0,1}\d+/gm
                    )
                  ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.incidentUntil) + ')'}
              </div>
            </div>
          </SectionWrapper>
          <SectionWrapper title="Severity statistics">
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-[2]">Minimum issue severity:</div>
              <div className="flex-[1] text-right font-bold">
                {getWarnings(incidentDetail?.minimumSeverity)}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-[2]">Highest detected issue severity:</div>
              <div className="flex-[1] text-right font-bold">
                {getSeverity(incidentDetail?.highestSeverity)}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-[2]">
                Total issues:
                <span className="inline-flex">
                  <Tooltip
                    className="max-w-80 py-2 px-2 bg-gray-800"
                    content={
                      'The total number of detected issues can be higher than the count of data quality results when the results were deleted, or data quality checks were run again, overwritting previous results.'
                    }
                  >
                    <div>
                      <SvgIcon name="question_mark" className="w-5 h-5" />
                    </div>
                  </Tooltip>
                </span>
              </div>
              <div className="flex-[1] text-right font-bold">
                {incidentDetail?.failedChecksCount}
              </div>
            </div>
            <div className="flex gap-3 items-center">
              <div className="flex-[2]">Issue url:</div>
              <div className="flex-[1] text-right font-bold">
                <div>
                  {incidentDetail?.issueUrl ? (
                    <div className="flex items-center space-x-2">
                      <a
                        href={incidentDetail?.issueUrl}
                        target="_blank"
                        rel="noreferrer"
                        className="underline"
                      >
                        <Tooltip
                          content={incidentDetail?.issueUrl}
                          className="max-w-80 py-2 px-2 bg-gray-800 delay-300"
                          placement="top-start"
                        >
                          {incidentDetail?.issueUrl.length > 15
                            ? '...' +
                              incidentDetail?.issueUrl.substring(
                                incidentDetail?.issueUrl.length - 15
                              )
                            : incidentDetail?.issueUrl}
                        </Tooltip>
                      </a>
                      <IconButton
                        ripple={false}
                        color="teal"
                        size="sm"
                        onClick={() => setOpen(true)}
                        className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      >
                        <SvgIcon name="edit" className="w-4" />
                      </IconButton>
                    </div>
                  ) : (
                    <IconButton
                      ripple={false}
                      color="teal"
                      size="sm"
                      onClick={() => setOpen(true)}
                      className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                    >
                      <SvgIcon name="add" className="w-4" />
                    </IconButton>
                  )}
                </div>
              </div>
            </div>
          </SectionWrapper>
          <SectionWrapper title="Data quality issue grouping">
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Quality dimension:</div>
              <div className="flex-1 font-bold">
                {incidentDetail?.qualityDimension}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Check category:</div>
              <div className="flex-1 font-bold">
                {incidentDetail?.checkCategory}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Check type:</div>
              <div className="flex-1 font-bold">
                {incidentDetail?.checkType}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Check name:</div>
              <div className="flex-1 font-bold">
                {incidentDetail?.checkName}
              </div>
            </div>
            <div className="flex gap-3 items-center">
              <div className="flex-1">Data group:</div>
              <div className="flex-1 font-bold">
                {incidentDetail?.dataGroup}
              </div>
            </div>
          </SectionWrapper>
        </div>
        {issues?.length === 0 &&
        Object.keys(histograms?.checks ?? {}).length === 0 &&
        Object.keys(histograms?.columns ?? {}).length === 0 ? (
          <div className="text-center text-gray-500 mt-4">
            No active data quality issues found, the data quality results were
            deleted, or the checks were executed again with new rule thresholds
            and passed.
          </div>
        ) : (
          <>
            <HistogramChart
              onChangeFilter={onChangeFilter}
              days={filters.days}
            />
            <div className="px-4 text-sm mb-4">
              <div
                className="py-3 mb-5 overflow-auto"
                style={{ maxWidth: `calc(100vw - ${sidebarWidth + 100}px` }}
              >
                <IncidentIssueList
                  incidentDetail={incidentDetail}
                  filters={filters}
                  issues={issues || []}
                  onChangeFilter={onChangeFilter}
                />
              </div>
              <div className="flex justify-end">
                <Pagination
                  page={filters.page || 1}
                  pageSize={filters.pageSize || 10}
                  totalPages={10}
                  isEnd={(filters.pageSize || 10) > issues?.length}
                  onChange={(page, pageSize) =>
                    onChangeFilter({
                      page,
                      pageSize
                    })
                  }
                />
              </div>
            </div>
          </>
        )}
      </div>

      <AddIssueUrlDialog
        open={open}
        onClose={() => setOpen(false)}
        onSubmit={handleAddIssueUrl}
        incident={incidentDetail}
      />
      <ConfirmDialog
        open={disableDialog}
        onClose={() => setDisableDialog(false)}
        onConfirm={disableIncident}
        message="Are you sure you want to disable checks for this incident?"
      />
      <ConfirmDialog
        open={recalibrateDialog}
        onClose={() => setRecalibrateDialog(false)}
        onConfirm={recalibrateIncident}
        message="Are you sure you want to recalibrate checks for this incident?"
      />
      <ConfirmDialog
        open={createNotificationDialogOpen}
        onClose={() => setCreateNotificationDialogOpen(false)}
        onConfirm={createConfirmNotification}
        message="No notification filters have been defined for this incident. Would you like to create a notification configuration based on this incident?"
        yesNo
      />
    </>
  );
};

export default IncidentDetail;
