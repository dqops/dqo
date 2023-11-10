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

type TFirstLevelCheck = {
  checkName: string;
  severity?: CheckCurrentDataQualityStatusModelSeverityEnum;
  executedAt?: number | string;
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
  const [categoryDimension, setCategoryDimension] = useState<
    'category' | 'dimension'
  >('category');
  const getTableDataQualityStatus = () => {
    CheckResultApi.getTableDataQualityStatus(connection, schema, table).then(
      (res) => setTableDataQualityStatus(res.data)
    );
  };

  useEffect(() => {
    getTableDataQualityStatus();
  }, [connection, schema, table]);

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
            executedAt: (tableDataQualityStatus.checks ?? {})[key]?.executed_at
          });
        } else {
          data[categoryDimensionKey] = [
            {
              checkName: key,
              severity: (tableDataQualityStatus.checks ?? {})[key]?.severity,
              executedAt: (tableDataQualityStatus.checks ?? {})[key]
                ?.executed_at
            }
          ];
        }
      }
    });
    setFirstLevelChecks(data);
  };

  useEffect(() => {
    onChangeFirstLevelChecks();
  }, [categoryDimension, tableDataQualityStatus]);

  console.log(tableDataQualityStatus);

  const colorCell = (checks: TFirstLevelCheck[]) => {
    if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.error
      )
    ) {
      return 'bg-red-500';
    } else if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.warning
      )
    ) {
      return 'bg-yellow-500';
    } else if (
      checks.find(
        (x) =>
          x.severity === CheckCurrentDataQualityStatusModelSeverityEnum.valid
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

  return (
    <div className="p-4">
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
      <table className="border border-gray-150 mt-4">
        <thead>
          <th className="p-4 border-b border-b-gray-150"></th>
          {Object.keys(firstLevelChecks).map((key, index) => (
            <th key={index} className={clsx('p-4 border-b border-b-gray-150')}>
              {key}
            </th>
          ))}
        </thead>
        <tbody>
          <tr className="border-b border-b-gray-150">
            <td className="font-bold px-4 ">Table level checks</td>
            {Object.keys(firstLevelChecks).map((key, index) => (
              <td
                key={index}
                className={clsx(
                  'p-2 border-b border-b-gray-150',
                  colorCell(firstLevelChecks[key])
                )}
              ></td>
            ))}
          </tr>
          {Object.keys(tableDataQualityStatus.columns ?? {}).map(
            (key, index) => (
              <tr key={index} className="p-2">
                <td
                  className="p-2 px-4 underline cursor-pointer"
                  onClick={() => openFirstLevelColumnTab(key)}
                >
                  {key}
                </td>
                {Object.keys(firstLevelChecks).map(
                  (firstLevelChecksKey, jIndex) => (
                    <td
                      key={jIndex * 10}
                      className={clsx(
                        colorColumnCell(
                          (tableDataQualityStatus.columns ?? {})[key],
                          firstLevelChecksKey
                        )
                      )}
                    ></td>
                  )
                )}
              </tr>
            )
          )}
        </tbody>
      </table>
    </div>
  );
}
