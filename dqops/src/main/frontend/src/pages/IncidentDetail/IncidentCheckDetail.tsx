import moment from 'moment/moment';
import React, { useCallback, useEffect, useState } from 'react';
import { CheckModel } from '../../api';
import CheckErrorsTab from '../../components/DataQualityChecks/CheckDetails/CheckErrorsTab';
import CheckResultsTab from '../../components/DataQualityChecks/CheckDetails/CheckResultsTab';
import ErrorSamplesTab from '../../components/DataQualityChecks/CheckDetails/ErrorSamplesTab';
import SensorReadoutsTab from '../../components/DataQualityChecks/CheckDetails/SensorReadoutsTab';
import IconButton from '../../components/IconButton';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useTree } from '../../contexts/treeContext';
import { CheckTypes } from '../../shared/routes';
import {
  getIncidentsErrorSamples,
  getIncidentsErrors,
  getIncidentsSensorReadouts,
  getLocalIncidentCheckResults
} from '../../utils';

interface IRefetchResultsProps {
  fetchCheckResults: () => void;
}

interface CheckDetailsProps {
  checkTypes: CheckTypes;
  connection: string;
  schema: string;
  table: string;
  column?: string;
  check?: CheckModel;
  runCheckType?: string;
  timeScale?: 'daily' | 'monthly';
  checkName?: string;
  onClose: () => void;
  category?: string;
  comparisonName?: string;
  onChangeRefreshCheckObject?: (obj: IRefetchResultsProps) => void;
}
//deleted dataGroup from here
const IncidentCheckDetails = ({
  checkTypes,
  connection,
  schema,
  table,
  column,
  runCheckType,
  checkName,
  timeScale,
  onClose,
  category,
  comparisonName,
  onChangeRefreshCheckObject,
  check
}: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const [resultsData, setResultsData] = useState<any>();
  const [readoutsData, setReadoutsData] = useState<any>();
  const [errorsData, setErrorsData] = useState<any>();
  const [errorSamples, setErrorSamples] = useState<any>();
  const [month, setMonth] = useState('Last 3 months');
  const [dataGroup, setDataGroup] = useState('no grouping');

  const { sidebarWidth } = useTree();

  const calculateDateRange = (month: string) => {
    if (!month) return { startDate: '', endDate: '' };

    if (month === 'Last 3 months') {
      return {
        startDate: moment().add(-3, 'month').format('YYYY-MM-DD'),
        endDate: moment().format('YYYY-MM-DD')
      };
    }

    return {
      startDate: moment(month, 'MMMM YYYY').format('YYYY-MM-DD'),
      endDate: moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
    };
  };

  const fetchCheckErrors = useCallback(
    (month: string, dataGrouping?: string) => {
      const { startDate, endDate } = calculateDateRange(
        month ?? 'Last 3 months'
      );

      getIncidentsErrors({
        checkType: checkTypes,
        connection,
        schema,
        table,
        column,
        dataGrouping,
        startDate,
        endDate,
        runCheckType,
        timeScale,
        checkName: checkName ?? '',
        category,
        comparisonName
      }).then((res) => {
        setErrorsData(res);
      });
    },
    [
      checkName,
      timeScale,
      runCheckType,
      connection,
      schema,
      table,
      column,
      category,
      comparisonName
    ]
  );

  const fetchCheckReadouts = useCallback(
    (month: string, dataGrouping?: string) => {
      const { startDate, endDate } = calculateDateRange(
        month ?? 'Last 3 months'
      );

      getIncidentsSensorReadouts({
        checkType: checkTypes,
        connection,
        schema,
        table,
        column,
        dataGrouping,
        startDate,
        endDate,
        runCheckType,
        timeScale,
        checkName: checkName ?? '',
        category,
        comparisonName
      }).then((res) => {
        setReadoutsData(res);
      });
    },
    [
      runCheckType,
      checkName,
      timeScale,
      connection,
      schema,
      table,
      column,
      category,
      comparisonName
    ]
  );

  const fetchCheckResults = useCallback(
    (month: string, dataGrouping?: string) => {
      const { startDate, endDate } = calculateDateRange(
        month ?? 'Last 3 months'
      );

      getLocalIncidentCheckResults({
        checkType: checkTypes,
        connection,
        schema,
        table,
        column,
        dataGrouping,
        startDate,
        endDate,
        runCheckType,
        timeScale,
        checkName: checkName ?? '',
        category,
        comparisonName
      }).then((res) => {
        setResultsData(res);
      });
    },
    [
      runCheckType,
      checkName,
      timeScale,
      connection,
      schema,
      table,
      column,
      category
    ]
  );

  const fetchErrorSamples = useCallback(
    (month: string, dataGrouping?: string) => {
      const { startDate, endDate } = calculateDateRange(
        month ?? 'Last 3 months'
      );

      getIncidentsErrorSamples({
        checkType: checkTypes,
        connection,
        schema,
        table,
        column,
        dataGrouping,
        startDate,
        endDate,
        runCheckType,
        timeScale,
        checkName: checkName ?? '',
        category,
        comparisonName
      }).then((res) => {
        setErrorSamples(res);
      });
    },
    [
      runCheckType,
      checkName,
      timeScale,
      connection,
      schema,
      table,
      column,
      category
    ]
  );

  const onChangeDataGroup = (value: string) => {
    setDataGroup(value);
    refetch(month, value);
  };

  const onChangeMonth = (value: string) => {
    setMonth(value);
    refetch(value, dataGroup);
  };

  const refetch = (month: string, name?: string) => {
    fetchCheckErrors(month, name);
    fetchCheckResults(month, name);
    fetchCheckReadouts(month, name);
    fetchErrorSamples(month, name);
  };

  useEffect(() => {
    if (onChangeRefreshCheckObject) {
      onChangeRefreshCheckObject({
        fetchCheckResults: () => fetchCheckResults(month, dataGroup)
      });
    }
  }, [fetchCheckResults]);

  useEffect(() => {
    if (activeTab === 'check_results') {
      fetchCheckResults(month, dataGroup);
    } else if (activeTab === 'sensor_readouts') {
      fetchCheckReadouts(month, dataGroup);
    } else if (activeTab === 'execution_errors') {
      fetchCheckErrors(month, dataGroup);
    } else if (activeTab === 'error_sampling') {
      fetchErrorSamples(month, dataGroup);
    }
  }, [activeTab]);
  const tabs = [
    {
      label: 'Check results',
      value: 'check_results'
    },
    {
      label: 'Sensor readouts',
      value: 'sensor_readouts'
    },
    {
      label: 'Execution errors',
      value: 'execution_errors'
    },
    {
      label: 'Error sampling',
      value: 'error_sampling',
      isDisabled: check?.supports_error_sampling !== true
    }
  ];
  return (
    <div
      className="my-4"
      style={{
        maxWidth: `calc(100vw - ${sidebarWidth + 40}px`
      }}
    >
      <div className="bg-white py-2 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-1.5 w-8 h-8 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>

        <div className="border-b border-gray-300 px-0">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        <div className="px-2">
          {activeTab === 'check_results' && (
            <CheckResultsTab
              runCheckType={runCheckType || ''}
              checkName={checkName || ''}
              timeScale={timeScale}
              results={resultsData || []}
              dataGroup={dataGroup}
              month={month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
              category={category}
              comparisonName={comparisonName}
            />
          )}
          {activeTab === 'sensor_readouts' && (
            <SensorReadoutsTab
              sensorReadouts={readoutsData || []}
              dataGroup={dataGroup}
              month={month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
          {activeTab === 'execution_errors' && (
            <CheckErrorsTab
              errors={errorsData || []}
              dataGroup={dataGroup}
              month={month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
          {activeTab === 'error_sampling' && (
            <ErrorSamplesTab
              errorSamples={errorSamples || []}
              dataGroup={dataGroup}
              month={month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default IncidentCheckDetails;
