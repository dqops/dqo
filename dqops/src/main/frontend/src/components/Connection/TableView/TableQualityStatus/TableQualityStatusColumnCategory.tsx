import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React from 'react';
import { useHistory } from 'react-router-dom';
import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableCurrentDataQualityStatusModel
} from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import SvgIcon from '../../../SvgIcon';
import {
  TFirstLevelCheck,
  backgroundStyle,
  secondBackgroundStyle
} from './TableQualityStatusConstans';
import {
  getColor,
  getColumnCircleStatus,
  getColumnStatus,
  getSecondColor
} from './TableQualityStatusUtils';

interface IExtendedCheck {
  checkType: string;
  categoryDimension: string;
}

interface ITableQualityStatusColumnCategoryProps {
  customKey: string;
  index: number;
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
  timeScale: 'daily' | 'monthly' | undefined;
}

export default function TableQualityStatusColumnCategory({
  customKey,
  tableDataQualityStatus,
  severityType,
  categoryDimension,
  extendedChecks,
  setExtendedChecks,
  firstLevelChecks,
  renderSecondLevelTooltip,
  renderTooltipContent,
  timeScale
}: ITableQualityStatusColumnCategoryProps) {
  const dispatch = useActionDispatch();
  const history = useHistory();
  const { checkTypes, connection, schema, table } = useDecodedParams();

  const openFirstLevelColumnTab = (column: string) => {
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

  const renderColumnCells = () => {
    return Object.keys(firstLevelChecks).map((firstLevelChecksKey) => {
      const columnStatus = getColumnStatus(
        severityType,
        categoryDimension,
        (tableDataQualityStatus.columns ?? {})[customKey],
        firstLevelChecksKey
      );

      const columnCircleStatus = getColumnCircleStatus(
        severityType,
        categoryDimension,
        (tableDataQualityStatus.columns ?? {})[customKey],
        firstLevelChecksKey
      );

      const showTooltip = columnCircleStatus.lastExecutedAt !== null;
      const showIcon = columnStatus.status !== null;

      return (
        <td
          key={`cell_column_${customKey}_${firstLevelChecksKey}`}
          className="h-full"
          onClick={() =>
            columnStatus.status &&
            toggleExtendedChecks(customKey, firstLevelChecksKey)
          }
          style={{ padding: 0, margin: 0 }}
        >
          {columnStatus.status && (
            <div
              className={clsx(
                'h-full flex w-29 items-center'
                // isExtended && 'w-50'
              )}
            >
              <div className="w-5 h-full"></div>
              <div
                className={clsx(
                  'h-8 w-32 flex justify-end  cursor-pointer relative',
                  getColor(columnStatus.status),
                  severityType === 'current' ? '' : 'justify-end'
                  // isExtended && 'w-50'
                )}
                style={
                  getColor(columnStatus.status) === 'bg-gray-150'
                    ? backgroundStyle
                    : {}
                }
              >
                {showIcon && (
                  <div className="absolute top-1 left-1">
                    <SvgIcon
                      name={
                        extendedChecks.some(
                          (x) =>
                            x.checkType === customKey &&
                            x.categoryDimension === firstLevelChecksKey
                        )
                          ? 'chevron-up'
                          : 'chevron-down'
                      }
                      className={clsx(
                        'h-5 w-5 pr-1',
                        extendedChecks.some(
                          (x) =>
                            x.checkType === customKey &&
                            x.categoryDimension === firstLevelChecksKey
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
                      moment(columnCircleStatus.lastExecutedAt).format(
                        'YYYY-MM-DD HH:mm:ss'
                      ),
                      columnCircleStatus.status,
                      severityType
                    )}
                  >
                    <div
                      className={clsx(
                        'h-4 w-4 mr-2 mt-2 ml-0.5',
                        getColor(columnCircleStatus.status)
                      )}
                      style={{
                        borderRadius: '6px',
                        ...(getColor(columnCircleStatus.status) ===
                        'bg-gray-150'
                          ? backgroundStyle
                          : {})
                      }}
                    ></div>
                  </Tooltip>
                )}
              </div>
            </div>
          )}
        </td>
      );
    });
  };

  const renderExtendedChecks = () => {
    return Object.keys(firstLevelChecks).map((check) => {
      const isExtended = extendedChecks.some(
        (x) => x.checkType === customKey && x.categoryDimension === check
      );

      if (!isExtended)
        return (
          <td
            key={`cell_column_blank_${customKey}_${check}`}
            style={{ padding: 0, margin: 0 }}
          ></td>
        );

      return (
        <td
          key={`cell_column_blank_${customKey}_${check}`}
          valign="baseline"
          style={{ padding: 0, margin: 0 }}
        >
          <div className="w-50">
            {(firstLevelChecks[check] ?? []).map((x, index) => {
              if (x.checkType !== customKey) return null;

              const severity =
                severityType === 'current'
                  ? x.currentSeverity
                  : x.highestSeverity;

              return (
                <Tooltip
                  key={`column_check_${customKey}_${check}_${index}`}
                  content={renderSecondLevelTooltip(x)}
                >
                  <div
                    className={clsx(
                      'cursor-auto h-5 px-1 ml-[15.5px] truncate',
                      getSecondColor(
                        severity ??
                          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
                      )
                    )}
                    style={{
                      fontSize: '11px',
                      whiteSpace: 'normal',
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
    <React.Fragment key={`column_${customKey}`}>
      <tr key={`column_row_${customKey}`} style={{ padding: 0, margin: 0 }}>
        <td
          key={`column_cell_${customKey}`}
          className="p-2 px-4 underline cursor-pointer text-xs"
          onClick={() => openFirstLevelColumnTab(customKey)}
        >
          {customKey}
        </td>
        {renderColumnCells()}
      </tr>
      <tr
        key={`column_row_blank_${customKey}`}
        style={{ padding: 0, margin: 0 }}
      >
        <td
          key={`column_cell_blank_${customKey}`}
          className="font-bold px-4"
          style={{ padding: 0, margin: 0 }}
        ></td>
        {renderExtendedChecks()}
      </tr>
      <tr
        key={`separator_${customKey}`}
        className="bg-white"
        style={{ height: '1px' }}
      >
        <td colSpan={Object.keys(firstLevelChecks).length + 1}></td>
      </tr>
    </React.Fragment>
  );
}
