import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React from 'react';
import SvgIcon from '../../../SvgIcon';
import {
  TFirstLevelCheck,
  backgroundStyle
} from './TableQualityStatusConstans';
import {
  getColor,
  getColumnStatus,
  getColumnCircleStatus
} from './TableQualityStatusUtils';
import { useHistory, useParams } from 'react-router-dom';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { TableCurrentDataQualityStatusModel } from '../../../../api';

interface ITableQualityStatusColumnCategoryProps {
  customKey: string;
  index: number;
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
  severityType: 'current' | 'highest';
  categoryDimension: 'category' | 'dimension';
  extendedChecks: Array<{ checkType: string; categoryDimension: string }>;
  setExtendedChecks: any;
  firstLevelChecks: Record<string, TFirstLevelCheck[]>;
  renderSecondLevelTooltip: (data: TFirstLevelCheck) => React.JSX.Element;
  renderTooltipContent: (
    lastExecutedAt: any,
    severity: any,
    severityType: 'current' | 'highest'
  ) => React.JSX.Element;
  timeScale: "daily" | "monthly" | undefined
}

export default function TableQualityStatusColumnCategory({
  customKey,
  index,
  tableDataQualityStatus,
  severityType,
  categoryDimension,
  extendedChecks,
  firstLevelChecks,
  setExtendedChecks,
  renderSecondLevelTooltip,
  renderTooltipContent,
  timeScale
}: ITableQualityStatusColumnCategoryProps) {
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

  const openFirstLevelColumnTab = (
    checkTypes: CheckTypes,
    connection: string,
    schema: string,
    table: string,
    column: string
  ) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      column,
      checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale ?? 'daily'
    );
    const value = ROUTES.COLUMN_LEVEL_VALUE(
      checkTypes,
      connection,
      schema,
      table,
      column
    );
    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value,
        state: {},
        label: column
      })
    );
    history.push(url);
  };

  const toggleExtendedChecks = (
    checkType: string,
    categoryDimension: string
  ) => {
    const array = [...extendedChecks];
    if (
      array.find(
        (item) =>
          item.checkType === checkType &&
          item.categoryDimension === categoryDimension
      )
    ) {
      const filteredArray = array.filter(
        (item) =>
          !(
            item.checkType === checkType &&
            item.categoryDimension === categoryDimension
          )
      );
      setExtendedChecks(filteredArray);
    } else {
      array.push({ checkType, categoryDimension });
      setExtendedChecks(array);
    }
  };

  return (
    <React.Fragment key={`column_${customKey}`}>
      <tr
        key={`column_row_${customKey}`}
      >
        <td
          key={`column_cell_${customKey}`}
          className="p-2 px-4 underline cursor-pointer font-bold"
          onClick={() =>
            openFirstLevelColumnTab(
              checkTypes,
              connection,
              schema,
              table,
              customKey
            )
          }
        >
          {customKey}
        </td>
        {Object.keys(firstLevelChecks).map((firstLevelChecksKey) => (
          <td
            key={`cell_column_${customKey}_${firstLevelChecksKey}`}
            className=" h-full"
            onClick={() => {
              toggleExtendedChecks(customKey, firstLevelChecksKey);
            }}
          >
            {' '}
            {getColor(
              getColumnStatus(
                severityType,
                categoryDimension,
                (tableDataQualityStatus.columns ?? {})[customKey],
                firstLevelChecksKey
              ).status
            ) !== '' ? (
              <div className="h-full flex w-40 items-center ">
                <div>
                  <SvgIcon
                    key={`svg_column_${customKey}_${firstLevelChecksKey}`}
                    name={
                      extendedChecks.find(
                        (x) =>
                          x.checkType === customKey &&
                          x.categoryDimension === firstLevelChecksKey
                      )
                        ? 'chevron-down'
                        : 'chevron-right'
                    }
                    className="h-5 w-5 pr-1"
                  />
                </div>
                <div
                  className={clsx(
                    'h-8 w-43 flex',
                    getColor(
                      getColumnStatus(
                        severityType,
                        categoryDimension,
                        (tableDataQualityStatus.columns ?? {})[customKey],
                        firstLevelChecksKey
                      ).status
                    ),
                    severityType === 'current' ? '' : 'justify-end'
                  )}
                  style={{
                    ...(getColor(
                      getColumnStatus(
                        severityType,
                        categoryDimension,
                        (tableDataQualityStatus.columns ?? {})[customKey],
                        firstLevelChecksKey
                      ).status
                    ) === 'bg-gray-150'
                      ? backgroundStyle
                      : {})
                  }}
                >
                  {getColumnCircleStatus(
                    severityType,
                    categoryDimension,
                    (tableDataQualityStatus.columns ?? {})[customKey],
                    firstLevelChecksKey
                  ).lastExecutedAt ? (
                    <Tooltip
                      content={renderTooltipContent(
                        moment(
                          getColumnCircleStatus(
                            severityType,
                            categoryDimension,
                            (tableDataQualityStatus.columns ?? {})[customKey],
                            firstLevelChecksKey
                          ).lastExecutedAt
                        ).format('YYYY-MM-DD HH:mm:ss'),
                        getColumnCircleStatus(
                          severityType,
                          categoryDimension,
                          (tableDataQualityStatus.columns ?? {})[customKey],
                          firstLevelChecksKey
                        ).status,
                        severityType
                      )}
                    >
                      <div
                        className="h-4 w-4 ml-2 mt-1 mr-2"
                        style={{
                          borderRadius: '6px',
                          ...(getColor(
                            getColumnCircleStatus(
                              severityType,
                              categoryDimension,
                              (tableDataQualityStatus.columns ?? {})[customKey],
                              firstLevelChecksKey
                            ).status
                          ) === 'bg-gray-150'
                            ? backgroundStyle
                            : {})
                        }}
                      ></div>
                    </Tooltip>
                  ) : null}
                </div>
              </div>
            ) : null}
          </td>
        ))}
      </tr>
      <tr key={`column_row_blank_${customKey}`}>
        <td
          key={`column_cell_blank_${customKey}`}
          className="font-bold px-4 "
        ></td>
        {Object.keys(firstLevelChecks).map((check) => (
          <td key={`cell_column_blank_${customKey}_${check}`} valign="baseline">
            {extendedChecks.find(
              (x) => x.checkType === customKey && x.categoryDimension === check
            ) ? (
              <div className="w-40">
                {(firstLevelChecks[check] ?? []).map((x, index) =>
                  x.checkType === customKey &&
                  getColor(
                    severityType === 'current'
                      ? x.currentSeverity
                      : x.highestSeverity
                  ) !== '' ? (
                    <Tooltip
                      key={`column_check_${customKey}_${check}_${index}`}
                      content={renderSecondLevelTooltip(x)}
                    >
                      <div
                        className={clsx(
                          'cursor-auto h-12 p-2 ml-5',
                          getColor(
                            severityType === 'current'
                              ? x.currentSeverity
                              : x.highestSeverity
                          )
                        )}
                        style={{
                          fontSize: '12px',
                          whiteSpace: 'normal',
                          wordBreak: 'break-word',
                          ...(getColor(
                            severityType === 'current'
                              ? x.currentSeverity
                              : x.highestSeverity
                          ) === 'bg-gray-150'
                            ? backgroundStyle
                            : {})
                        }}
                      >
                        {x.checkName}
                      </div>
                    </Tooltip>
                  ) : (
                    ''
                  )
                )}
              </div>
            ) : null}
          </td>
        ))}
      </tr>
    </React.Fragment>
  );
}
