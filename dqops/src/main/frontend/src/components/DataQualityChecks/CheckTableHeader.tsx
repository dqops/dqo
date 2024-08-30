import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckContainerModel,
  DqoJobHistoryEntryModelStatusEnum,
  SimilarCheckModelCheckTypeEnum,
  TimeWindowFilterParameters
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import {
  ColumnApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import Button from '../Button';
import Checkbox from '../Checkbox';
import DeleteOnlyDataDialog from '../CustomTree/DeleteOnlyDataDialog';
import SvgIcon from '../SvgIcon';

interface TableHeaderProps {
  checksUI: CheckContainerModel;
  timeWindowFilter?: TimeWindowFilterParameters | null;

  mode?: string;
  setMode: (mode?: string) => void;
  copyUI?: CheckContainerModel;
  setCopyUI: (ui: CheckContainerModel) => void;
  onUpdate: () => void;
  isDefaultEditing?: boolean;
  showAdvanced?: boolean;
  setShowAdvanced: (showAdvanced: boolean) => void;
  isFiltered?: boolean;
  ruleParamenterConfigured: boolean;
  flashRunChecks?: boolean;
  getCheckOverview?: () => void;
}

const TableHeader = ({
  checksUI,
  timeWindowFilter,
  mode,
  setMode,
  copyUI,
  setCopyUI,
  onUpdate,
  isDefaultEditing,
  showAdvanced,
  setShowAdvanced,
  isFiltered,
  ruleParamenterConfigured,
  flashRunChecks,
  getCheckOverview
}: TableHeaderProps) => {
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkTypes,
    connection,
    schema,
    table,
    column
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useDecodedParams();
  const dispatch = useActionDispatch();
  const history = useHistory();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { currentJobId } = useSelector(getFirstLevelState(checkTypes));
  const job = currentJobId ? job_dictionary_state[currentJobId] : undefined;
  const onRunChecks = async () => {
    await onUpdate();
    const res = await JobApiClient.runChecks(undefined, false, undefined, {
      check_search_filters: checksUI?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? { time_window_filter: timeWindowFilter }
        : {})
    });
    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );
  };

  const onChangeMode = (newMode: string) => {
    setMode(newMode);
    const newCheckUI: CheckContainerModel = { ...checksUI };
    setCopyUI(newCheckUI);
  };

  const copyMonitoringCheck = async (timeScale: 'daily' | 'monthly') => {
    const newUI = {
      ...copyUI,
      categories: copyUI?.categories
        ?.map((category) => ({
          ...category,
          checks: category.checks
            ?.filter((item) => item.configured && item.similar_checks?.length)
            .map((item) => ({
              ...item,
              check_name: item.similar_checks?.find(
                (item) =>
                  item.check_type ===
                    SimilarCheckModelCheckTypeEnum.monitoring &&
                  item.time_scale === timeScale
              )?.check_name
            }))
            .filter((item) => item.check_name)
        }))
        .filter((item) => item.checks?.length)
    };
    let url;
    let value;

    if (column) {
      await ColumnApiClient.updateColumnMonitoringChecksModel(
        connection,
        schema,
        table,
        column,
        timeScale,
        newUI
      );

      url = ROUTES.COLUMN_MONITORING(
        CheckTypes.MONITORING,
        connection,
        schema,
        table,
        column,
        timeScale
      );
      value = ROUTES.COLUMN_MONITORING_VALUE(
        CheckTypes.MONITORING,
        connection,
        schema,
        table,
        column
      );
    } else {
      await TableApiClient.updateTableMonitoringChecksModel(
        connection,
        schema,
        table,
        timeScale,
        newUI
      );

      url = ROUTES.TABLE_MONITORING(
        CheckTypes.MONITORING,
        connection,
        schema,
        table,
        timeScale
      );
      value = ROUTES.TABLE_MONITORING_VALUE(
        CheckTypes.MONITORING,
        connection,
        schema,
        table
      );
    }

    dispatch(
      addFirstLevelTab(CheckTypes.MONITORING, {
        url,
        value,
        state: {},
        label: `${
          timeScale === 'daily' ? 'Daily' : 'Monthly'
        } monitoring checks`
      })
    );
    history.push(url);
  };

  const copyPartitionCheck = async (timeScale: 'daily' | 'monthly') => {
    const newUI = {
      ...copyUI,
      categories: copyUI?.categories
        ?.map((category) => ({
          ...category,
          checks: category.checks
            ?.filter((item) => item.configured && item.similar_checks?.length)
            .map((item) => ({
              ...item,
              check_name: item.similar_checks?.find(
                (item) =>
                  item.check_type ===
                    SimilarCheckModelCheckTypeEnum.partitioned &&
                  item.time_scale === timeScale
              )?.check_name
            }))
            .filter((item) => item.check_name)
        }))
        .filter((item) => item.checks?.length)
    };

    let url;
    let value;

    if (column) {
      await ColumnApiClient.updateColumnPartitionedChecksModel(
        connection,
        schema,
        table,
        column,
        timeScale,
        newUI
      );

      url = ROUTES.COLUMN_PARTITIONED(
        CheckTypes.PARTITIONED,
        connection,
        schema,
        table,
        column,
        timeScale
      );
      value = ROUTES.COLUMN_PARTITIONED_VALUE(
        CheckTypes.PARTITIONED,
        connection,
        schema,
        table,
        column
      );
    } else {
      await TableApiClient.updateTablePartitionedChecksModel(
        connection,
        schema,
        table,
        timeScale,
        newUI
      );

      url = ROUTES.TABLE_PARTITIONED(
        CheckTypes.PARTITIONED,
        connection,
        schema,
        table,
        timeScale
      );
      value = ROUTES.TABLE_PARTITIONED_VALUE(
        CheckTypes.PARTITIONED,
        connection,
        schema,
        table
      );
    }

    dispatch(
      addFirstLevelTab(CheckTypes.PARTITIONED, {
        url,
        value,
        state: {},
        label: `${timeScale === 'daily' ? 'Daily' : 'Monthly'} partition checks`
      })
    );
    history.push(url);
  };

  useEffect(() => {
    if (!getCheckOverview) return;
    if (
      job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      getCheckOverview();
    }
  }, [job?.status]);

  return (
    <thead className="relative">
      {ruleParamenterConfigured && (
        <tr>
          <td
            className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"
            colSpan={2}
          ></td>
          <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400 relative pl-1">
            Passing rule (KPI met)
            <div className="w-4 bg-white absolute h-full right-0 top-0"></div>
          </td>
          <td
            className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"
            colSpan={2}
          >
            Failing rule (KPI not met)
          </td>
        </tr>
      )}
      <tr>
        <th className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          <div className="flex items-center ">
            <div className="flex space-x-1 items-center w-45">
              <span className="mr-1">Data quality check</span>
            </div>
            <div className=" flex items-center justify-start font-normal">
              {isFiltered !== true ? (
                <Checkbox
                  label="Show advanced checks"
                  labelPosition="right"
                  checked={showAdvanced}
                  onChange={(value) => setShowAdvanced(value)}
                />
              ) : null}
            </div>
          </div>
        </th>
        <th className="text-start whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          <div className="flex gap-2 items-center font-normal text-gray-950 justify-start">
            {mode === 'monitoring' && (
              <>
                <div className="text-sm">Copy selected checks to:</div>
                <Button
                  color="primary"
                  label="Daily monitoring checks"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  onClick={() => copyMonitoringCheck('daily')}
                />
                <Button
                  color="primary"
                  label="Monthly monitoring checks"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  onClick={() => copyMonitoringCheck('monthly')}
                />
                <Button
                  color="primary"
                  label="Cancel"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  variant="outlined"
                  onClick={() => setMode(undefined)}
                />
              </>
            )}
            {mode === 'partitioned' && (
              <>
                <div className="text-sm">Copy selected checks to:</div>
                <Button
                  color="primary"
                  label="Daily partition checks"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  onClick={() => copyPartitionCheck('daily')}
                />
                <Button
                  color="primary"
                  label="Monthly partition checks"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  onClick={() => copyPartitionCheck('monthly')}
                />
                <Button
                  color="primary"
                  label="Cancel"
                  textSize="sm"
                  className="font-medium px-1 py-1"
                  variant="outlined"
                  onClick={() => setMode(undefined)}
                />
              </>
            )}
          </div>
        </th>

        {ruleParamenterConfigured ? (
          <>
            <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-yellow-100 relative pl-1 min-w-44">
              Warning threshold
              <div className="w-4 bg-white absolute h-full right-0 top-0"></div>
            </td>
            <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-orange-100">
              Error threshold
            </td>
            <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-red-100">
              Fatal threshold
            </td>
          </>
        ) : (
          <>
            <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-gray-400">
              <div className="flex items-center !w-40">
                Issue severity level
              </div>
            </td>
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-gray-400">
              <div className="flex items-center">Rule thresholds</div>
            </td>
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-gray-400">
              <div className="w-38 h-full bg-gray-400"></div>
            </td>
          </>
        )}
        {isDefaultEditing !== true && (
          <div className="flex justify-end gap-x-3 absolute right-4 top-2">
            <div className="group relative">
              <SvgIcon
                name="delete"
                width={20}
                className="cursor-pointer"
                onClick={() => setDeleteDataDialogOpened(true)}
              />
              <div className="hidden group-hover:block absolute bottom-[-10] right-2 px-2 py-1 bg-black text-white text-xxs rounded-md mt-1">
                Delete data quality results for the category
              </div>
            </div>
            {job?.status === DqoJobHistoryEntryModelStatusEnum.running ||
            job?.status === DqoJobHistoryEntryModelStatusEnum.queued ||
            job?.status === DqoJobHistoryEntryModelStatusEnum.waiting ? (
              <SvgIcon
                name="hourglass"
                className="text-gray-700 h-5 cursor-pointer"
              />
            ) : (
              <div
                className={clsx(
                  'group relative rounded-99',
                  flashRunChecks && 'flash-red-border'
                )}
              >
                <SvgIcon
                  name="play"
                  width={20}
                  className={clsx('text-primary cursor-pointer')}
                  onClick={onRunChecks}
                />
                <div
                  className={clsx(
                    'hidden group-hover:block absolute right-2 px-2 py-1 bg-black text-white text-xxs rounded-md mt-1',
                    flashRunChecks
                      ? 'w-40 right-[22px] bottom-[-10px]'
                      : 'right-[22px] bottom-[-10px]'
                  )}
                >
                  {flashRunChecks
                    ? 'Data quality checks are configured, but DQOps does not have any recent results. Please run the data quality checks to get the values.'
                    : 'Run checks for the category'}
                </div>
              </div>
            )}
          </div>
        )}
      </tr>
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params) => {
          setDeleteDataDialogOpened(false);
          JobApiClient.deleteStoredData(undefined, false, undefined, {
            ...checksUI.data_clean_job_template,
            ...params
          });
        }}
      />
    </thead>
  );
};

export default TableHeader;
