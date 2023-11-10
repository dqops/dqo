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

type TFirstLevelCheck = {
  checkName: string;
  severity?: CheckCurrentDataQualityStatusModelSeverityEnum;
  executedAt?: number | string;
  checkType: string;
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
      since
    ).then((res) => setTableDataQualityStatus(res.data));
  };

  useEffect(() => {
    getTableDataQualityStatus();
  }, [connection, schema, table, month, since]);

  console.log(month, since);

  // const colorCellBasedOnSeverity = (checks : TFirstLevelCheck[] | CheckCurrentDataQualityStatusModel[]) => {}

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
            checkType: 'table'
          });
        } else {
          data[categoryDimensionKey] = [
            {
              checkName: key,
              severity: (tableDataQualityStatus.checks ?? {})[key]?.severity,
              executedAt: (tableDataQualityStatus.checks ?? {})[key]
                ?.executed_at,
              checkType: 'table'
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
              checkType: column
            });
          } else {
            data[categoryDimensionColumnKey] = [
              {
                checkName: key,
                severity: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.severity,
                executedAt: ((tableDataQualityStatus.columns ?? {})[column]
                  .checks ?? {})[key]?.executed_at,
                checkType: column
              }
            ];
          }
        }
      })
    );

    setFirstLevelChecks(data);
  };

  const calculateSeverityColor = (
    severity: CheckCurrentDataQualityStatusModelSeverityEnum
  ) => {
    if (
      severity ===
      CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
    ) {
      return 'bg-gray-500';
    }
    if (severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal) {
      return 'bg-red-500';
    }
    if (severity === CheckCurrentDataQualityStatusModelSeverityEnum.error) {
      return 'bg-orange-500';
    } else if (
      severity === CheckCurrentDataQualityStatusModelSeverityEnum.warning
    ) {
      return 'bg-yellow-500';
    } else if (
      severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid
    ) {
      return 'bg-teal-500';
    }
    return '';
  };

  useEffect(() => {
    onChangeFirstLevelChecks();
  }, [categoryDimension, tableDataQualityStatus]);

  console.log(firstLevelChecks);

  const colorCell = (checks: TFirstLevelCheck[]) => {
    if (
      checks.find(
        (x) =>
          x.severity ===
            CheckCurrentDataQualityStatusModelSeverityEnum.execution_error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-gray-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-red-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.error &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-orange-500';
    } else if (
      checks.find(
        (x) =>
          x.severity ===
            CheckCurrentDataQualityStatusModelSeverityEnum.warning &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-yellow-500';
    } else if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid &&
          x.checkType === 'table'
      )
    ) {
      return 'bg-teal-500';
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
      return 'bg-gray-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.fatal
      )
    ) {
      return 'bg-red-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.error
      )
    ) {
      return 'bg-orange-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.warning
      )
    ) {
      return 'bg-yellow-500';
    }
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid
      )
    ) {
      return 'bg-teal-500';
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
  return (
    <div className="p-4">
      <div className="flex justify-between">
        <div className="flex pb-6 gap-x-5">
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
          <div className="flex">
            <div onClick={() => onChangeFirstLevelChecks()}>Status:</div>
            <div>{tableDataQualityStatus.highest_severity_level}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Last check executed at:</div>
            <div>{tableDataQualityStatus.last_check_executed_at}</div>
          </div>
        </SectionWrapper>
        <SectionWrapper title="Total checks executed">
          <div className="flex gap-x-2">
            <div>Total checks executed:</div>
            <div>{tableDataQualityStatus.executed_checks}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Valid:</div>
            <div>{tableDataQualityStatus.valid_results}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Warnings:</div>
            <div>{tableDataQualityStatus.warnings}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Errors:</div>
            <div>{tableDataQualityStatus.errors}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Fatals:</div>
            <div>{tableDataQualityStatus.fatals}</div>
          </div>
          <div className="flex gap-x-2">
            <div>Execution errors:</div>
            <div>{tableDataQualityStatus.execution_errors}</div>
          </div>
        </SectionWrapper>
      </div>
      <table className="border border-gray-150 mt-4 min-w-250">
        <thead>
          <th
            key="header_blank"
            className="p-4 border-b border-b-gray-150"
          ></th>
          {Object.keys(firstLevelChecks).map((key) => (
            <th
              key={`header_${key}`}
              className={clsx('p-4 border-b border-b-gray-150')}
            >
              {key}
            </th>
          ))}
        </thead>
        <tbody>
          <tr
            key="row_table_level_checks"
            className="border-b border-b-gray-150"
          >
            <td key="cell_table_level_checks_title" className="font-bold px-4">
              Table level checks
            </td>
            {Object.keys(firstLevelChecks).map((key) => (
              <td
                key={`cell_table_level_checks_${key}`}
                className={clsx(
                  'p-2 border-b border-b-gray-150',
                  colorCell(firstLevelChecks[key])
                )}
              >
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
                          x.checkType === key && x.categoryDimension === 'table'
                      )
                        ? 'chevron-right'
                        : 'chevron-down'
                    }
                    className="h-5 w-5"
                  />
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
              <td key={`cell_table_level_checks_blank_${key}`}>
                {extendedChecks.find(
                  (x) => x.checkType === key && x.categoryDimension === 'table'
                ) &&
                  (firstLevelChecks[key] ?? []).map((x, index) =>
                    x.checkType === 'table' ? (
                      <div
                        key={`table_check_${key}_${index}`}
                        className={clsx(
                          calculateSeverityColor(
                            x.severity ??
                              CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
                          )
                        )}
                      >
                        {x.checkName}
                      </div>
                    ) : (
                      ''
                    )
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
                    className="p-2 px-4 underline cursor-pointer"
                    onClick={() => openFirstLevelColumnTab(key)}
                  >
                    {key}
                  </td>
                  {Object.keys(firstLevelChecks).map((firstLevelChecksKey) => (
                    <td
                      key={`cell_column_${key}_${firstLevelChecksKey}`}
                      className={clsx(
                        '',
                        colorColumnCell(
                          (tableDataQualityStatus.columns ?? {})[key],
                          firstLevelChecksKey
                        )
                      )}
                    >
                      {' '}
                      {colorColumnCell(
                        (tableDataQualityStatus.columns ?? {})[key],
                        firstLevelChecksKey
                      ) !== '' ? (
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
                                ? 'chevron-right'
                                : 'chevron-down'
                            }
                            className="h-5 w-5"
                          />
                        </div>
                      ) : null}
                    </td>
                  ))}
                </tr>
                <tr key={`column_row_blank_${key}`}>
                  <td key={`column_cell_blank_${key}`} className="px-4 "></td>
                  {Object.keys(firstLevelChecks).map((check) => (
                    <td key={`cell_column_blank_${key}_${check}`}>
                      {extendedChecks.find(
                        (x) =>
                          x.checkType === key && x.categoryDimension === check
                      )
                        ? (firstLevelChecks[check] ?? []).map((x, index) =>
                            x.checkType === key ? (
                              <div
                                key={`column_check_${key}_${check}_${index}`}
                                className={clsx(
                                  calculateSeverityColor(
                                    x.severity ??
                                      CheckCurrentDataQualityStatusModelSeverityEnum.execution_error
                                  )
                                )}
                              >
                                {x.checkName}
                              </div>
                            ) : (
                              ''
                            )
                          )
                        : null}
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
