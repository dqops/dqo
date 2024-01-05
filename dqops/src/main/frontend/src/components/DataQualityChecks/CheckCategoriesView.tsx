import React, { Fragment, useEffect, useState } from 'react';
import SvgIcon from '../SvgIcon';
import CheckListItem from './CheckListItem';
import {
  CheckResultsOverviewDataModel,
  TimeWindowFilterParameters,
  CheckModel,
  QualityCategoryModel,
} from '../../api';
import { useSelector } from 'react-redux';
import { JobApiClient } from '../../services/apiClient';
import DeleteOnlyDataDialog from '../CustomTree/DeleteOnlyDataDialog';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../shared/routes';
import { setCurrentJobId } from '../../redux/actions/source.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { IRootState } from '../../redux/reducers';
import { clsx } from 'clsx';

interface CheckCategoriesViewProps {
  category: QualityCategoryModel;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  handleChangeDataGroupingConfiguration: (
    check: CheckModel,
    index: number
  ) => void;
  onUpdate: () => void;
  getCheckOverview: () => void;
  timeWindowFilter?: TimeWindowFilterParameters | null;
  mode?: string;
  changeCopyUI: (category: string, checkName: string, checked: boolean) => void;
  copyCategory?: QualityCategoryModel;
  isDefaultEditing?: boolean;
  isFiltered?: boolean;
  showAdvanced?: boolean,
  isAlreadyDeleted?: boolean
}
const CheckCategoriesView = ({
  mode,
  category,
  checkResultsOverview,
  handleChangeDataGroupingConfiguration,
  onUpdate,
  getCheckOverview,
  timeWindowFilter,
  changeCopyUI,
  copyCategory,
  isDefaultEditing,
  isFiltered,
  showAdvanced,
  isAlreadyDeleted
}: CheckCategoriesViewProps) => {
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [isExtended, setIsExtended] = useState(false);

  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const shouldExtend = () => {
    if (category.checks?.some((x) => x.configured === true)) {
      setIsExtended(true);
    }
  };

  const onRunChecks = async () => {
    await onUpdate();
    const res = await JobApiClient.runChecks(undefined, false, undefined, {
      check_search_filters: category?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? { timeWindowFilter }
        : {})
    });
    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );

    if (getCheckOverview) {
      getCheckOverview();
    }
  };

  useEffect(() => {
    shouldExtend();
  }, []);

  return (
    <Fragment>
      <tr>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" colSpan={2}>
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <div
                className="font-semibold text-gray-700 flex items-center justify-center gap-x-3 cursor-pointer"
                onClick={() => setIsExtended(!isExtended)}
              >
                {isExtended === false ? (
                  <SvgIcon
                    name="chevron-right"
                    className="w-5 h-5 text-gray-700"
                  />
                ) : (
                  <SvgIcon
                    name="chevron-down"
                    className="w-5 h-5 text-gray-700"
                  />
                )}
                {category.category?.replace(/^\w/, (c) => c.toUpperCase())}
              </div>
            </div>
            <div> </div>
          </div>
        </td>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t" />
        <td className="py-2 px-4 bg-gray-50 border-b border-t">
          {isDefaultEditing !== true && (
            <div className="flex justify-end gap-x-3">
              <div className="group relative">
                <SvgIcon
                  name="delete"
                  width={20}
                  className="cursor-pointer"
                  onClick={() => setDeleteDataDialogOpened(true)}
                />
                <div className="hidden group-hover:block absolute bottom-5 right-0 px-2 py-1 bg-black text-white text-xxs rounded-md mt-1">
                  Delete data for the category
                </div>
              </div>
              <div className="group relative">
                <SvgIcon
                  name="play"
                  width={20}
                  className={clsx(
                    'text-primary',
                    userProfile.can_run_checks !== true
                      ? 'pointer-events-none cursor-not-allowed'
                      : 'cursor-pointer'
                  )}
                  onClick={onRunChecks}
                />
                <div className="hidden group-hover:block absolute bottom-5 right-0 px-2 py-1 bg-black text-white text-xxs rounded-md mt-1">
                  Run checks for the category
                </div>
              </div>
            </div>
          )}
        </td>
      </tr>
      {category.checks &&
        isExtended &&
          category.checks.filter((check) => showAdvanced || check.standard || check.configured || isAlreadyDeleted || isFiltered)
          .map((check, index) => (
          <CheckListItem
            check={check}
            key={index}
            onChange={(item) =>
              handleChangeDataGroupingConfiguration(item, index)
            }
            checkResult={checkResultsOverview.find(
              (item) => item.checkHash === check.check_hash
            )}
            getCheckOverview={getCheckOverview}
            onUpdate={onUpdate}
            timeWindowFilter={timeWindowFilter}
            mode={mode}
            changeCopyUI={(value) =>
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
            category={category.category}
            comparisonName={category.comparison_name}
            isDefaultEditing={isDefaultEditing}
            canUserRunChecks={userProfile.can_run_checks}
            isAlreadyDeleted={isAlreadyDeleted}
          />
        ))}
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params) => {
          setDeleteDataDialogOpened(false);
          JobApiClient.deleteStoredData(undefined, false, undefined, {
            ...category.data_clean_job_template,
            ...params
          });
        }}
      />
    </Fragment>
  );
};

export default CheckCategoriesView;
