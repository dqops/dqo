import React, { useEffect, useState } from 'react';
import { CheckResultApi } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import {
  CheckCurrentDataQualityStatusModel,
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  ColumnCurrentDataQualityStatusModel,
  TableCurrentDataQualityStatusModel
} from '../../../api';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import RadioButton from '../../RadioButton';
import clsx from 'clsx';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import SvgIcon from '../../SvgIcon';
import DatePicker from '../../DatePicker';
import { Tooltip } from '@material-tailwind/react';
import moment from 'moment';

type TFirstLevelCheck = {
  checkName: string;
  currentSeverity?: CheckCurrentDataQualityStatusModelCurrentSeverityEnum;
  highestSeverity?: CheckCurrentDataQualityStatusModelCurrentSeverityEnum;
  lastExecutedAt?: number | string;
  checkType: string;
  category?: string;
  qualityDimension?: string;
};

interface IProps {
  timeScale?: 'daily' | 'monthly';
}

export default function TableQualityStatus({ timeScale }: IProps) {
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
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [tableDataQualityStatus, setTableDataQualityStatus] =
    useState<TableCurrentDataQualityStatusModel>({});
  const [firstLevelChecks, setFirstLevelChecks] = useState<
    Record<string, TFirstLevelCheck[]>
  >({});
  const [extendedChecks, setExtendedChecks] = useState<
    Array<{ checkType: string; categoryDimension: string }>
  >([]);
  const [categoryDimension, setCategoryDimension] = useState<
    'category' | 'dimension'
  >('category');
  const [severityType, setSeverityType] = useState<'current' | 'highest'>(
    'current'
  );
  const [month, setMonth] = useState<number | undefined>(1);
  const [since, setSince] = useState<number | undefined>();

  const getTableDataQualityStatus = (month?: number, since?: number) => {
    CheckResultApi.getTableDataQualityStatus(
      connection,
      schema,
      table,
      month,
      since,
      checkTypes === CheckTypes.PROFILING,
      checkTypes === CheckTypes.MONITORING,
      checkTypes === CheckTypes.PARTITIONED,
      timeScale
    ).then((res) => setTableDataQualityStatus(res.data));
  };

  useEffect(() => {
    getTableDataQualityStatus(month, since);
  }, [connection, schema, table, month, since, checkTypes, timeScale]);

  const onChangeFirstLevelChecks = () => {
    const data: Record<string, TFirstLevelCheck[]> = {};
    Object.keys(tableDataQualityStatus.checks ?? {}).flatMap((key) => {
      const categoryDimensionKey =
        categoryDimension === 'category'
          ? (tableDataQualityStatus.checks ?? {})[key]?.category
          : (tableDataQualityStatus.checks ?? {})[key]?.quality_dimension;
      if (categoryDimensionKey !== undefined) {
        if (Object.keys(data).find((x) => x === categoryDimensionKey)) {
          data[categoryDimensionKey].push({
            checkName: key,
            currentSeverity: (tableDataQualityStatus.checks ?? {})[key]
              ?.current_severity,
            highestSeverity: (tableDataQualityStatus.checks ?? {})[key]
              ?.highest_historical_severity,
            lastExecutedAt: (tableDataQualityStatus.checks ?? {})[key]?.last_executed_at,
            checkType: 'table',
            category: (tableDataQualityStatus.checks ?? {})[key]?.category,
            qualityDimension: (tableDataQualityStatus.checks ?? {})[key]
              ?.quality_dimension
          });
        } else {
          data[categoryDimensionKey] = [
            {
              checkName: key,
              currentSeverity: (tableDataQualityStatus.checks ?? {})[key]
                ?.current_severity,
              highestSeverity: (tableDataQualityStatus.checks ?? {})[key]
                ?.highest_historical_severity,
              lastExecutedAt: (tableDataQualityStatus.checks ?? {})[key]
                ?.last_executed_at,
              checkType: 'table',
              category: (tableDataQualityStatus.checks ?? {})[key]?.category,
              qualityDimension: (tableDataQualityStatus.checks ?? {})[key]
                ?.quality_dimension
            }
          ];
        }
      }
    });
    Object.keys(tableDataQualityStatus.columns ?? {}).forEach((column) =>
      Object.keys(
        (tableDataQualityStatus.columns ?? {})[column].checks ?? {}
      ).forEach((key) => {
        const categoryDimensionColumnKey =
          categoryDimension === 'category'
            ? ((tableDataQualityStatus.columns ?? {})[column].checks ?? {})[key]
                ?.category
            : ((tableDataQualityStatus.columns ?? {})[column].checks ?? {})[key]
                ?.quality_dimension;
        if (categoryDimensionColumnKey !== undefined) {
          if (Object.keys(data).find((x) => x === categoryDimensionColumnKey)) {
            data[categoryDimensionColumnKey].push({
              checkName: key,
              currentSeverity: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.current_severity,
              highestSeverity: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.highest_historical_severity,
              lastExecutedAt: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.last_executed_at,
              checkType: column,
              category: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.category,
              qualityDimension: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.quality_dimension
            });
          } else {
            data[categoryDimensionColumnKey] = [
              {
                checkName: key,
                currentSeverity: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.current_severity,
                highestSeverity: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.highest_historical_severity,
                lastExecutedAt: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.last_executed_at,
                checkType: column,
                category: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.category,
                qualityDimension: ((tableDataQualityStatus.columns ?? {})[
                  column
                ].checks ?? {})[key]?.quality_dimension
              }
            ];
          }
        }
      })
    );
    const sortedData: Record<string, TFirstLevelCheck[]> = {};
    const keys = Object.keys(data).sort();

    keys.forEach((key) => {
      sortedData[key] = data[key];
    });
    setFirstLevelChecks(sortedData);
  };

  const calculateSeverityColor = (
    severity: CheckCurrentDataQualityStatusModelCurrentSeverityEnum
  ) => {
    if (
      severity ===
      CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
    ) {
      return 'bg-gray-150';
    }
    if (
      severity === CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal
    ) {
      return 'bg-red-200';
    }
    if (
      severity === CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error
    ) {
      return 'bg-orange-200';
    } else if (
      severity === CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning
    ) {
      return 'bg-yellow-200';
    } else if (
      severity === CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  useEffect(() => {
    onChangeFirstLevelChecks();
  }, [categoryDimension, tableDataQualityStatus, severityType]);

  const colorCell = (checks: TFirstLevelCheck[]) => {
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-orange-200';
    } else if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-yellow-200';
    } else if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  const colorColumnCell = (
    column: ColumnCurrentDataQualityStatusModel,
    firstLevelCheck: string
  ) => {
    const checks: CheckCurrentDataQualityStatusModel[] = [];
    Object.values(column.checks ?? {}).forEach((check) => {
      if (
        categoryDimension === 'category'
          ? check.category === firstLevelCheck
          : check.quality_dimension === firstLevelCheck
      ) {
        checks.push(check);
      }
    });
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error
      )
    ) {
      return 'bg-orange-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning
      )
    ) {
      return 'bg-yellow-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid
      )
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  const colorColumnCellCircle = (
    column: ColumnCurrentDataQualityStatusModel,
    firstLevelCheck: string
  ) => {
    const checks: CheckCurrentDataQualityStatusModel[] = [];
    Object.values(column.checks ?? {}).forEach((check) => {
      if (
        categoryDimension === 'category'
          ? check.category === firstLevelCheck
          : check.quality_dimension === firstLevelCheck
      ) {
        checks.push(check);
      }
    });
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error
      )
    ) {
      return 'bg-orange-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning
      )
    ) {
      return 'bg-yellow-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.current_severity
            : x.highest_historical_severity) ===
          CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid
      )
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  const openFirstLevelColumnTab = (column: string) => {
    const url = ROUTES.COLUMN_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      column,
      'detail'
    );
    const value = ROUTES.COLUMN_LEVEL_VALUE(
      checkTypes,
      connection,
      schema,
      table,
      column
    );
    dispatch(
      addFirstLevelTab(CheckTypes.PROFILING, {
        url,
        value,
        state: {},
        label: table
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

  const backgroundStyle: React.CSSProperties = {
    background: `
        repeating-linear-gradient(
          45deg,
          #ffffff,
          #ffffff 5px,
          #cccccc 5px,
          #cccccc 10px
        ),
        repeating-linear-gradient(
          45deg,
          #cccccc,
          #cccccc 5px,
          #ffffff 5px,
          #ffffff 10px
        )`
  };

  const colorCircle = (checks: TFirstLevelCheck[]) => {
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-orange-200';
    } else if (
      checks.find(
        (x) =>
          (severityType === 'current'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-yellow-200';
    } else if (
      checks.find(
        (x) =>
          (severityType === 'highest'
            ? x.currentSeverity
            : x.highestSeverity) ===
            CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  const openFirstLevelTableTab = () => {
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

  return (
    <div className="p-4">
      <div className="flex justify-between items-center">
        <div className="flex pb-6 gap-x-5 items-center">
          <div>Group checks by: </div>
          <RadioButton
            checked={categoryDimension === 'category'}
            label="category"
            onClick={() => setCategoryDimension('category')}
          />
          <RadioButton
            checked={categoryDimension === 'dimension'}
            label="quality dimension"
            onClick={() => setCategoryDimension('dimension')}
          />
        </div>
        <div>
          <div className="flex pb-6 gap-x-5">
            <RadioButton
              checked={month === 1}
              label="Last month"
              onClick={() => {
                setSince(undefined), setMonth(1);
              }}
            />
            <RadioButton
              checked={month === 3}
              label="Last 3 months"
              onClick={() => {
                setSince(undefined), setMonth(3);
              }}
            />
            <RadioButton
              checked={month === undefined}
              label="Since"
              onClick={() => {
                setMonth(undefined);
              }}
            />
            <DatePicker
              showIcon
              placeholderText="Select date start"
              onChange={setSince}
              selected={since}
              dateFormat="yyyy-MM-dd"
            />
          </div>
          <div className="flex items-center gap-x-3 pb-6">
            <RadioButton
              label="Current severity status"
              checked={severityType === 'current'}
              onClick={() => setSeverityType('current')}
            />
            <RadioButton
              label="Highest severity status"
              checked={severityType === 'highest'}
              onClick={() => setSeverityType('highest')}
            />
          </div>
        </div>
      </div>

      <div className="flex gap-x-5">
        <SectionWrapper title="Current table status">
          <div className="flex gap-x-2">
            <div className="w-43">Status:</div>
            <div>{tableDataQualityStatus.current_severity}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-43">Last check executed at:</div>
            <div>
              {moment(tableDataQualityStatus.last_check_executed_at).format(
                'YYYY-MM-DD HH:mm:ss'
              )}
            </div>
          </div>
        </SectionWrapper>
        <SectionWrapper title="Total checks executed">
          <div className="flex gap-x-2">
            <div className="w-42">Total checks executed:</div>
            <div>{tableDataQualityStatus.executed_checks}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-42">Valid:</div>
            <div>{tableDataQualityStatus.valid_results}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-42">Warnings:</div>
            <div>{tableDataQualityStatus.warnings}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-42">Errors:</div>
            <div>{tableDataQualityStatus.errors}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-42">Fatals:</div>
            <div>{tableDataQualityStatus.fatals}</div>
          </div>
          <div className="flex gap-x-2">
            <div className="w-42">Execution errors:</div>
            <div>{tableDataQualityStatus.execution_errors}</div>
          </div>
        </SectionWrapper>
      </div>
      {Object.keys(firstLevelChecks).length > 0 ? (
        <table className="border border-gray-150 mt-4">
          <thead>
            <th
              key="header_blank"
              className="p-4 border-b border-b-gray-150"
            ></th>
            {Object.keys(firstLevelChecks).map((key) => (
              <th
                key={`header_${key}`}
                className={clsx(
                  'p-4 border-b min-w-40 w-40 border-b-gray-150 font-bold cursor-pointer underline'
                )}
                onClick={() => openFirstLevelTableTab()}
              >
                {key}
              </th>
            ))}
          </thead>
          <tbody>
            <tr key="row_table_level_checks" className="">
              <td
                key="cell_table_level_checks_title"
                className="font-bold px-4 "
              >
                Table level checks
              </td>
              {Object.keys(firstLevelChecks).map((key) => (
                <td key={`cell_table_level_checks_${key}`} className=" h-full ">
                  <div className="h-full flex w-40 items-center ">
                    {colorCell(firstLevelChecks[key]) !== '' ? (
                      <div
                        onClick={() => {
                          toggleExtendedChecks(key, 'table');
                        }}
                      >
                        <SvgIcon
                          key={`svg_table_level_checks_${key}`}
                          name={
                            extendedChecks.find(
                              (x) =>
                                x.checkType === key &&
                                x.categoryDimension === 'table'
                            )
                              ? 'chevron-down'
                              : 'chevron-right'
                          }
                          className="h-5 w-5 pr-1"
                        />
                      </div>
                    ) : null}
                    {colorCell(firstLevelChecks[key]) !== '' ? (
                      <div
                        className={clsx(
                          'w-43 h-12 flex ',
                          colorCell(firstLevelChecks[key]),
                          severityType === 'current' ? '' : 'justify-end'
                        )}
                        style={{
                          ...(colorCell(firstLevelChecks[key]) === 'bg-gray-150'
                            ? backgroundStyle
                            : {})
                        }}
                      >
                        <div
                          className={clsx(
                            ' h-3 w-3 mr-2 mt-2 ml-2',
                            colorCircle(firstLevelChecks[key])
                          )}
                          style={{
                            borderRadius: '6px',
                            ...(colorCircle(firstLevelChecks[key]) ===
                            'bg-gray-150'
                              ? backgroundStyle
                              : {})
                          }}
                        ></div>
                      </div>
                    ) : null}
                  </div>
                </td>
              ))}
            </tr>
            <tr
              key="row_table_level_checks_blank"
              className="border-b border-b-gray-150"
            >
              <td
                key="cell_table_level_checks_blank"
                className="font-bold px-4 "
              ></td>
              {Object.keys(firstLevelChecks).map((key) => (
                <td
                  valign="baseline"
                  key={`cell_table_level_checks_blank_${key}`}
                >
                  {extendedChecks.find(
                    (x) =>
                      x.checkType === key && x.categoryDimension === 'table'
                  ) && (
                    <div className="w-40">
                      {(firstLevelChecks[key] ?? []).map((x, index) =>
                        x.checkType === 'table' ? (
                          <Tooltip
                            key={`table_check_${key}_${index}`}
                            content={
                              <div>
                                <div className="flex gap-x-2">
                                  <div className="w-42">Last executed at:</div>
                                  <div>
                                    {moment(x.lastExecutedAt).format(
                                      'YYYY-MM-DD HH:mm:ss'
                                    )}
                                  </div>
                                </div>
                                <div className="flex gap-x-2">
                                  <div className="w-42">
                                    Current severity level:
                                  </div>
                                  <div>{x.currentSeverity}</div>
                                </div>
                                <div className="flex gap-x-2">
                                  <div className="w-42">
                                    Highest historical severity level:
                                  </div>
                                  <div>{x.highestSeverity}</div>
                                </div>
                                <div className="flex gap-x-2">
                                  <div className="w-42">Category:</div>
                                  <div>{x.category}</div>
                                </div>
                                <div className="flex gap-x-2">
                                  <div className="w-42">Quality Dimension:</div>
                                  <div>{x.qualityDimension}</div>
                                </div>
                              </div>
                            }
                          >
                            <div
                              className={clsx(
                                'cursor-auto h-12 ml-5 p-2',
                                calculateSeverityColor(
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
                                ...(calculateSeverityColor(
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
            {Object.keys(tableDataQualityStatus.columns ?? {}).map(
              (key, index) => (
                <React.Fragment key={`column_${key}`}>
                  <tr
                    key={`column_row_${key}`}
                    className={clsx(
                      index !==
                        Object.keys(tableDataQualityStatus.columns ?? {})
                          .length -
                          1 && 'my-2'
                    )}
                  >
                    <td
                      key={`column_cell_${key}`}
                      className="p-2 px-4 underline cursor-pointer font-bold"
                      onClick={() => openFirstLevelColumnTab(key)}
                    >
                      {key}
                    </td>
                    {Object.keys(firstLevelChecks).map(
                      (firstLevelChecksKey) => (
                        <td
                          key={`cell_column_${key}_${firstLevelChecksKey}`}
                          className=" h-full"
                        >
                          {' '}
                          {colorColumnCell(
                            (tableDataQualityStatus.columns ?? {})[key],
                            firstLevelChecksKey
                          ) !== '' ? (
                            <div className="h-full flex w-40 items-center ">
                              <div
                                onClick={() => {
                                  toggleExtendedChecks(
                                    key,
                                    firstLevelChecksKey
                                  );
                                }}
                              >
                                <SvgIcon
                                  key={`svg_column_${key}_${firstLevelChecksKey}`}
                                  name={
                                    extendedChecks.find(
                                      (x) =>
                                        x.checkType === key &&
                                        x.categoryDimension ===
                                          firstLevelChecksKey
                                    )
                                      ? 'chevron-down'
                                      : 'chevron-right'
                                  }
                                  className="h-5 w-5 pr-1"
                                />
                              </div>
                              <div
                                className={clsx(
                                  'h-12 w-43 flex',
                                  // 'border border-gray-150',
                                  colorColumnCell(
                                    (tableDataQualityStatus.columns ?? {})[key],
                                    firstLevelChecksKey
                                  ),
                                  severityType === 'current'
                                    ? ''
                                    : 'justify-end'
                                )}
                                style={{
                                  ...(colorColumnCell(
                                    (tableDataQualityStatus.columns ?? {})[key],
                                    firstLevelChecksKey
                                  ) === 'bg-gray-150'
                                    ? backgroundStyle
                                    : {})
                                }}
                              >
                                {' '}
                                <div
                                  className="h-3 w-3 ml-2 mt-2 mr-2"
                                  style={{
                                    borderRadius: '6px',
                                    ...(colorColumnCellCircle(
                                      (tableDataQualityStatus.columns ?? {})[
                                        key
                                      ],
                                      firstLevelChecksKey
                                    ) === 'bg-gray-150'
                                      ? backgroundStyle
                                      : {})
                                  }}
                                ></div>
                              </div>
                            </div>
                          ) : null}
                        </td>
                      )
                    )}
                  </tr>
                  <tr key={`column_row_blank_${key}`}>
                    <td
                      key={`column_cell_blank_${key}`}
                      className="font-bold px-4 "
                    ></td>
                    {Object.keys(firstLevelChecks).map((check) => (
                      <td
                        key={`cell_column_blank_${key}_${check}`}
                        valign="baseline"
                      >
                        {extendedChecks.find(
                          (x) =>
                            x.checkType === key && x.categoryDimension === check
                        ) ? (
                          <div className="w-40">
                            {(firstLevelChecks[check] ?? []).map((x, index) =>
                              x.checkType === key ? (
                                <Tooltip
                                  key={`column_check_${key}_${check}_${index}`}
                                  content={
                                    <div>
                                      <div className="flex gap-x-2">
                                        <div className="w-42">Last executed at:</div>
                                        <div>
                                          {moment(x.lastExecutedAt).format(
                                            'YYYY-MM-DD HH:mm:ss'
                                          )}
                                        </div>
                                      </div>
                                      <div className="flex gap-x-2">
                                        <div className="w-42">
                                          Current severity level:
                                        </div>
                                        <div>{x.currentSeverity}</div>
                                      </div>
                                      <div className="flex gap-x-2">
                                        <div className="w-42">
                                          Highest historical severity level:
                                        </div>
                                        <div>{x.highestSeverity}</div>
                                      </div>
                                      <div className="flex gap-x-2">
                                        <div className="w-42">Category:</div>
                                        <div>{x.category}</div>
                                      </div>
                                      <div className="flex gap-x-2">
                                        <div className="w-42">
                                          Quality Dimension:
                                        </div>
                                        <div>{x.qualityDimension}</div>
                                      </div>
                                    </div>
                                  }
                                >
                                  <div
                                    className={clsx(
                                      'cursor-auto h-12 p-2 ml-5',
                                      calculateSeverityColor(
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
                                      ...(calculateSeverityColor(
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
              )
            )}
          </tbody>
        </table>
      ) : (
        <div className="mt-5">No data quality check results</div>
      )}
    </div>
  );
}