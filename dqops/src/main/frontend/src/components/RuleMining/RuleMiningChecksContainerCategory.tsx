import React, { Fragment, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckModel,
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
import RuleMiningChecksContainerListItem from './RuleMiningChecksContainerListItem';

type CheckIndexTuple = [check: CheckModel, index: number];

interface CheckCategoriesViewProps {
  category: QualityCategoryModel;
  handleChangeDataGroupingConfiguration: (
    check: CheckModel,
    index: number,
    columnName?: string
  ) => void;
  onUpdate: () => void;
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
const RuleMiningChecksContainerCategory = ({
  mode,
  category,
  handleChangeDataGroupingConfiguration,
  onUpdate,
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
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [isExtended, setIsExtended] = useState(false);

  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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
        <td className="py-2 px-4 bg-gray-50 border-b border-t"></td>
      </tr>
      {category.checks &&
        isExtended &&
        category.checks
          .map((check, index) => {
            const checkIndexPair: CheckIndexTuple = [check, index];
            return checkIndexPair;
          })
          .map((tuple) => (
            <RuleMiningChecksContainerListItem
              check={tuple[0]}
              key={tuple[1]}
              onChange={(item) =>
                handleChangeDataGroupingConfiguration(item, tuple[1])
              }
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

export default RuleMiningChecksContainerCategory;