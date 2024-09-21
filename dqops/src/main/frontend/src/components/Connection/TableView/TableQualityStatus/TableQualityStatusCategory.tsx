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
  backgroundStyle,
  secondBackgroundStyle,
  TFirstLevelCheck
} from './TableQualityStatusConstans';
import {
  getColor,
  getSecondColor,
  getTableCircleStatus,
  getTableStatus
} from './TableQualityStatusUtils';

interface IExtendedCheck {
  checkType: string;
  categoryDimension: string;
}

interface ITableQualityStatusColumnCategoryProps {
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
  severityType: 'current' | 'highest';
  categoryDimension: 'category' | 'dimension';
  extendedChecks: IExtendedCheck[];
  setExtendedChecks: React.Dispatch<React.SetStateAction<IExtendedCheck[]>>;
  firstLevelChecks: Record<string, TFirstLevelCheck[]>;
  renderSecondLevelTooltip: (data: TFirstLevelCheck) => React.JSX.Element;
  renderTooltipContent: (
    lastExecutedAt: string,
    severity: CheckCurrentDataQualityStatusModelCurrentSeverityEnum | null,
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
    setExtendedChecks((prevChecks) => {
      const isAlreadyExtended = prevChecks.some(
        (check) =>
          check.checkType === checkType &&
          check.categoryDimension === categoryDimension
      );

      if (isAlreadyExtended) {
        return prevChecks.filter(
          (check) =>
            !(
              check.checkType === checkType &&
              check.categoryDimension === categoryDimension
            )
        );
      } else {
        return [...prevChecks, { checkType, categoryDimension }];
      }
    });
  };

  const renderTableLevelCheckCells = () => {
    return Object.keys(firstLevelChecks).map((key) => {
      const tableStatus = getTableStatus(severityType, firstLevelChecks[key]);
      const tableCircleStatus = getTableCircleStatus(
        severityType,
        firstLevelChecks[key]
      );

      const showTooltip = tableCircleStatus.lastExecutedAt !== null;
      const showIcon = tableStatus.status !== null;

      return (
        <td
          key={`cell_table_level_checks_${key}`}
          className="h-full"
          style={{ padding: '0.5px', paddingBottom: 0, margin: '0.5px' }}
        >
          <div
            className="h-full flex w-29 items-center justify-end relative"
            onClick={() =>
              tableStatus.status && toggleExtendedChecks(key, 'table')
            }
          >
            <div className="w-5 h-full"></div>
            {tableStatus.status && (
              <div
                className={clsx(
                  'w-32 h-8 flex  justify-end  cursor-pointer',
                  getColor(tableStatus.status),
                  severityType === 'current' ? '' : 'justify-end'
                )}
                style={
                  getColor(tableStatus.status) === 'bg-gray-150'
                    ? backgroundStyle
                    : {}
                }
              >
                {showIcon && (
                  <div className="absolute top-1 left-4.5">
                    <SvgIcon
                      name={
                        extendedChecks.some(
                          (x) =>
                            x.checkType === key &&
                            x.categoryDimension === 'table'
                        )
                          ? 'chevron-up'
                          : 'chevron-down'
                      }
                      className={clsx(
                        'h-5 w-5 pr-1',
                        extendedChecks.some(
                          (x) =>
                            x.checkType === key &&
                            x.categoryDimension === 'table'
                        )
                          ? 'mb-1'
                          : 'mt-1'
                      )}
                    />
                  </div>
                )}
                {showTooltip && (
                  <Tooltip
                    content={renderTooltipContent(
                      moment(tableCircleStatus.lastExecutedAt).format(
                        'YYYY-MM-DD HH:mm:ss'
                      ),
                      tableCircleStatus.status,
                      severityType
                    )}
                  >
                    <div
                      className={clsx(
                        'h-4 w-4 ml-0.5 mt-2 mr-2 ',
                        getColor(tableCircleStatus.status)
                      )}
                      style={{
                        borderRadius: '6px',
                        ...(getColor(tableCircleStatus.status) === 'bg-gray-150'
                          ? backgroundStyle
                          : {})
                      }}
                    ></div>
                  </Tooltip>
                )}
              </div>
            )}
          </div>
        </td>
      );
    });
  };

  const renderExtendedChecks = () => {
    return Object.keys(firstLevelChecks).map((key) => {
      const isExtended = extendedChecks.some(
        (x) => x.checkType === key && x.categoryDimension === 'table'
      );

      if (!isExtended)
        return (
          <td
            key={`cell_table_level_checks_blank_${key}`}
            style={{ padding: 0, paddingBottom: '0.5px', margin: 0 }}
          ></td>
        );

      return (
        <td
          valign="baseline"
          key={`cell_table_level_checks_blank_${key}`}
          style={{ padding: 0, paddingBottom: '0.5px', margin: 0 }}
        >
          <div className="w-[200px] pr-[1px]">
            {(firstLevelChecks[key] ?? []).map((x, index) => {
              if (x.checkType !== 'table') return null;

              const severity =
                severityType === 'current'
                  ? x.currentSeverity
                  : x.highestSeverity;

              return (
                <Tooltip
                  key={`table_check_${key}_${index}`}
                  content={renderSecondLevelTooltip(x)}
                >
                  <div
                    className={clsx(
                      'cursor-auto h-5 ml-[16.3px] px-1 truncate',
                      getSecondColor(
                        severity ??
                          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                      )
                    )}
                    style={{
                      fontSize: '11px',
                      ...(getSecondColor(severity) === 'bg-gray-150'
                        ? secondBackgroundStyle
                        : {})
                    }}
                  >
                    {x.checkName}
                  </div>
                </Tooltip>
              );
            })}
          </div>
        </td>
      );
    });
  };

  return (
    <>
      <tr key="row_table_level_checks" style={{ margin: 0 }}>
        <td
          key="cell_table_level_checks_title"
          className="font-bold px-4 whitespace-nowrap text-xs"
        >
          Table level checks
        </td>
        {renderTableLevelCheckCells()}
      </tr>
      <tr key="row_table_level_checks_blank" style={{ margin: 0 }}>
        <td
          key="cell_table_level_checks_blank"
          className="font-bold px-4"
          style={{ padding: 0, margin: 0 }}
        ></td>
        {renderExtendedChecks()}
      </tr>
      <tr
        className="bg-white border-b border-gray-100"
        style={{ height: '1px' }}
      >
        <td colSpan={Object.keys(firstLevelChecks).length + 1}></td>
      </tr>
    </>
  );
}
