import React, { useEffect } from 'react';
import {
  CheckResultsOverviewDataModel,
  UICheckContainerModel,
  UICheckModel,
  UIEffectiveScheduleModelScheduleLevelEnum
} from '../../api';
import { useTree } from '../../contexts/treeContext';
import clsx from 'clsx';
import { useHistory, useParams } from "react-router-dom";
import CheckCategoriesView from "./CheckCategoriesView";
import TableHeader from "./CheckTableHeader";
import Loader from "../Loader";
import { CheckTypes, ROUTES } from "../../shared/routes";
import moment from "moment/moment";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { addFirstLevelTab } from "../../redux/actions/source.actions";

interface IDataQualityChecksProps {
  checksUI?: UICheckContainerModel;
  onChange: (ui: UICheckContainerModel) => void;
  className?: string;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  getCheckOverview: () => void;
  onUpdate: () => void;
  loading?: boolean;
}

const DataQualityChecks = ({ checksUI, onChange, className, checkResultsOverview = [], getCheckOverview, onUpdate, loading }: IDataQualityChecksProps) => {
  const { checkTypes, connection, schema, table, column, timeScale }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string, timeScale: 'daily' | 'monthly' } = useParams();
  const history = useHistory();
  const dispatch = useActionDispatch();

  const { sidebarWidth } = useTree();
  const handleChangeDataDataStreams = (
    check: UICheckModel,
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
  }, [checkTypes, connection, schema, table, column, timeScale]);

  const goToSchedule = () => {
    if (checksUI?.effective_schedule?.schedule_level === UIEffectiveScheduleModelScheduleLevelEnum.connection) {
      dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schedule'),
        value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
        state: {},
        label: connection
      }))
      history.push(ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schedule'));
      return;
    }
    if (checksUI?.effective_schedule?.schedule_level === UIEffectiveScheduleModelScheduleLevelEnum.table_override) {
      dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'schedule'),
        value: ROUTES.TABLE_LEVEL_VALUE(CheckTypes.SOURCES, connection, schema, table),
        state: {},
        label: table
      }))
      history.push(ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'schedule'));
      return;
    }
  };

  const goToScheduleTab = () => {
    if (checksUI?.effective_schedule?.schedule_level === UIEffectiveScheduleModelScheduleLevelEnum.connection) {
      const url = `${ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schedule')}?activeTab=${checksUI?.effective_schedule?.schedule_group}`;
      dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.CONNECTION_LEVEL_VALUE(CheckTypes.SOURCES, connection),
        state: {},
        label: connection
      }))
      history.push(url);
      return;
    }
    if (checksUI?.effective_schedule?.schedule_level === UIEffectiveScheduleModelScheduleLevelEnum.table_override) {
      dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
        url: ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'schedule'),
        value: ROUTES.TABLE_LEVEL_VALUE(CheckTypes.SOURCES, connection, schema, table),
        state: {},
        label: table
      }))
      history.push(`${ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'schedule')}?activeTab=${checksUI?.effective_schedule?.schedule_group}`);
      return;
    }
  };

  const goToTableTimestamps = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(CheckTypes.SOURCES, connection, schema, table, 'timestamps');
    dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
      url,
      value: ROUTES.TABLE_LEVEL_VALUE(CheckTypes.SOURCES, connection, schema, table),
      state: {},
      label: table
    }))
    history.push(url);
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

  return (
    <div
      className={clsx(className, 'p-4 overflow-auto')}
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 30}px` }}
    >
      <div className="flex items-center text-3xs justify-between mb-3 gap-4">
        <div className="flex items-center space-x-1">
          <span>Scheduling status:</span>
          <span>{checksUI?.effective_schedule_enabled_status}</span>
        </div>
        <div className="flex items-center space-x-1">
          <span>Scheduling configured at:</span>
          <a
            className="underline cursor-pointer"
            onClick={goToSchedule}
          >{checksUI?.effective_schedule?.schedule_level}</a>
        </div>
        <div className="flex items-center space-x-1">
          <span>Effective cron expression:</span>
          <span>{checksUI?.effective_schedule?.cron_expression}</span>
        </div>
        <div className="flex items-center space-x-1">
          <span>Next execution at:</span>
          <span>{moment(checksUI?.effective_schedule?.time_of_execution).format('MMM, DD YYYY')}</span>
        </div>
        <div className="flex items-center space-x-1">
          <span>Configure at:</span>
          <a
            className="underline cursor-pointer"
            onClick={goToScheduleTab}
          >{checksUI?.effective_schedule?.schedule_group}</a>
        </div>
      </div>
      {checkTypes === CheckTypes.PARTITIONED && (
        <div className="flex items-center mb-3 gap-6">
          <div className="text-xs">
            <span className="mr-3">
              The results are partitioned (grouped) by a timestamp column:
            </span>
            {checksUI.partition_by_column || 'Not configured'}
          </div>
          <span
            className="text-primary underline text-xs cursor-pointer"
            onClick={goToTableTimestamps}
          >
            Configure the partition by column
          </span>
        </div>
      )}
      <table className="w-full">
        <TableHeader checksUI={checksUI} />
        <tbody>
          {checksUI?.categories.map((category, index) => (
            <CheckCategoriesView
              key={index}
              category={category}
              checkResultsOverview={checkResultsOverview}
              handleChangeDataDataStreams={(check, jIndex) => handleChangeDataDataStreams(check, index, jIndex)}
              onUpdate={onUpdate}
              getCheckOverview={getCheckOverview}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
