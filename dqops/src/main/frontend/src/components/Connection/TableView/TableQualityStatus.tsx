import React, { useEffect, useState } from 'react';
import { CheckResultApi } from '../../../services/apiClient';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import {
  CheckCurrentDataQualityStatusModel,
  CheckCurrentDataQualityStatusModelSeverityEnum,
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
  severity?: CheckCurrentDataQualityStatusModelSeverityEnum;
  executedAt?: number | string;
  checkType: string;
  category?: string;
  qualityDimension?: string;
};

export default function TableQualityStatus() {
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
  const [month, setMonth] = useState<number | undefined>(1);
  const [since, setSince] = useState<number | undefined>();

  const getTableDataQualityStatus = (month?: number, since?: number) => {
    CheckResultApi.getTableDataQualityStatus(
      connection,
      schema,
      table,
      month,
      since,
      checkTypes === CheckTypes.PROFILING ? true : undefined,
      checkTypes === CheckTypes.MONITORING ? true : undefined,
      checkTypes === CheckTypes.PARTITIONED ? true : undefined
    ).then((res) => setTableDataQualityStatus(res.data));
  };

  useEffect(() => {
    getTableDataQualityStatus(month, since);
  }, [connection, schema, table, month, since]);

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
            severity: (tableDataQualityStatus.checks ?? {})[key]?.severity,
            executedAt: (tableDataQualityStatus.checks ?? {})[key]?.executed_at,
            checkType: 'table',
            category: (tableDataQualityStatus.checks ?? {})[key]?.category,
            qualityDimension: (tableDataQualityStatus.checks ?? {})[key]
              ?.quality_dimension
          });
        } else {
          data[categoryDimensionKey] = [
            {
              checkName: key,
              severity: (tableDataQualityStatus.checks ?? {})[key]?.severity,
              executedAt: (tableDataQualityStatus.checks ?? {})[key]
                ?.executed_at,
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
              severity: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.severity,
              executedAt: ((tableDataQualityStatus.columns ?? {})[column]
                .checks ?? {})[key]?.executed_at,
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
                severity: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.severity,
                executedAt: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.executed_at,
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
    severity: CheckCurrentDataQualityStatusModelSeverityEnum
  ) => {
    if (
      severity ===
      CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
    ) {
      return 'bg-gray-150';
    }
    if (severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal) {
      return 'bg-red-200';
    }
    if (severity === CheckCurrentDataQualityStatusModelSeverityEnum.error) {
      return 'bg-orange-200';
    } else if (
      severity === CheckCurrentDataQualityStatusModelSeverityEnum.warning
    ) {
      return 'bg-yellow-200';
    } else if (
      severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid
    ) {
      return 'bg-green-200';
    }
    return '';
  };

  useEffect(() => {
    onChangeFirstLevelChecks();
  }, [categoryDimension, tableDataQualityStatus]);

  const colorCell = (checks: TFirstLevelCheck[]) => {
    if (
      checks.find(
        (x) =>
          x.severity ===
            CheckCurrentDataQualityStatusModelSeverityEnum.execution_error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-orange-200';
    } else if (
      checks.find(
        (x) =>
          x.severity ===
            CheckCurrentDataQualityStatusModelSeverityEnum.warning &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-yellow-200';
    } else if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid &&
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
          x.severity ===
          CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
      )
    ) {
      return 'bg-gray-150';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal
      )
    ) {
      return 'bg-red-200';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.error
      )
    ) {
      return 'bg-orange-200';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.warning
      )
    ) {
      return 'bg-yellow-200';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid
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
        )`,
    height: '48px'
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
      </div>

      <div className="flex gap-x-5">
        <SectionWrapper title="Current table status">
          <div className="flex gap-x-2">
            <div className="w-43">Status:</div>
            <div>{tableDataQualityStatus.highest_severity_level}</div>
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
                'p-4 border-b min-w-40 w-40 border-b-gray-150 font-bold'
              )}
            >
              {key}
            </th>
          ))}
        </thead>
        <tbody>
          <tr key="row_table_level_checks" className="">
            <td key="cell_table_level_checks_title" className="font-bold px-4 ">
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
                  <div
                    className={clsx(
                      'w-43 h-12',
                      colorCell(firstLevelChecks[key])
                    )}
                    style={{
                      ...(colorCell(firstLevelChecks[key]) === 'bg-gray-150'
                        ? backgroundStyle
                        : {})
                    }}
                  ></div>
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
                  (x) => x.checkType === key && x.categoryDimension === 'table'
                ) && (
                  <div className="w-40">
                    {(firstLevelChecks[key] ?? []).map((x, index) =>
                      x.checkType === 'table' ? (
                        <Tooltip
                          key={`table_check_${key}_${index}`}
                          content={
                            <div>
                              <div className="flex gap-x-2">
                                <div className="w-42">Executed at:</div>
                                <div>
                                  {moment(x.executedAt).format(
                                    'YYYY-MM-DD HH:mm:ss'
                                  )}
                                </div>
                              </div>
                              <div className="flex gap-x-2">
                                <div className="w-42">Severity level:</div>
                                <div>{x.severity}</div>
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
                                x.severity ??
                                  CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
                              )
                            )}
                            style={{
                              fontSize: '12px',
                              whiteSpace: 'normal',
                              wordBreak: 'break-word',
                              ...(calculateSeverityColor(
                                x.severity ??
                                  CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
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
                      Object.keys(tableDataQualityStatus.columns ?? {}).length -
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
                  {Object.keys(firstLevelChecks).map((firstLevelChecksKey) => (
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
                              toggleExtendedChecks(key, firstLevelChecksKey);
                            }}
                          >
                            <SvgIcon
                              key={`svg_column_${key}_${firstLevelChecksKey}`}
                              name={
                                extendedChecks.find(
                                  (x) =>
                                    x.checkType === key &&
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
                              'h-12 w-43',
                              // 'border border-gray-150',
                              colorColumnCell(
                                (tableDataQualityStatus.columns ?? {})[key],
                                firstLevelChecksKey
                              )
                            )}
                            style={{
                              ...(colorColumnCell(
                                (tableDataQualityStatus.columns ?? {})[key],
                                firstLevelChecksKey
                              ) === 'bg-gray-150'
                                ? backgroundStyle
                                : {})
                            }}
                          ></div>
                        </div>
                      ) : null}
                    </td>
                  ))}
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
                                      <div className="w-42">Executed at:</div>
                                      <div>
                                        {moment(x.executedAt).format(
                                          'YYYY-MM-DD HH:mm:ss'
                                        )}
                                      </div>
                                    </div>
                                    <div className="flex gap-x-2">
                                      <div className="w-42">
                                        Severity level:
                                      </div>
                                      <div>{x.severity}</div>
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
                                      x.severity ??
                                        CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
                                    )
                                  )}
                                  style={{
                                    fontSize: '12px',
                                    whiteSpace: 'normal',
                                    wordBreak: 'break-word',
                                    ...(calculateSeverityColor(
                                      x.severity ??
                                        CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
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
    </div>
  );
}
