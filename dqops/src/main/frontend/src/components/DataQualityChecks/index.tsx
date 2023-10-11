import React, { useEffect, useState } from 'react';
import {
  CheckResultsOverviewDataModel,
  CheckContainerModel,
  CheckContainerModelEffectiveScheduleEnabledStatusEnum,
  CheckModel,
  EffectiveScheduleModelScheduleLevelEnum
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import clsx from 'clsx';
import { useHistory, useParams } from 'react-router-dom';
import CheckCategoriesView from './CheckCategoriesView';
import TableHeader from './CheckTableHeader';
import Loader from '../Loader';
import { CheckTypes, ROUTES } from '../../shared/routes';
import moment from 'moment/moment';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import Button from '../Button';
import Select from '../Select';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';

interface IDataQualityChecksProps {
  checksUI?: CheckContainerModel;
  onChange: (ui: CheckContainerModel) => void;
  className?: string;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  getCheckOverview: () => void;
  onUpdate: () => void;
  loading?: boolean;
  isDefaultEditing?: boolean;
}

const DataQualityChecks = ({
  checksUI,
  onChange,
  className,
  checkResultsOverview = [],
  getCheckOverview,
  onUpdate,
  loading,
  isDefaultEditing
}: IDataQualityChecksProps) => {
  const {
    checkTypes,
    connection,
    schema,
    table,
    column,
    timePartitioned,
    tab
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    timePartitioned: 'daily' | 'monthly';
    tab: 'daily' | 'monthly';
  } = useParams();
  const history = useHistory();
  const dispatch = useActionDispatch();
  const [timeWindow, setTimeWindow] = useState(
    'Default incremental time window'
  );
  const [mode, setMode] = useState<string>();
  const [copyUI, setCopyUI] = useState<CheckContainerModel>();

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
  }, [checkTypes, connection, schema, table, column, timePartitioned]);

  const goToSchedule = () => {
    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.connection
    ) {
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url: ROUTES.CONNECTION_DETAIL(
            CheckTypes.SOURCES,
            connection,
            'schedule'
          ),
          value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
          state: {},
          label: connection
        })
      );
      history.push(
        ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schedule')
      );
      return;
    }
    if (
      checksUI?.effective_schedule?.schedule_level ===
      EffectiveScheduleModelScheduleLevelEnum.table_override
    ) {
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url: ROUTES.TABLE_LEVEL_PAGE(
            CheckTypes.SOURCES,
            connection,
            schema,
            table,
            'schedule'
          ),
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
      history.push(
        ROUTES.TABLE_LEVEL_PAGE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table,
          'schedule'
        )
      );
      return;
    }
  };

  const goToConnectionSchedule = () => {
    let activeTab = checksUI?.effective_schedule?.schedule_group;
    if (!activeTab) {
      if (checkTypes === CheckTypes.PROFILING) {
        activeTab = checkTypes;
      } else if (checkTypes !== CheckTypes.SOURCES) {
        let timeScale = tab || timePartitioned;

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
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.TABLE_LEVEL_PAGE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table,
          'schedule'
        ),
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
    history.push(
      `${ROUTES.TABLE_LEVEL_PAGE(
        CheckTypes.SOURCES,
        connection,
        schema,
        table,
        'schedule'
      )}activeTab=${checksUI?.effective_schedule?.schedule_group}`
    );
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
    return <div className="p-4">No Checks</div>;
  }

  const timeWindowOptions = Object.keys(RUN_CHECK_TIME_WINDOW_FILTERS).map(
    (item) => ({
      label: item,
      value: item
    })
  );

  return (
    <div
      className={clsx(className, 'p-1 overflow-y-auto')}
      style={{
        maxWidth: `calc(100vw - ${sidebarWidth + 30}px`,
        minWidth: '100%'
      }}
    >
      <div className="flex items-center text-sm mb-3 gap-6">
        {isDefaultEditing !== true &&
        <div className="flex items-center space-x-1 gap-x-4">
          <div className="flex items-center space-x-1">
            <span>Scheduling status:</span>
            <span>
              {checksUI?.effective_schedule_enabled_status
                ?.replaceAll('_', ' ')
                .split(' ')
                .map((item) => item.charAt(0).toUpperCase() + item.slice(1))
                .join(' ')}
            </span>
          </div>
          {checksUI.effective_schedule_enabled_status !==
          CheckContainerModelEffectiveScheduleEnabledStatusEnum.not_configured ? (
            <div className="flex items-center gap-x-4">
              <div className="flex items-center space-x-1">
                <span>Scheduling configured at:</span>
                <a className="underline cursor-pointer" onClick={goToSchedule}>
                  {checksUI?.effective_schedule?.schedule_level}
                </a>
              </div>
              <div className="flex items-center space-x-1">
                <span>Effective cron expression:</span>
                <span>{checksUI?.effective_schedule?.cron_expression}</span>
              </div>
            </div>
          ) : (
            <div className="text-red-500">
              Warning: Data Quality Checks will not be scheduled, please
              configure the scheduling
            </div>
          )}
          {checksUI?.effective_schedule?.cron_expression && (
            <div className="flex items-center space-x-1">
              <span>Next execution at:</span>
              <span>
                {moment(checksUI?.effective_schedule?.time_of_execution).format(
                  'MMM, DD YYYY HH:mm'
                )}
              </span>
            </div>
          )}
        </div>
        }
        {isDefaultEditing !== true && 
        <div className="flex items-center justify-between">
          <span className='pr-2'>Schedule configuration: </span>
          <a className="underline cursor-pointer" onClick={goToScheduleTab}>
            {checksUI?.effective_schedule?.schedule_group}
          </a>

          {checksUI?.effective_schedule_enabled_status ===
            CheckContainerModelEffectiveScheduleEnabledStatusEnum.not_configured && (
            <div className="flex items-center gap-x-4">
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
        }
      </div>
      {checkTypes === CheckTypes.PARTITIONED && (
        <div className="flex items-center mb-3 gap-6">
          <div className="text-sm text-red-500">
            <span className="mr-3 text-black">
              The results are date partitioned (grouped) by a column:
            </span>
            {checksUI.partition_by_column ? (
              <span className="text-black">{checksUI.partition_by_column}</span>
            ) : (
              'Warning: Partition checks will not be run, please configure the date or datetime column'
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
              <span>Time window:</span>
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
        />
        <tbody>
          {checksUI?.categories.map((category, index) => (
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
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
