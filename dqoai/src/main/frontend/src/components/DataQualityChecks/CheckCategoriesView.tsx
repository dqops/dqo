import React, { Fragment, useState } from 'react';
import SvgIcon from '../SvgIcon';
import CheckListItem from './CheckListItem';
import {
  CheckResultsOverviewDataModel,
  DqoJobHistoryEntryModelStatusEnum,
  TimeWindowFilterParameters,
  CheckModel,
  QualityCategoryModel
} from '../../api';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import DeleteOnlyDataDialog from '../CustomTree/DeleteOnlyDataDialog';
import CheckMenu from './CheckMenu';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../shared/routes';

interface CheckCategoriesViewProps {
  category: QualityCategoryModel;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  handleChangeDataDataStreams: (check: CheckModel, index: number) => void;
  onUpdate: () => void;
  getCheckOverview: () => void;
  timeWindowFilter?: TimeWindowFilterParameters | null;
  mode?: string;
  changeCopyUI: (category: string, checkName: string, checked: boolean) => void;
  copyCategory?: QualityCategoryModel;
}
const CheckCategoriesView = ({
  mode,
  category,
  checkResultsOverview,
  handleChangeDataDataStreams,
  onUpdate,
  getCheckOverview,
  timeWindowFilter,
  changeCopyUI,
  copyCategory
}: CheckCategoriesViewProps) => {
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const [jobId, setJobId] = useState<number>();

  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const onRunChecks = async () => {
    const res = await JobApiClient.runChecks(false, undefined, {
      checkSearchFilters: category?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? { timeWindowFilter }
        : {})
    });

    setJobId((res.data as any)?.jobId?.jobId);

    if (getCheckOverview) {
      getCheckOverview();
    }
  };

  return (
    <Fragment>
      <tr>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" colSpan={2}>
          <div className="flex items-center gap-2">
            <div className="font-semibold text-gray-700 capitalize">
              {category.category}
            </div>
            <div className="flex items-center">
              {(!job ||
                job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
                job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
                <CheckMenu
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
          </div>
        </td>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
      </tr>
      {category.checks &&
        category.checks.map((check, index) => (
          <CheckListItem
            check={check}
            key={index}
            onChange={(item) => handleChangeDataDataStreams(item, index)}
            checkResult={checkResultsOverview.find(
              (item) =>
                item.checkName === check.check_name &&
                category.category === item.checkCategory
            )}
            getCheckOverview={getCheckOverview}
            onUpdate={onUpdate}
            timeWindowFilter={timeWindowFilter}
            mode={mode}
            changeCopyUI={(value: boolean) =>
              changeCopyUI(
                category.category ?? '',
                check.check_name ?? '',
                value
              )
            }
            checkedCopyUI={
              copyCategory?.checks?.find(
                (item) => item.check_name === check.check_name
              )?.configured
            }
          />
        ))}
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params) => {
          setDeleteDataDialogOpened(false);
          JobApiClient.deleteStoredData({
            ...category.data_clean_job_template,
            ...params
          });
        }}
      />
    </Fragment>
  );
};

export default CheckCategoriesView;
