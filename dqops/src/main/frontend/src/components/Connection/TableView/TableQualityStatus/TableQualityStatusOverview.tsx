import clsx from 'clsx';
import moment from 'moment';
import React, { useState } from 'react';
import TableQualityStatusCategory from './TableQualityStatusCategory';
import TableQualityStatusColumnCategory from './TableQualityStatusColumnCategory';
import {
  ITableParameters,
  TFirstLevelCheck
} from './TableQualityStatusConstans';

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
  // const dispatch = useActionDispatch();
  // const history = useHistory();
  // const {
  //   checkTypes,
  //   connection,
  //   schema,
  //   table
  // }: {
  //   checkTypes: CheckTypes;
  //   connection: string;
  //   schema: string;
  //   table: string;
  // } = useDecodedParams();

  // const openFirstLevelTableTab = (
  //   checkTypes: CheckTypes,
  //   connection: string,
  //   schema: string,
  //   table: string,
  //   timeScale?: 'daily' | 'monthly'
  // ) => {
  //   const url = ROUTES.TABLE_LEVEL_PAGE(
  //     checkTypes,
  //     connection,
  //     schema,
  //     table,
  //     timeScale ?? 'advanced'
  //   );
  //   const value = ROUTES.TABLE_LEVEL_VALUE(
  //     checkTypes,
  //     connection,
  //     schema,
  //     table
  //   );
  //   dispatch(
  //     addFirstLevelTab(checkTypes, {
  //       url,
  //       value,
  //       state: {},
  //       label: table
  //     })
  //   );
  //   history.push(url);
  // };

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
          <div className="w-49">Data quality check:</div>
          <div>{data.checkName}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Last executed at:</div>
          <div>{moment(data.lastExecutedAt).format('YYYY-MM-DD HH:mm:ss')}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Current severity level:</div>
          <div>
            {data.currentSeverity
              ? data.currentSeverity.replace(/_/g, ' ')
              : ''}
          </div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Highest historical severity level:</div>
          <div>
            {data.highestSeverity
              ? data.highestSeverity.replace(/_/g, ' ')
              : ''}
          </div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Category:</div>
          <div>{data.category}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Quality Dimension:</div>
          <div>{data.qualityDimension}</div>
        </div>
      </div>
    );
  };

  return (
    <table className="border border-gray-300 m-4">
      <thead>
        <th key="header_blank" className="p-4 border-b border-b-gray-300"></th>
        {Object.keys(firstLevelChecks).map((key) => (
          <th
            key={`header_${key}`}
            className={clsx(
              ' border-b border-gray-300 font-bold text-xs text-center'
            )}
            // onClick={() =>
            //   openFirstLevelTableTab(
            //     checkTypes,
            //     connection,
            //     schema,
            //     table,
            //     timeScale
            //   )
            // }
          >
            <div className=" ml-4">{key}</div>
          </th>
        ))}
      </thead>
      <tbody className="text-sm">
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
        {Object.keys(tableDataQualityStatus.columns ?? {})
          .sort()
          .map((key, index) => (
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
              timeScale={timeScale}
            />
          ))}
      </tbody>
    </table>
  );
}
