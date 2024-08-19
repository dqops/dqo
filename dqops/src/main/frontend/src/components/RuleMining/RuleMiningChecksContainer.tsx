import clsx from 'clsx';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckContainerModel,
  CheckMiningProposalModel,
  CheckModel
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setRuleParametersConfigured } from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import Loader from '../Loader';
import SvgIcon from '../SvgIcon';
import RuleMiningChecksContainerCategory from './RuleMiningChecksContainerCategory';
import RuleMiningChecksContainerHeader from './RuleMiningChecksContainerHeader';

interface IDataQualityChecksProps {
  checksUI?: CheckMiningProposalModel;
  onChange: (ui: CheckMiningProposalModel) => void;
  className?: string;
  onUpdate: () => void;
  loading?: boolean;
  isDefaultEditing?: boolean;
  timePartitioned?: 'daily' | 'monthly';
  isFiltered?: boolean;
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
}

const RuleMiningChecksContainer = ({
  checksUI,
  onChange,
  className,
  onUpdate,
  loading,
  isDefaultEditing,
  isFiltered,
  timePartitioned,
  setTimePartitioned
}: IDataQualityChecksProps) => {
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useDecodedParams();

  const dispatch = useActionDispatch();
  const [timeWindow, setTimeWindow] = useState(
    'Default incremental time window'
  );
  const [mode, setMode] = useState<string>();
  const [copyUI, setCopyUI] = useState<CheckContainerModel>();
  const [showAdvanced, setShowAdvanced] = useState<boolean>(
    isFiltered === true
  );
  const [isExtendedArray, setIsExtendedArray] = useState<string[]>([
    'Table level checks'
  ]);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { ruleParametersConfigured } = useSelector(
    getFirstLevelState(checkTypes)
  );

  const onChangeIsExtended = (category: string) => {
    if (isExtendedArray.includes(category)) {
      setIsExtendedArray(isExtendedArray.filter((x) => x !== category));
    } else {
      setIsExtendedArray([...isExtendedArray, category]);
    }
  };

  const { sidebarWidth } = useTree();

  const handleChangeTableDataGrouping = (
    check: CheckModel,
    idx: number,
    jdx: number
  ) => {
    if (!checksUI) return;
    const newChecksUI = {
      ...checksUI,
      table_checks: {
        ...checksUI.table_checks,
        categories: checksUI.table_checks?.categories?.map((category, index) =>
          index !== idx
            ? category
            : {
                ...category,
                checks: category?.checks?.map((item, jindex) =>
                  jindex !== jdx ? item : check
                )
              }
        )
      }
    };
    onChange(newChecksUI);
  };
  const handleChangeColumnDataGrouping = (
    check: CheckModel,
    idx: number,
    jdx: number,
    columnName: string
  ) => {
    if (!checksUI) return;
    const newChecksUI = {
      ...checksUI,
      column_checks: {
        ...checksUI.column_checks,
        [columnName]: {
          ...checksUI?.column_checks?.[columnName],
          categories: checksUI.column_checks?.[columnName]?.categories?.map(
            (category, index) =>
              index !== idx
                ? category
                : {
                    ...category,
                    checks: category?.checks?.map((item, jindex) =>
                      jindex !== jdx ? item : check
                    )
                  }
          )
        }
      }
    };

    onChange(newChecksUI);
  };
  const changeCopyUI = (
    category: string,
    checkName: string,
    checked: boolean
  ) => {
    setCopyUI({
      ...copyUI,
      categories: copyUI?.categories?.map((item) =>
        item.category === category
          ? {
              ...item,
              checks: item.checks?.map((check) =>
                check.check_name === checkName
                  ? {
                      ...check,
                      configured: checked
                    }
                  : check
              )
            }
          : item
      )
    });
  };

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  //   if (!checksUI?.categories) {
  //     return <div className="p-4">Please wait, loading data quality checks.</div>;
  //   }

  const onChangeRuleParametersConfigured = (param: boolean) => {
    dispatch(
      setRuleParametersConfigured(checkTypes, firstLevelActiveTab, param)
    );
  };

  const getConfiguredChecksCount = (checks: CheckContainerModel) => {
    let count = 0;
    if (!checks?.categories) return;
    checks?.categories.forEach((category) => {
      if (!category?.checks) return;
      category?.checks.forEach((check) => {
        if (check.configured) {
          count++;
        }
      });
    });
    return count;
  };

  return (
    <div
      className={clsx(className, ' overflow-y-auto relative ')}
      style={{
        maxWidth: `calc(100vw - ${sidebarWidth + 30}px`,
        minWidth: '100%'
      }}
    >
      <table className="w-full ">
        <RuleMiningChecksContainerHeader
          ruleParamenterConfigured={!!ruleParametersConfigured}
        />
        {checksUI?.table_checks?.categories &&
          checksUI?.table_checks?.categories?.length > 0 && (
            <tbody>
              <tr
                onClick={() => onChangeIsExtended('Table level checks')}
                className=" cursor-pointer"
              >
                <td colSpan={100} className="w-full">
                  <div className="w-full flex items-center gap-x-3 font-bold text-md py-2 pl-4 bg-gray-300">
                    <SvgIcon
                      name={
                        !isExtendedArray.includes('Table level checks')
                          ? 'chevron-right'
                          : 'chevron-down'
                      }
                      className="w-5 h-5 text-gray-700"
                    />
                    <span>Table level checks</span>
                  </div>
                </td>
              </tr>
            </tbody>
          )}
        {(checksUI?.table_checks?.categories ?? []).map((category, index) => (
          <tbody key={index}>
            {isExtendedArray.includes('Table level checks') && (
              <RuleMiningChecksContainerCategory
                category={category}
                timeWindowFilter={RUN_CHECK_TIME_WINDOW_FILTERS[timeWindow]}
                handleChangeDataGroupingConfiguration={(check, jIndex) =>
                  handleChangeTableDataGrouping(check, index, jIndex)
                }
                onUpdate={onUpdate}
                mode={mode}
                changeCopyUI={changeCopyUI}
                copyCategory={copyUI?.categories?.find(
                  (item) => item.category === category.category
                )}
                isDefaultEditing={isDefaultEditing}
                showAdvanced={showAdvanced}
                isFiltered={isFiltered}
                ruleParamenterConfigured={ruleParametersConfigured}
                onChangeRuleParametersConfigured={
                  onChangeRuleParametersConfigured
                }
              />
            )}
          </tbody>
        ))}
        {Object.entries(checksUI?.column_checks ?? {}).length > 0 && (
          <tr className="w-full">
            <td colSpan={100} className="w-full ">
              <div className="w-full flex items-center gap-x-3 font-bold text-md py-2 pl-4 bg-gray-300">
                Column level checks
              </div>
            </td>
          </tr>
        )}
        {Object.entries(checksUI?.column_checks ?? {}).map(
          ([key, category], index) => (
            <tbody key={index}>
              <tr
                onClick={() => onChangeIsExtended(key)}
                className=" cursor-pointer"
              >
                <td colSpan={100} className="w-full">
                  <div className="w-full flex items-center gap-x-3 font-bold text-md py-2 pl-4 bg-gray-300">
                    <SvgIcon
                      name={
                        !isExtendedArray.includes(key)
                          ? 'chevron-right'
                          : 'chevron-down'
                      }
                      className="w-5 h-5 text-gray-700"
                    />
                    {key} {`(${getConfiguredChecksCount(category)})`}
                  </div>
                </td>
              </tr>
              {isExtendedArray.includes(key) &&
                category?.categories?.map((x, jindex) => (
                  <RuleMiningChecksContainerCategory
                    key={x.category && x?.category + jindex}
                    category={x}
                    timeWindowFilter={RUN_CHECK_TIME_WINDOW_FILTERS[timeWindow]}
                    handleChangeDataGroupingConfiguration={(check, jIndex) =>
                      handleChangeColumnDataGrouping(check, index, jIndex, key)
                    }
                    onUpdate={onUpdate}
                    mode={mode}
                    changeCopyUI={changeCopyUI}
                    copyCategory={copyUI?.categories?.find(
                      (item) => item.category === category
                    )}
                    isDefaultEditing={isDefaultEditing}
                    showAdvanced={showAdvanced}
                    isFiltered={isFiltered}
                    ruleParamenterConfigured={ruleParametersConfigured}
                    onChangeRuleParametersConfigured={
                      onChangeRuleParametersConfigured
                    }
                  />
                ))}
            </tbody>
          )
        )}
      </table>
    </div>
  );
};

export default RuleMiningChecksContainer;
