import React, { useEffect, useState } from 'react';
import { TableCurrentDataQualityStatusModel } from '../../../../api';
import { CheckResultApi } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import DatePicker from '../../../DatePicker';
import RadioButton from '../../../RadioButton';
import CurrentTableStatus from './CurrentTableStatus';
import { TFirstLevelCheck } from './TableQualityStatusConstans';
import TableQualityStatusOverview from './TableQualityStatusOverview';
import TotalChecksExecuted from './TotalChecksExecuted';
import { useDecodedParams } from '../../../../utils';

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
  } = useDecodedParams();
  const [tableDataQualityStatus, setTableDataQualityStatus] =
    useState<TableCurrentDataQualityStatusModel>({});
  const [firstLevelChecks, setFirstLevelChecks] = useState<
    Record<string, TFirstLevelCheck[]>
  >({});
  const [categoryDimension, setCategoryDimension] = useState<
    'category' | 'dimension'
  >('category');
  const [severityType, setSeverityType] = useState<'current' | 'highest'>(
    'current'
  );
  const [month, setMonth] = useState<number | undefined>(1);
  const [since, setSince] = useState<Date | undefined>();

  const getTableDataQualityStatus = (month?: number, since?: Date) => {
    CheckResultApi.getTableDataQualityStatus(
      connection,
      schema,
      table,
      month,
      since?.toISOString(),
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
            lastExecutedAt: (tableDataQualityStatus.checks ?? {})[key]
              ?.last_executed_at,
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

  useEffect(() => {
    onChangeFirstLevelChecks();
  }, [categoryDimension, tableDataQualityStatus, severityType]);

  return (
    <div className="p-4 text-sm">
      <div className="flex justify-between items-center">
        <div className="flex pb-6 gap-x-5 items-center">
          <div className='text-sm'>Group checks by: </div>
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
              label="Current month"
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
        <CurrentTableStatus tableDataQualityStatus={tableDataQualityStatus} />
        <TotalChecksExecuted tableDataQualityStatus={tableDataQualityStatus} />
      </div>
      {Object.keys(firstLevelChecks).length > 0 ? (
        <TableQualityStatusOverview
          firstLevelChecks={firstLevelChecks}
          categoryDimension={categoryDimension}
          severityType={severityType}
          tableDataQualityStatus={tableDataQualityStatus}
          timeScale={timeScale}
        />
      ) : (
        <div className="mt-5">No data quality check results</div>
      )}
    </div>
  );
}
