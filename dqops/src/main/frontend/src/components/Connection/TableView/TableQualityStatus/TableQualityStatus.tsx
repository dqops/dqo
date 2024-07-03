import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { TableCurrentDataQualityStatusModel } from '../../../../api';
import { CheckResultApi } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import DatePicker from '../../../DatePicker';
import RadioButton from '../../../RadioButton';
import CurrentTableStatus from './CurrentTableStatus';
import { TFirstLevelCheck } from './TableQualityStatusConstans';
import TableQualityStatusOverview from './TableQualityStatusOverview';
import TotalChecksExecuted from './TotalChecksExecuted';

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
  const [month, setMonth] = useState<number | undefined>();
  const [since, setSince] = useState<Date | undefined>(
    new Date(moment().subtract(30, 'days').format('YYYY-MM-DD'))
  );

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

    const processCheck = (key: string, check: any, checkType: string) => {
      const categoryDimensionKey =
        categoryDimension === 'category'
          ? check?.category
          : check?.quality_dimension;

      if (categoryDimensionKey !== undefined) {
        const checkData: TFirstLevelCheck = {
          checkName: key,
          currentSeverity: check?.current_severity,
          highestSeverity: check?.highest_historical_severity,
          lastExecutedAt: check?.last_executed_at,
          checkType,
          category: check?.category,
          qualityDimension: check?.quality_dimension,
          execution_errors: check?.execution_errors
        };

        if (data[categoryDimensionKey]) {
          data[categoryDimensionKey].push(checkData);
        } else {
          data[categoryDimensionKey] = [checkData];
        }
      }
    };

    const tableChecks = tableDataQualityStatus.checks ?? {};
    Object.keys(tableChecks).forEach((key) => {
      processCheck(key, tableChecks[key], 'table');
    });

    const columnChecks = tableDataQualityStatus.columns ?? {};
    Object.keys(columnChecks).forEach((column) => {
      const columnData = columnChecks[column].checks ?? {};
      Object.keys(columnData).forEach((key) => {
        processCheck(key, columnData[key], column);
      });
    });

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
          <div className="text-sm pl-1">Group checks by: </div>
          <RadioButton
            checked={categoryDimension === 'category'}
            label="category"
            fontClassName="text-sm"
            onClick={() => setCategoryDimension('category')}
          />
          <RadioButton
            checked={categoryDimension === 'dimension'}
            label="quality dimension"
            fontClassName="text-sm"
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
              fontClassName="text-sm"
            />
            <RadioButton
              checked={month === 3}
              label="Last 3 months"
              onClick={() => {
                setSince(undefined), setMonth(3);
              }}
              fontClassName="text-sm"
            />
            <RadioButton
              checked={month === undefined}
              label="Since"
              onClick={() => {
                setMonth(undefined);
              }}
              fontClassName="text-sm"
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
              fontClassName="text-sm"
            />
            <RadioButton
              label="Highest severity status"
              checked={severityType === 'highest'}
              onClick={() => setSeverityType('highest')}
              fontClassName="text-sm"
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
