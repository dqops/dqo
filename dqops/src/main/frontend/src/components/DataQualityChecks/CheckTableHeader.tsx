import SvgIcon from '../SvgIcon';
import React, { useState } from 'react';
import {
  DqoJobHistoryEntryModelStatusEnum,
  SimilarCheckModelCheckTypeEnum,
  TimeWindowFilterParameters,
  CheckContainerModel
} from '../../api';
import { JobApiClient, TableApiClient } from '../../services/apiClient';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import DeleteOnlyDataDialog from '../CustomTree/DeleteOnlyDataDialog';
import CategoryMenu from './CategoryMenu';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import Button from '../Button';
import Checkbox from '../Checkbox';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';

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
  isFiltered?: boolean
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
  isFiltered
}: TableHeaderProps) => {
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();
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

    await TableApiClient.updateTableMonitoringChecksModel(
      connection,
      schema,
      table,
      timeScale,
      newUI
    );

    const url = ROUTES.TABLE_MONITORING(
      CheckTypes.MONITORING,
      connection,
      schema,
      table,
      timeScale
    );
    const value = ROUTES.TABLE_MONITORING_VALUE(
      CheckTypes.MONITORING,
      connection,
      schema,
      table
    );

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

    await TableApiClient.updateTablePartitionedChecksModel(
      connection,
      schema,
      table,
      timeScale,
      newUI
    );

    const url = ROUTES.TABLE_PARTITIONED(
      CheckTypes.PARTITIONED,
      connection,
      schema,
      table,
      timeScale
    );
    const value = ROUTES.TABLE_PARTITIONED_VALUE(
      CheckTypes.PARTITIONED,
      connection,
      schema,
      table
    );

    dispatch(
      addFirstLevelTab(CheckTypes.PARTITIONED, {
        url,
        value,
        state: {},
        label: `${
          timeScale === 'daily' ? 'Daily' : 'Monthly'
        } partition checks`
      })
    );
    history.push(url);
  };

  return (
    <thead>
      <tr>
        {checkTypes === CheckTypes.PROFILING ? (
          <td
            colSpan={2}
            className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"
          >
            <div className="flex gap-2 items-center font-normal text-gray-950">
              {isFiltered !== true ? 
              <Checkbox
                label="Show advanced checks"
                labelPosition="right"
                checked={showAdvanced}
                onChange={(value) => setShowAdvanced(value)}
              /> 
              : null}
              {!mode && (
                <>
                  <Button
                    color="primary"
                    label="Set up monitoring checks"
                    textSize="sm"
                    className="font-medium px-1 py-1"
                    variant="outlined"
                    onClick={() => onChangeMode('monitoring')}
                  />
                  <Button
                    color="primary"
                    label="Set up partition checks"
                    textSize="sm"
                    className="font-medium px-1 py-1"
                    variant="outlined"
                    onClick={() => onChangeMode('partitioned')}
                  />
                </>
              )}
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
          </td>
        ) : (
          <>
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
              <div className="flex gap-2 items-center font-normal text-gray-950">
                {isFiltered !== true ? 
                <Checkbox
                  label="Show advanced checks"
                  labelPosition="right"
                  checked={showAdvanced}
                  onChange={(value) => setShowAdvanced(value)}
                /> 
                : null}
              </div>
            </td>
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400" />
          </>
        )}
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400 relative pl-1">
          Passing rule
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
        </td>
        <td
          className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"
          colSpan={2}
        >
          Failing rule
        </td>
      </tr>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          <div className="flex space-x-1 items-center">
            <span className="mr-1">Data quality check</span>
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) &&
              isDefaultEditing !== true && (
                <CategoryMenu
                  onRunChecks={onRunChecks}
                  onDeleteChecks={() => setDeleteDataDialogOpened(true)}
                />
              )}
            {job?.status === DqoJobHistoryEntryModelStatusEnum.waiting && (
              <SvgIcon
                name="hourglass"
                className="text-gray-700 h-5 cursor-pointer"
              />
            )}
            {(job?.status === DqoJobHistoryEntryModelStatusEnum.running ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.queued) && (
              <SvgIcon
                name="hourglass"
                className="text-gray-700 h-5 cursor-pointer"
              />
            )}
          </div>
        </td>
        <td className="text-right whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"></td>
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-yellow-100 relative pl-1 min-w-44">
          Warning threshold
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>

        </td>
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-orange-100">
          Error threshold
        </td>
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-red-100">
          Fatal threshold
        </td>
      </tr>
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params) => {
          setDeleteDataDialogOpened(false);
          JobApiClient.deleteStoredData(
            undefined,
            false,
            undefined,
            {
            ...checksUI.data_clean_job_template,
            ...params
          });
        }}
      />
    </thead>
  );
};

export default TableHeader;
