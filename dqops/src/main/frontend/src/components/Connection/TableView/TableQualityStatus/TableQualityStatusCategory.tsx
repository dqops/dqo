import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React from 'react';
import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableCurrentDataQualityStatusModel
} from '../../../../api';
import SvgIcon from '../../../SvgIcon';
import {
  TFirstLevelCheck,
  backgroundStyle
} from './TableQualityStatusConstans';
import {
  getColor,
  getTableCircleStatus,
  getTableStatus
} from './TableQualityStatusUtils';

interface ITableQualityStatusColumnCategoryProps {
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
}

export default function TableQualityStatusCategory({
  severityType,
  extendedChecks,
  firstLevelChecks,
  setExtendedChecks,
  renderSecondLevelTooltip,
  renderTooltipContent
}: ITableQualityStatusColumnCategoryProps) {
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
    <React.Fragment>
      <tr key="row_table_level_checks">
        <td key="cell_table_level_checks_title" className="font-bold px-4">
          Table level checks
        </td>
        {Object.keys(firstLevelChecks).map((key) => (
          <td key={`cell_table_level_checks_${key}`} className="h-full">
            <div
              className="h-full flex w-40 items-center"
              onClick={() => {
                toggleExtendedChecks(key, 'table');
              }}
            >
              <div className="w-5 h-full"></div>
              {getColor(
                getTableStatus(severityType, firstLevelChecks[key]).status
              ) !== '' ? (
                <div
                  className={clsx(
                    'w-43 h-8 flex justify-end',
                    getColor(
                      getTableStatus(severityType, firstLevelChecks[key]).status
                    ),
                    severityType === 'current' ? '' : 'justify-end'
                  )}
                  style={{
                    ...(getColor(
                      getTableStatus(severityType, firstLevelChecks[key]).status
                    ) === 'bg-gray-150'
                      ? backgroundStyle
                      : {})
                  }}
                >
                  {getTableCircleStatus(severityType, firstLevelChecks[key])
                    .lastExecutedAt ? (
                    <Tooltip
                      content={renderTooltipContent(
                        moment(
                          getTableCircleStatus(
                            severityType,
                            firstLevelChecks[key]
                          ).lastExecutedAt
                        ).format('YYYY-MM-DD HH:mm:ss'),
                        getTableCircleStatus(
                          severityType,
                          firstLevelChecks[key]
                        ).status,
                        severityType
                      )}
                    >
                      <div
                        className={clsx(
                          'h-4 w-4 mr-0.5 mt-2 ml-2',
                          getColor(
                            getTableCircleStatus(
                              severityType,
                              firstLevelChecks[key]
                            ).status
                          )
                        )}
                        style={{
                          borderRadius: '6px',
                          ...(getColor(
                            getTableCircleStatus(
                              severityType,
                              firstLevelChecks[key]
                            ).status
                          ) === 'bg-gray-150'
                            ? backgroundStyle
                            : {})
                        }}
                      ></div>
                    </Tooltip>
                  ) : null}
                  {getColor(
                    getTableStatus(severityType, firstLevelChecks[key]).status
                  ) !== '' ? (
                    <div className="flex justify-center items-center">
                      <SvgIcon
                        key={`svg_table_level_checks_${key}`}
                        name={
                          extendedChecks.find(
                            (x) =>
                              x.checkType === key &&
                              x.categoryDimension === 'table'
                          )
                            ? 'chevron-up'
                            : 'chevron-down'
                        }
                        className={clsx(
                          'h-5 w-5 pr-1',
                          extendedChecks.find(
                            (x) =>
                              x.checkType === key &&
                              x.categoryDimension === 'table'
                          )
                            ? 'mb-1'
                            : 'mt-1'
                        )}
                      />
                    </div>
                  ) : null}
                </div>
              ) : null}
            </div>
          </td>
        ))}
      </tr>
      <tr key="row_table_level_checks_blank" style={{ maxHeight: '0.5px' }}>
        <td key="cell_table_level_checks_blank" className="font-bold px-4"></td>
        {Object.keys(firstLevelChecks).map((key) => (
          <td valign="baseline" key={`cell_table_level_checks_blank_${key}`}>
            {extendedChecks.find(
              (x) => x.checkType === key && x.categoryDimension === 'table'
            ) && (
              <div className="w-40">
                {(firstLevelChecks[key] ?? []).map((x, index) =>
                  x.checkType === 'table' ? (
                    <Tooltip
                      key={`table_check_${key}_${index}`}
                      content={renderSecondLevelTooltip(x)}
                    >
                      <div
                        className={clsx(
                          'cursor-auto h-12 ml-[16.7px] p-2',
                          getColor(
                            severityType === 'current'
                              ? x.currentSeverity ??
                                  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                              : x.highestSeverity ??
                                  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                          )
                        )}
                        style={{
                          fontSize: '12px',
                          whiteSpace: 'normal',
                          wordBreak: 'break-word',
                          ...(getColor(
                            severityType === 'current'
                              ? x.currentSeverity ??
                                  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                              : x.highestSeverity ??
                                  CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                          ) === 'bg-gray-150'
                            ? backgroundStyle
                            : {})
                        }}
                      >
                        {x.checkName}
                      </div>
                    </Tooltip>
                  ) : null
                )}
              </div>
            )}
          </td>
        ))}
      </tr>
    </React.Fragment>
  );
}
