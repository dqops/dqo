import { clsx } from 'clsx';
import React, { Fragment, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckModel,
  CheckResultsOverviewDataModel,
  DqoJobHistoryEntryModelStatusEnum,
  QualityCategoryModel,
  TimeWindowFilterParameters
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCurrentJobId } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import DeleteOnlyDataDialog from '../CustomTree/DeleteOnlyDataDialog';
import SvgIcon from '../SvgIcon';
import CheckListItem from './CheckListItem';

type CheckIndexTuple = [check: CheckModel, index: number];

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
  showAdvanced?: boolean;
  isAlreadyDeleted?: boolean;
  ruleParamenterConfigured: boolean;
  onChangeRuleParametersConfigured: (v: boolean) => void;
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
  isAlreadyDeleted,
  ruleParamenterConfigured,
  onChangeRuleParametersConfigured
}: CheckCategoriesViewProps) => {
  const { job_dictionary_state, userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [isExtended, setIsExtended] = useState(false);

  const shouldExtend = () => {
    if (
      category.checks?.some(
        (x) => x.configured === true || x.default_check === true
      )
    ) {
      setIsExtended(true);
    }
  };

  const onRunChecks = async () => {
    await onUpdate();
    const res = await JobApiClient.runChecks(undefined, false, undefined, {
      check_search_filters: category?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? {
            time_window_filter: timeWindowFilter,
            collect_error_samples: true
          }
        : { collect_error_samples: true })
    });
    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );
    setJobId(res.data?.jobId?.jobId);
  };

  useEffect(() => {
    shouldExtend();
  }, []);

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
                {category.category === 'pii'
                  ? 'PII'
                  : category.category === 'custom_sql'
                  ? 'Custom SQL'
                  : category.category === 'datatype'
                  ? 'Detected data type'
                  : category.category === 'datetime'
                  ? 'Date and time'
                  : category.category
                      ?.replace(/_/g, ' ')
                      .replace(/^\w/, (c) => c.toUpperCase())}
              </div>
            </div>
            <div> </div>
          </div>
        </td>
        <td className="py-2 px-4 bg-gray-50 border-b border-t" colSpan={2} />
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
                  Delete data quality results for the category
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
        category.checks
          .map((check, index) => {
            const checkIndexPair: CheckIndexTuple = [check, index];
            return checkIndexPair;
          })
          .filter(
            (tuple) =>
              showAdvanced ||
              tuple[0].standard ||
              tuple[0].configured ||
              isAlreadyDeleted ||
              isFiltered
          )
          .map((tuple) => (
            <CheckListItem
              check={tuple[0]}
              key={tuple[1]}
              onChange={(item) =>
                handleChangeDataGroupingConfiguration(item, tuple[1])
              }
              checkResult={checkResultsOverview.find(
                (item) => item.checkHash === tuple[0].check_hash
              )}
              getCheckOverview={getCheckOverview}
              onUpdate={onUpdate}
              timeWindowFilter={timeWindowFilter}
              mode={mode}
              changeCopyUI={(value) =>
                changeCopyUI(
                  category.category ?? '',
                  tuple[0].check_name ?? '',
                  value
                )
              }
              checkedCopyUI={
                copyCategory?.checks?.find(
                  (item) => item.check_name === tuple[0].check_name
                )?.configured
              }
              category={category.category}
              comparisonName={category.comparison_name}
              isDefaultEditing={isDefaultEditing}
              canUserRunChecks={userProfile.can_run_checks}
              isAlreadyDeleted={isAlreadyDeleted}
              ruleParamenterConfigured={ruleParamenterConfigured}
              onChangeRuleParametersConfigured={
                onChangeRuleParametersConfigured
              }
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
