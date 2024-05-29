import { IconButton, Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  IncidentIssueHistogramModel,
  IncidentModel,
  IncidentModelStatusEnum
} from '../../api';
import Button from '../../components/Button';
import ConfirmDialog from '../../components/CustomTree/ConfirmDialog';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import Input from '../../components/Input';
import { Pagination } from '../../components/Pagination';
import Select from '../../components/Select';
import SvgIcon from '../../components/SvgIcon';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import useDebounce from '../../hooks/useDebounce';
import {
  getIncidentsIssues,
  setIncidentsFilter
} from '../../redux/actions/incidents.actions';
import {
  addFirstLevelTab,
  addFirstLevelTab as addSourceFirstLevelTab
} from '../../redux/actions/source.actions';
import { IncidentIssueFilter } from '../../redux/reducers/incidents.reducer';
import { getFirstLevelIncidentsState } from '../../redux/selectors';
import { IncidentsApi } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getDaysString, useDecodedParams } from '../../utils';
import AddIssueUrlDialog from '../IncidentConnection/AddIssueUrlDialog';
import { HistogramChart } from './HistogramChart';
import { IncidentIssueList } from './IncidentIssueList';
import IncidentNavigation from './IncidentNavigation';

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
  const dispatch = useActionDispatch();
  const { sidebarWidth } = useTree();
  const {
    issues,
    isEnd,
    filters = {}
  } = useSelector(getFirstLevelIncidentsState);
  const history = useHistory();
  const { histograms }: { histograms: IncidentIssueHistogramModel } =
    useSelector(getFirstLevelIncidentsState);
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

  // useEffect(() => {
  //   onChangeFilter({
  //     filter: debouncedSearchTerm,
  //     page: 1
  //   });
  // }, [debouncedSearchTerm]);

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
    dispatch(
      addSourceFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.TABLE_INCIDENTS_NOTIFICATION(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        value: ROUTES.TABLE_INCIDENTS_NOTIFICATION_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {},
        label: 'Incident configuration'
      })
    );
    history.push(
      ROUTES.TABLE_INCIDENTS_NOTIFICATION(
        CheckTypes.SOURCES,
        connection,
        schema,
        table
      )
    );
  };

  const tableQualityStatusOptions: Array<{
    checkType: CheckTypes;
    timeScale?: 'daily' | 'monthly';
    show?: boolean;
  }> = histograms && [
    { checkType: CheckTypes.PROFILING, show: histograms?.hasProfilingIssues },
    {
      checkType: CheckTypes.PARTITIONED,
      timeScale: 'daily',
      show: histograms?.hasDailyPartitionedIssues
    },
    {
      checkType: CheckTypes.PARTITIONED,
      timeScale: 'monthly',
      show: histograms?.hasMonthlyPartitionedIssues
    },
    {
      checkType: CheckTypes.MONITORING,
      timeScale: 'daily',
      show: histograms?.hasDailyMonitoringIssues
    },
    {
      checkType: CheckTypes.MONITORING,
      timeScale: 'monthly',
      show: histograms?.hasMonthlyMonitoringIssues
    }
  ];

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

  const routeTableQualityStatus = (
    checkType: CheckTypes,
    timeScale?: 'daily' | 'monthly'
  ) => {
    const redirectTableQualityStatus = () => {
      const schema = incidentDetail?.schema || '';
      const table = incidentDetail?.table || '';
      dispatch(
        addFirstLevelTab(checkType, {
          url: ROUTES.TABLE_LEVEL_PAGE(
            checkType,
            connection,
            schema,
            table,
            `table-quality-status${
              timeScale !== undefined ? '-' + timeScale : ''
            }`
          ),
          value: ROUTES.TABLE_LEVEL_VALUE(checkType, connection, schema, table),
          state: {},
          label: table
        })
      );
      history.push(
        ROUTES.TABLE_LEVEL_PAGE(
          checkType,
          connection,
          schema,
          table,
          `table-quality-status${
            timeScale !== undefined ? '-' + timeScale : ''
          }`
        )
      );
    };

    return (
      <div className="flex items-center">
        <Button
          label={checkType + ' ' + (timeScale !== undefined ? timeScale : '')}
          variant="text"
          onClick={redirectTableQualityStatus}
          className="m-0 p-0 px-1 text-black font-bold"
        />
        <SvgIcon name="chevron-right" className="w-4 h-4" />
      </div>
    );
  };

  return (
    <>
      <div className="relative">
        <IncidentNavigation incident={incidentDetail} />
        <div className="flex items-center justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="database" className="w-5 h-5 shrink-0" />
            <div className="text-lg font-semibold truncate">
              Data quality incident {`${year}/${month}/${incidentId}`}
            </div>
          </div>
          <div className="flex space-x-3">
            {tableQualityStatusOptions
              ?.filter((y) => y.show)
              .map((x) => routeTableQualityStatus(x.checkType, x.timeScale))}
            <div className="flex items-center gap-x-2">
              <Tooltip
                content={'Disable'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <Button
                  leftIcon={<SvgIcon name="stop" className="w-3.5 h-3.5" />}
                  className="pr-1.5 py-1.5 pl-1.5 m-0 "
                  color="primary"
                  onClick={() => setDisableDialog(true)}
                />
              </Tooltip>
              <Tooltip
                content={'Recalibrate'}
                className="w-30 h-30 py-4 px-4 bg-gray-800"
              >
                <Button
                  leftIcon={<SvgIcon name="minus" className="w-3.5 h-3.5" />}
                  className="pr-1.5 py-1.5 pl-1.5 m-0 "
                  color="primary"
                  onClick={() => setRecalibrateDialog(true)}
                />
              </Tooltip>
              <Tooltip
                content={'Settings'}
                className="max-w-80 py-4 px-4 bg-gray-800 delay-700"
              >
                <Button
                  leftIcon={<SvgIcon name="cog" className="w-3.5 h-3.5" />}
                  className="pr-1.5 py-1.5 pl-1.5 m-0 "
                  color="primary"
                  onClick={goToConfigure}
                />
              </Tooltip>
            </div>
          </div>
        </div>
        <div className="flex items-center p-4 gap-6 mb-4 text-sm">
          <div className="grow">
            <Input
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Filter errors: col*"
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
                {Number(
                  getDaysString(incidentDetail?.firstSeen || 0).match(
                    /[-]{0,1}\d+/gm
                  )
                ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.firstSeen || 0) + ')'}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Last seen:</div>
              <div className="flex-[2] font-bold">
                <span className="mr-2">
                  {moment(incidentDetail?.lastSeen).format('YYYY-MM-DD')}
                </span>
                {Number(
                  getDaysString(incidentDetail?.lastSeen || 0).match(
                    /[-]{0,1}\d+/gm
                  )
                ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.lastSeen || 0) + ')'}
              </div>
            </div>
            <div className="flex gap-3 mb-3 items-center">
              <div className="flex-1">Valid until</div>
              <div className="flex-[2] font-bold">
                <span className="mr-2">
                  {moment(incidentDetail?.incidentUntil).format('YYYY-MM-DD')}
                </span>
                {Number(
                  getDaysString(incidentDetail?.incidentUntil || 0).match(
                    /[-]{0,1}\d+/gm
                  )
                ) >= 1 &&
                  '(' + getDaysString(incidentDetail?.incidentUntil || 0) + ')'}
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
              <div className="flex-[2]">Total issues:</div>
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
                        className="text-blue-600 underline"
                      >
                        <Tooltip
                          content={incidentDetail?.issueUrl}
                          className="max-w-80 py-4 px-4 bg-gray-800 delay-300"
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
                        color="teal"
                        size="sm"
                        onClick={() => setOpen(true)}
                        className="!shadow-none"
                      >
                        <SvgIcon name="edit" className="w-4" />
                      </IconButton>
                    </div>
                  ) : (
                    <IconButton
                      color="teal"
                      size="sm"
                      onClick={() => setOpen(true)}
                      className="!shadow-none"
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

        <HistogramChart onChangeFilter={onChangeFilter} days={filters.days} />
        <div className="px-4 text-sm">
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

          <Pagination
            page={filters.page || 1}
            pageSize={filters.pageSize || 50}
            totalPages={10}
            isEnd={isEnd}
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
    </>
  );
};

export default IncidentDetail;
