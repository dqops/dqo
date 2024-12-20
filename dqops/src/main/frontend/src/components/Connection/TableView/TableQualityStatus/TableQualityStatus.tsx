import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { TableCurrentDataQualityStatusModel } from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { setJobAllert } from '../../../../redux/actions/job.actions';
import { IRootState } from '../../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { CheckResultApi } from '../../../../services/apiClient';
import { CheckTypes } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import DatePicker from '../../../DatePicker';
import RadioButton from '../../../RadioButton';
import Tabs from '../../../Tabs';
import CurrentTableStatus from './CurrentTableStatus';
import { TFirstLevelCheck } from './TableQualityStatusConstans';
import TableQualityStatusOverview from './TableQualityStatusOverview';
import TotalChecksExecuted from './TotalChecksExecuted';

export default function TableQualityStatus({
  timePartitioned,
  setTimePartitioned
}: {
  timePartitioned?: 'daily' | 'monthly';
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
}) {
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
  const tabs = [
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Daily checkpoints'
          : 'Daily partitioned',
      value: 'daily'
    },
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Monthly checkpoints'
          : 'Monthly partitioned',
      value: 'monthly'
    }
  ];
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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
  const [extendedChecks, setExtendedChecks] = useState<
    Array<{ checkType: string; categoryDimension: string }>
  >([]);
  const [month, setMonth] = useState<number | undefined>();
  const [since, setSince] = useState<Date | undefined>(
    new Date(moment().subtract(30, 'days').format('YYYY-MM-DD'))
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const dispatch = useActionDispatch();

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
      timePartitioned
    ).then((res) => {
      if (
        (!res.data.checks || Object.keys(res.data.checks).length === 0) &&
        (!res.data.columns || Object.keys(res.data.columns).length === 0)
      ) {
        dispatch(
          setJobAllert({
            activeTab: firstLevelActiveTab,
            action: timePartitioned ? 'check-editor' : 'advanced',
            tooltipMessage:
              'The table has no data recent data quality results. Please configure and run additional data quality checks to see the table quality status.'
          })
        );
      }
      setTableDataQualityStatus(res.data);
    });
  };

  useEffect(() => {
    getTableDataQualityStatus(month, since);
  }, [connection, schema, table, month, since, checkTypes, timePartitioned]);

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

  const handleCategoryDimensionChange = (value: 'category' | 'dimension') => {
    setCategoryDimension(value);
    setExtendedChecks([]);
  };

  const handleSeverityTypeChange = (value: 'current' | 'highest') => {
    setSeverityType(value);
    setExtendedChecks([]);
  };

  const handleMonthChange = (value: number | undefined) => {
    setMonth(value);
    setSince(undefined);
    setExtendedChecks([]);
  };

  return (
    <div className="text-sm">
      {timePartitioned &&
        userProfile &&
        userProfile.license_type &&
        userProfile.license_type?.toLowerCase() !== 'free' &&
        !userProfile.trial_period_expires_at && (
          <div className="border-b border-gray-300">
            <Tabs
              tabs={tabs}
              activeTab={timePartitioned}
              onChange={setTimePartitioned}
              className="pt-2"
            />
          </div>
        )}
      <div className="flex justify-between ">
        <div className="flex gap-x-5 pl-4 mt-3 relative">
          <div className="text-sm pl-1">Group checks by: </div>
          <RadioButton
            checked={categoryDimension === 'category'}
            label="category"
            fontClassName="text-sm"
            className="!items-start"
            onClick={() => handleCategoryDimensionChange('category')}
          />
          <RadioButton
            checked={categoryDimension === 'dimension'}
            label="quality dimension"
            fontClassName="text-sm"
            className="!items-start"
            onClick={() => handleCategoryDimensionChange('dimension')}
          />
        </div>
        <div className="flex pb-6 gap-x-5 pr-4 pt-2">
          <RadioButton
            checked={month === 1}
            label="Current month"
            onClick={() => handleMonthChange(1)}
            fontClassName="text-sm"
          />
          <RadioButton
            checked={month === 3}
            label="Last 3 months"
            onClick={() => handleMonthChange(3)}
            fontClassName="text-sm"
          />
          <RadioButton
            checked={month === undefined}
            label="Since"
            onClick={() => handleMonthChange(undefined)}
            fontClassName="text-sm"
          />
          <DatePicker
            showIcon
            placeholderText="Select date start"
            onChange={(date: any) => {
              setSince(date);
              setExtendedChecks([]);
            }}
            selected={since}
            dateFormat="yyyy-MM-dd"
            disabled={month !== undefined}
          />
        </div>
      </div>
      <div className="flex flex-col 2xl:flex-row 2xl:items-center items-end gap-y-2 gap-x-3 justify-end pr-4">
        <RadioButton
          label="Current severity status"
          checked={severityType === 'current'}
          onClick={() => handleSeverityTypeChange('current')}
          fontClassName="text-sm"
        />
        <RadioButton
          label="Highest severity status"
          checked={severityType === 'highest'}
          onClick={() => handleSeverityTypeChange('highest')}
          fontClassName="text-sm"
        />
      </div>
      <div className="flex gap-x-5 ml-4 mt-[-30px]">
        <CurrentTableStatus tableDataQualityStatus={tableDataQualityStatus} />
        <TotalChecksExecuted tableDataQualityStatus={tableDataQualityStatus} />
      </div>
      <div>
        {Object.keys(firstLevelChecks).length > 0 ? (
          <TableQualityStatusOverview
            firstLevelChecks={firstLevelChecks}
            categoryDimension={categoryDimension}
            severityType={severityType}
            tableDataQualityStatus={tableDataQualityStatus}
            timeScale={timePartitioned}
            extendedChecks={extendedChecks}
            setExtendedChecks={setExtendedChecks}
          />
        ) : (
          <div className="mt-5">No data quality check results</div>
        )}
      </div>
    </div>
  );
}
