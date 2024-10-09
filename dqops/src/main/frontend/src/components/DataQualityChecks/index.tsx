import clsx from 'clsx';
import moment from 'moment/moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckContainerModel,
  CheckContainerModelEffectiveScheduleEnabledStatusEnum,
  CheckModel,
  CheckResultsOverviewDataModel,
  CheckSearchFiltersCheckTypeEnum,
  EffectiveScheduleModelScheduleLevelEnum,
  QualityCategoryModel
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  setRuleParametersConfigured
} from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';
import { CheckTypes, ROUTES } from '../../shared/routes';
import {
  getIsAnyCheckResults,
  getIsAnyChecksEnabledOrDefault,
  useDecodedParams
} from '../../utils';
import Button from '../Button';
import Loader from '../Loader';
import Select from '../Select';
import Tabs from '../Tabs';
import CheckCategoriesView from './CheckCategoriesView';
import TableHeader from './CheckTableHeader';

interface IDataQualityChecksProps {
  checksUI?: CheckContainerModel;
  onChange: (ui: CheckContainerModel) => void;
  className?: string;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  getCheckOverview: () => void;
  onUpdate: () => void;
  loading?: boolean;
  isDefaultEditing?: boolean;
  timePartitioned?: 'daily' | 'monthly';
  isFiltered?: boolean;
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
}

