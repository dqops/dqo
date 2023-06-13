import SvgIcon from "../SvgIcon";
import React, { useState } from "react";
import {
  DqoJobHistoryEntryModelStatusEnum,
  SimilarCheckModelCheckTypeEnum,
  TimeWindowFilterParameters,
  UICheckContainerModel
} from "../../api";
import { JobApiClient, TableApiClient } from "../../services/apiClient";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import DeleteOnlyDataDialog from "../CustomTree/DeleteOnlyDataDialog";
import CategoryMenu from "./CategoryMenu";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { useHistory, useParams } from "react-router-dom";
import Button from "../Button";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { addFirstLevelTab } from "../../redux/actions/source.actions";

interface TableHeaderProps {
  checksUI: UICheckContainerModel;
  timeWindowFilter?: TimeWindowFilterParameters | null;

  mode?: string;
  setMode: (mode?: string) => void;
  copyUI?: UICheckContainerModel,
  setCopyUI: (ui: UICheckContainerModel) => void;
}

const TableHeader = ({ checksUI, timeWindowFilter, mode, setMode, copyUI, setCopyUI }: TableHeaderProps) => {
  const { job_dictionary_state } = useSelector((state: IRootState) => state.job || {});
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { checkTypes, connection, schema, table }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const onRunChecks = async () => {
    const res = await JobApiClient.runChecks(false, undefined, {
      checkSearchFilters: checksUI?.run_checks_job_template,
      ... checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null ? { timeWindowFilter } : {}
    });
    setJobId(res.data?.jobId?.jobId);
  };

  const onChangeMode = (newMode: string) => {
    setMode(newMode);

    const newCheckUI: UICheckContainerModel = {...checksUI};

    setCopyUI(newCheckUI);
  };

  const copyRecurringCheck = async (timeScale: 'daily' | 'monthly') => {
    const newUI = {
      ...copyUI,
      categories: copyUI?.categories?.map((category) => ({
        ...category,
        checks: category.checks?.filter((item) => item.configured && item.similar_checks?.length).map((item) => ({
          ...item,
          check_name: item.similar_checks?.find((item) => item.check_type === SimilarCheckModelCheckTypeEnum.recurring && item.time_scale === timeScale)?.check_name
        })).filter(item => item.check_name)
      })).filter((item) => item.checks?.length)
    }

    await TableApiClient.updateTableRecurringChecksUI(connection, schema, table, timeScale, newUI);

    const url = ROUTES.TABLE_RECURRING(CheckTypes.RECURRING, connection, schema, table, timeScale);
    const value = ROUTES.TABLE_RECURRING_VALUE(CheckTypes.RECURRING, connection, schema, table);

    dispatch(addFirstLevelTab(CheckTypes.RECURRING, {
      url,
      value,
      state: {},
      label: `${timeScale === 'daily' ? 'Daily' : 'Monthly'} recurring checks`
    }));
    history.push(url);
  };

  const copyPartitionCheck = async (timeScale: 'daily' | 'monthly') => {
    const newUI = {
      ...copyUI,
      categories: copyUI?.categories?.map((category) => ({
        ...category,
        checks: category.checks?.filter((item) => item.configured && item.similar_checks?.length).map((item) => ({
          ...item,
          check_name: item.similar_checks?.find((item) => item.check_type === SimilarCheckModelCheckTypeEnum.partitioned && item.time_scale === timeScale)?.check_name
        })).filter(item => item.check_name)
      })).filter((item) => item.checks?.length)
    }

    await TableApiClient.updateTablePartitionedChecksUI(connection, schema, table, timeScale, newUI);

    const url = ROUTES.TABLE_PARTITIONED(CheckTypes.PARTITIONED, connection, schema, table, timeScale);
    const value = ROUTES.TABLE_PARTITIONED_VALUE(CheckTypes.PARTITIONED, connection, schema, table);

    dispatch(addFirstLevelTab(CheckTypes.PARTITIONED, {
      url,
      value,
      state: {},
      label: `${timeScale === 'daily' ? 'Daily' : 'Monthly'} partitioned checks`
    }));
    history.push(url);
  };

  return (
    <thead>
      <tr>
        {checkTypes === CheckTypes.PROFILING ? (
          <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
            <div className="flex gap-2 items-center">
              {!mode && (
                <>
                  <Button
                    color="primary"
                    label="Set up recurring checkpoints"
                    textSize="sm"
                    className="font-medium px-4"
                    variant="outlined"
                    onClick={() => onChangeMode('recurring')}
                  />
                  <Button
                    color="primary"
                    label="Set up date partition checks"
                    textSize="sm"
                    className="font-medium px-4"
                    variant="outlined"
                    onClick={() => onChangeMode('partitioned')}
                  />
                </>
              )}
              {mode === 'recurring' && (
                <>
                  <div className="text-sm">Copy selected checks to:</div>
                  <Button
                    color="primary"
                    label="Daily recurring checkpoints"
                    textSize="sm"
                    className="font-medium px-4"
                    onClick={() => copyRecurringCheck('daily')}
                  />
                  <Button
                    color="primary"
                    label="Monthly recurring checkpoints"
                    textSize="sm"
                    className="font-medium px-4"
                    onClick={() => copyRecurringCheck('monthly')}
                  />
                  <Button
                    color="primary"
                    label="Cancel"
                    textSize="sm"
                    className="font-medium px-4"
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
                    className="font-medium px-4"
                    onClick={() => copyPartitionCheck('daily')}
                  />
                  <Button
                    color="primary"
                    label="Monthly partition checks"
                    textSize="sm"
                    className="font-medium px-4"
                    onClick={() => copyPartitionCheck('monthly')}
                  />
                  <Button
                    color="primary"
                    label="Cancel"
                    textSize="sm"
                    className="font-medium px-4"
                    variant="outlined"
                    onClick={() => setMode(undefined)}
                  />
                </>
              )}
            </div>
          </td>
        ) : (
          <>
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400" />
            <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400" />
          </>
        )}
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          Passing check
        </td>
        <td
          className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400"
          colSpan={2}
        >
          Failing check
        </td>
      </tr>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          <div className="flex space-x-1 items-center">
            <span className="mr-1">Data quality check</span>
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
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
        <td className="text-left whitespace-nowrap text-gray-700 py-1.5 px-4 font-semibold bg-gray-400">
          Sensor parameters
        </td>
        <td className="text-center whitespace-nowrap text-gray-700 py-1.5 px-4 border-b font-semibold bg-yellow-100">
          Warning threshold
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
          JobApiClient.deleteStoredData({
            ...checksUI.data_clean_job_template,
            ...params,
          });
        }}
      />
    </thead>
  );
};

export default TableHeader;