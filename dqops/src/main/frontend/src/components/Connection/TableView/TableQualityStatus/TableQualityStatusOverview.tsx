import clsx from 'clsx';
import moment from 'moment';
import React, { useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { ROUTES, CheckTypes } from '../../../../shared/routes';
import {
  ITableParameters,
  TFirstLevelCheck
} from './TableQualityStatusConstans';
import TableQualityStatusColumnCategory from './TableQualityStatusColumnCategory';
import TableQualityStatusCategory from './TableQualityStatusCategory';

export default function TableQualityStatusOverview({
  firstLevelChecks,
  categoryDimension,
  severityType,
  tableDataQualityStatus,
  timeScale
}: ITableParameters) {
  const [extendedChecks, setExtendedChecks] = useState<
    Array<{ checkType: string; categoryDimension: string }>
  >([]);
  const dispatch = useActionDispatch();
  const history = useHistory();
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

  const openFirstLevelTableTab = (
    checkTypes: CheckTypes,
    connection: string,
    schema: string,
    table: string,
    timeScale?: 'daily' | 'monthly'
  ) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      timeScale ?? 'advanced'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      checkTypes,
      connection,
      schema,
      table
    );
    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value,
        state: {},
        label: table
      })
    );
    history.push(url);
  };

  const renderTooltipContent = (lastExecutedAt: any, severity: any) => {
    return (
      <div>
        <div className="flex justify-between w-80">
          <div>Last executed at:</div>
          <div>{lastExecutedAt}</div>
        </div>
        <div className="flex justify-between">
          <div>
            {severityType === 'current'
              ? 'Highest severity status:'
              : 'Current severity status:'}
          </div>
          <div>{severity}</div>
        </div>
      </div>
    );
  };

  const renderSecondLevelTooltip = (data: TFirstLevelCheck) => {
    return (
      <div>
        <div className="flex gap-x-2">
          <div className="w-42">Data quality check:</div>
          <div>{data.checkName}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-42">Last executed at:</div>
          <div>{moment(data.lastExecutedAt).format('YYYY-MM-DD HH:mm:ss')}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-42">Current severity level:</div>
          <div>{data.currentSeverity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-42">Highest historical severity level:</div>
          <div>{data.highestSeverity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-42">Category:</div>
          <div>{data.category}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-42">Quality Dimension:</div>
          <div>{data.qualityDimension}</div>
        </div>
      </div>
    );
  };

  return (
    <table className="border border-gray-150 mt-4">
      <thead>
        <th key="header_blank" className="p-4 border-b border-b-gray-150"></th>
        {Object.keys(firstLevelChecks).map((key) => (
          <th
            key={`header_${key}`}
            className={clsx(
              'p-4 border-b min-w-40 w-40 border-b-gray-150 font-bold cursor-pointer underline'
            )}
            onClick={() =>
              openFirstLevelTableTab(
                checkTypes,
                connection,
                schema,
                table,
                timeScale
              )
            }
          >
            {key}
          </th>
        ))}
      </thead>
      <tbody>
        <TableQualityStatusCategory
          tableDataQualityStatus={tableDataQualityStatus}
          severityType={severityType}
          extendedChecks={extendedChecks}
          firstLevelChecks={firstLevelChecks}
          setExtendedChecks={setExtendedChecks}
          categoryDimension={categoryDimension}
          renderSecondLevelTooltip={renderSecondLevelTooltip}
          renderTooltipContent={renderTooltipContent}
        />
        {Object.keys(tableDataQualityStatus.columns ?? {}).map((key, index) => (
          <TableQualityStatusColumnCategory
            key={key}
            customKey={key}
            index={index}
            tableDataQualityStatus={tableDataQualityStatus}
            severityType={severityType}
            extendedChecks={extendedChecks}
            firstLevelChecks={firstLevelChecks}
            setExtendedChecks={setExtendedChecks}
            categoryDimension={categoryDimension}
            renderSecondLevelTooltip={renderSecondLevelTooltip}
            renderTooltipContent={renderTooltipContent}
            timeScale = {timeScale}
          />
        ))}
      </tbody>
    </table>
  );
}