const DataQualityChecks = ({
  checksUI,
  onChange,
  className,
  checkResultsOverview = [],
  getCheckOverview,
  onUpdate,
  loading,
  isDefaultEditing,
  isFiltered,
  timePartitioned,
  setTimePartitioned
}: IDataQualityChecksProps) => {
  const {
    checkTypes,
    connection,
    schema,
    table,
    column,
    tab
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    tab: 'daily' | 'monthly';
  } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job);

  const tabs = [
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Daily checkpoints'
          : 'Daily partitioned',
      value: 'daily'
    },
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Monthly checkpoints'
          : 'Monthly partitioned',
      value: 'monthly'
    }
  ];
  const history = useHistory();
  const dispatch = useActionDispatch();
  const [timeWindow, setTimeWindow] = useState(
    'Default incremental time window'
  );
  const [mode, setMode] = useState<string>();
  const [copyUI, setCopyUI] = useState<CheckContainerModel>();
  const [showAdvanced, setShowAdvanced] = useState<boolean>(
    isFiltered === true
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { ruleParametersConfigured } = useSelector(
    getFirstLevelState(checkTypes)
  );

  const { sidebarWidth } = useTree();
  const handleChangeDataGrouping = (
    check: CheckModel,
    idx: number,
    jdx: number
  ) => {
    if (!checksUI) return;

    const newChecksUI = {
      ...checksUI,
      categories: checksUI?.categories?.map((category, index) =>
        index !== idx
          ? category
          : {
              ...category,
              checks: category?.checks?.map((item, jindex) =>
                jindex !== jdx ? item : check
              )
            }
      )
    };

    onChange(newChecksUI);
  };

  useEffect(() => {
    getCheckOverview();
  }, [
    checkTypes,
    connection,
    schema,
    table,
    column,
    timePartitioned,
    firstLevelActiveTab
  ]);

  const goToSchedule = () => {
    let activeTab = checksUI?.effective_schedule?.schedule_group;
    if (!activeTab) {
      if (checkTypes === CheckTypes.PROFILING) {
        activeTab = checkTypes;
      } else if (checkTypes !== CheckTypes.SOURCES) {
        let timeScale = timePartitioned;

        if (!timeScale) {
          timeScale = checksUI?.run_checks_job_template?.timeScale || 'daily';
        }
        activeTab = `${checkTypes}_${timeScale}`;
      }
    }

    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.connection
    ) {
      const url = `${ROUTES.CONNECTION_DETAIL(
        CheckTypes.SOURCES,
        connection,
        'schedule'
      )}?activeTab=${activeTab}`;
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url: url,
          value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
          state: {},
          label: connection
        })
      );
      history.push(url);
      return;
    }
    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.table_override
    ) {
      const url = `${ROUTES.TABLE_LEVEL_PAGE(
        CheckTypes.SOURCES,
        connection,
        schema,
        table,
        'schedule'
      )}?activeTab=${activeTab}`;
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url: url,
          value: ROUTES.TABLE_LEVEL_VALUE(
            CheckTypes.SOURCES,
            connection,
            schema,
            table
          ),
          state: {},
          label: table
        })
      );
      history.push(url);
      return;
    }
  };

  const goToConnectionSchedule = () => {
    let activeTab = checksUI?.effective_schedule?.schedule_group;
    if (!activeTab) {
      if (checkTypes === CheckTypes.PROFILING) {
        activeTab = checkTypes;
      } else if (checkTypes !== CheckTypes.SOURCES) {
        let timeScale = timePartitioned;

        if (!timeScale) {
          timeScale = checksUI?.run_checks_job_template?.timeScale || 'daily';
        }
        activeTab = `${checkTypes}_${timeScale}`;
      }
    }

    const url = `${ROUTES.CONNECTION_DETAIL(
      CheckTypes.SOURCES,
      connection,
      'schedule'
    )}?activeTab=${activeTab}`;
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
        state: {},
        label: connection
      })
    );
    history.push(url);
    return;
  };

  const goToTableSchedule = () => {
    let activeTab = checksUI?.effective_schedule?.schedule_group;
    if (!activeTab) {
      if (checkTypes === CheckTypes.PROFILING) {
        activeTab = checkTypes;
      } else if (checkTypes !== CheckTypes.SOURCES) {
        let timeScale = timePartitioned;

        if (!timeScale) {
          timeScale = checksUI?.run_checks_job_template?.timeScale || 'daily';
        }
        activeTab = `${checkTypes}_${timeScale}`;
      }
    }

    const url = `${ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'schedule'
    )}?activeTab=${activeTab}`;
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {},
        label: table
      })
    );
    history.push(url);
    return;
  };

  const goToScheduleTab = () => {
    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.connection
    ) {
      goToConnectionSchedule();
    }
    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.table_override
    ) {
      goToTableSchedule();
    }
  };

  const goToTableTimestamps = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'timestamps'
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
        label: table
      })
    );
    history.push(url);
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

  if (!checksUI?.categories) {
    return <div className="p-4">Please wait, loading data quality checks.</div>;
  }

  const timeWindowOptions = Object.keys(RUN_CHECK_TIME_WINDOW_FILTERS).map(
    (item) => ({
      label: item,
      value: item
    })
  );
  const getCustomCategoryBasedOnResults = () => {
    const checkResultCopy = [...checkResultsOverview];
    const missingCategory = checkResultCopy.filter(
      (obj1) =>
        checksUI.categories &&
        !checksUI.categories.find(
          (obj2) =>
            obj1.checkCategory + '/' + obj1.comparisonName === obj2.category ||
            obj1.checkCategory === obj2.category
        )
    );

    const customCategory: CheckResultsOverviewDataModel[] = missingCategory.map(
      (x) => ({
        ...x,
        checkCategory: x.comparisonName
          ? x.checkCategory + '/' + x.comparisonName
          : x.checkCategory
      })
    );

    const groupedArray = customCategory.reduce(
      (acc: QualityCategoryModel[], obj) => {
        const existingCategory = acc.find(
          (item) => item.category === obj.checkCategory
        );

        if (existingCategory && existingCategory.checks) {
          existingCategory.checks.push({
            check_name: obj.checkName,
            check_hash: obj.checkHash,
            run_checks_job_template: {
              checkType: checkTypes as CheckSearchFiltersCheckTypeEnum,
              timeScale: tab || timePartitioned
            }
          });
        } else {
          acc.push({
            category: obj.checkCategory,
            comparison_name: obj.comparisonName,
            checks: [
              {
                check_name: obj.checkName,
                check_hash: obj.checkHash,
                run_checks_job_template: {
                  checkType: checkTypes as CheckSearchFiltersCheckTypeEnum,
                  timeScale: tab || timePartitioned
                }
              }
            ]
          });
        }

        return acc;
      },
      []
    );
    return groupedArray ?? [];
  };

  const getScheduleLevelBasedOnEnum = (
    schedule?: EffectiveScheduleModelScheduleLevelEnum
  ) => {
    switch (schedule) {
      case EffectiveScheduleModelScheduleLevelEnum.check_override: {
        return 'Check level';
      }
      case EffectiveScheduleModelScheduleLevelEnum.connection: {
        return 'Connection level';
      }
      case EffectiveScheduleModelScheduleLevelEnum.table_override: {
        return 'Table level';
      }
    }
  };
  const onChangeRuleParametersConfigured = (param: boolean) => {
    dispatch(
      setRuleParametersConfigured(checkTypes, firstLevelActiveTab, param)
    );
  };

  return (
    <div
      className={clsx(className, ' overflow-y-auto')}
      style={{
        maxWidth: `calc(100vw - ${sidebarWidth + 30}px`,
        minWidth: '100%'
      }}
    >
      {timePartitioned &&
        userProfile &&
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at && (
          <div className="border-b border-gray-300">
            <Tabs
              tabs={tabs}
              activeTab={timePartitioned}
              onChange={setTimePartitioned}
              className="pt-2"
            />
          </div>
        )}
      <div className="flex items-center text-sm my-3 gap-6 ml-4">
        {isDefaultEditing !== true && (
          <div className="flex items-center space-x-1 gap-x-4">
            <div className="flex items-center space-x-1">
              <div className="inline-block whitespace-nowrap">
                Scheduling status:
              </div>
              <div className="inline-block whitespace-nowrap">
                {checksUI?.effective_schedule_enabled_status
                  ?.replaceAll('_', ' ')
                  .split(' ')
                  .map((item) => item.charAt(0).toUpperCase() + item.slice(1))
                  .join(' ')}
              </div>
            </div>
            {checksUI.effective_schedule_enabled_status !==
            CheckContainerModelEffectiveScheduleEnabledStatusEnum.not_configured ? (
              <div className="flex items-center gap-x-4">
                <div className="flex items-center space-x-1">
                  <div className="whitespace-normal min-w-23">
                    Scheduling configured at:
                  </div>
                  <a
                    className="underline cursor-pointer inline-block whitespace-nowrap  "
                    onClick={goToSchedule}
                  >
                    {getScheduleLevelBasedOnEnum(
                      checksUI?.effective_schedule?.schedule_level
                    )}
                  </a>
                </div>
                <div className="flex items-center space-x-1">
                  <div className="inline-block whitespace-nowrap">
                    Effective cron expression:
                  </div>
                  <div className="inline-block whitespace-nowrap">
                    {checksUI?.effective_schedule?.cron_expression}
                  </div>
                </div>
              </div>
            ) : (
              <div className="text-red-500">
                Warning: Data quality checks will not be scheduled, please
                configure the scheduling
              </div>
            )}
            {checksUI?.effective_schedule?.cron_expression && (
              <div className="flex items-center space-x-1">
                <div className="inline-block whitespace-nowrap">
                  Next execution at:
                </div>
                <div className="inline-block whitespace-nowrap">
                  {moment(
                    checksUI?.effective_schedule?.time_of_execution
                  ).format('MMM, DD YYYY HH:mm')}
                </div>
              </div>
            )}
          </div>
        )}
        {isDefaultEditing !== true && (
          <div className="flex items-center justify-between">
            <div className="whitespace-normal">Schedule configuration: </div>
            <a
              className="underline cursor-pointer pl-1"
              onClick={goToScheduleTab}
            >
              {checksUI?.effective_schedule?.schedule_group
                ?.replace(/_/, ' ')
                .replace(/./, (c) => c.toUpperCase())}
            </a>

            {checksUI?.effective_schedule_enabled_status ===
              CheckContainerModelEffectiveScheduleEnabledStatusEnum.not_configured && (
              <div className="flex items-center gap-x-4 !mr-2">
                <Button
                  label="Configure a schedule for the connection"
                  color="primary"
                  variant="outlined"
                  onClick={goToConnectionSchedule}
                  className="px-1 py-1"
                  textSize="sm"
                />

                <Button
                  label="Configure a schedule for the table"
                  color="primary"
                  variant="outlined"
                  onClick={goToTableSchedule}
                  className="px-1 py-1"
                  textSize="sm"
                />
              </div>
            )}
          </div>
        )}
      </div>
      {checkTypes === CheckTypes.PARTITIONED && (
        <div className="flex items-center mb-3 gap-6 ml-4">
          <div className="text-sm text-red-500">
            <div className="mr-3 text-black">
              The results are date partitioned (grouped) by a column:
              {checksUI.partition_by_column ? checksUI.partition_by_column : ''}
            </div>
            {!checksUI.partition_by_column ? (
              <div>
                Warning: Partition checks will not be run, please configure the
                date or datetime column
              </div>
            ) : (
              ''
            )}
          </div>
          <Button
            label="Configure the date partitioning column"
            color="primary"
            variant={checksUI.partition_by_column ? 'outlined' : 'contained'}
            onClick={goToTableTimestamps}
            className="px-1 py-1"
            textSize="sm"
          />
          {checksUI.partition_by_column && (
            <div className="flex gap-2 text-sm items-center">
              <div className="inline-block whitespace-nowrap">Time window:</div>
              <Select
                options={timeWindowOptions}
                value={timeWindow}
                onChange={setTimeWindow}
              />
            </div>
          )}
        </div>
      )}
      <table className="w-full">
        <TableHeader
          checksUI={checksUI}
          timeWindowFilter={RUN_CHECK_TIME_WINDOW_FILTERS[timeWindow]}
          mode={mode}
          setMode={setMode}
          copyUI={copyUI}
          setCopyUI={setCopyUI}
          onUpdate={onUpdate}
          isDefaultEditing={isDefaultEditing}
          showAdvanced={showAdvanced}
          setShowAdvanced={setShowAdvanced}
          isFiltered={isFiltered}
          ruleParamenterConfigured={!!ruleParametersConfigured}
          flashRunChecks={
            getIsAnyChecksEnabledOrDefault(checksUI) &&
            !getIsAnyCheckResults(checkResultsOverview)
          }
          getCheckOverview={getCheckOverview}
        />
        <tbody>
          {(checksUI?.categories ?? []).map((category, index) => (
            <CheckCategoriesView
              key={index}
              category={category}
              checkResultsOverview={checkResultsOverview}
              timeWindowFilter={RUN_CHECK_TIME_WINDOW_FILTERS[timeWindow]}
              handleChangeDataGroupingConfiguration={(check, jIndex) =>
                handleChangeDataGrouping(check, index, jIndex)
              }
              onUpdate={onUpdate}
              getCheckOverview={getCheckOverview}
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
          ))}
          {isFiltered !== true &&
            getCustomCategoryBasedOnResults().map((category, index) => (
              <CheckCategoriesView
                key={index}
                category={category}
                checkResultsOverview={checkResultsOverview}
                timeWindowFilter={RUN_CHECK_TIME_WINDOW_FILTERS[timeWindow]}
                handleChangeDataGroupingConfiguration={(check, jIndex) =>
                  handleChangeDataGrouping(check, index, jIndex)
                }
                onUpdate={onUpdate}
                getCheckOverview={getCheckOverview}
                mode={mode}
                changeCopyUI={changeCopyUI}
                copyCategory={copyUI?.categories?.find(
                  (item) => item.category === category.category
                )}
                isDefaultEditing={isDefaultEditing}
                showAdvanced={showAdvanced}
                isAlreadyDeleted={true}
                ruleParamenterConfigured={!!ruleParametersConfigured}
                onChangeRuleParametersConfigured={
                  onChangeRuleParametersConfigured
                }
              />
            ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
