import moment from 'moment/moment';
import React, { useCallback, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckModel, DeleteStoredDataQueueJobParameters } from '../../api';
import DeleteOnlyDataDialog from '../../components/CustomTree/DeleteOnlyDataDialog';
import CheckErrorsTab from '../../components/DataQualityChecks/CheckDetails/CheckErrorsTab';
import CheckResultsTab from '../../components/DataQualityChecks/CheckDetails/CheckResultsTab';
import SensorReadoutsTab from '../../components/DataQualityChecks/CheckDetails/SensorReadoutsTab';
import IconButton from '../../components/IconButton';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useTree } from '../../contexts/treeContext';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCheckFilters } from '../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import {
  getIncidentsErrors,
  getIncidentsSensorReadouts,
  getLocalIncidentCheckResults
} from '../../utils';

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
  }
];
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
  data_clean_job_template?: DeleteStoredDataQueueJobParameters;
  defaultFilters?: any;
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
  data_clean_job_template,
  runCheckType,
  checkName,
  timeScale,
  onClose,
  defaultFilters,
  category,
  comparisonName,
  onChangeRefreshCheckObject
}: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [resultsData, setResultsData] = useState<any>();
  const [readoutsData, setReadoutsData] = useState<any>();
  const [errorsData, setErrorsData] = useState<any>();
  const [month, setMonth] = useState('Last 3 months');
  const [dataGroup, setDataGroup] = useState('no grouping');
  const { checkFilters: filtersData } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const checkNameWithComparisonName = comparisonName
    ? checkName + '/' + comparisonName
    : checkName;

  const checkResults = resultsData
    ? resultsData[checkNameWithComparisonName ?? ''] || []
    : [];
  const sensorReadouts = readoutsData
    ? readoutsData[checkNameWithComparisonName ?? ''] || []
    : [];
  const sensorErrors = errorsData
    ? errorsData[checkNameWithComparisonName ?? ''] || []
    : [];
  const filters =
    filtersData && filtersData[checkNameWithComparisonName ?? '']
      ? filtersData[checkNameWithComparisonName ?? '']
      : defaultFilters || {
          month: 'Last 3 months'
        };

  const dispatch = useActionDispatch();

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

  const openDeleteDialog = () => {
    setDeleteDataDialogOpened(true);
  };

  const onChangeDataGroup = (value: string) => {
    dispatch(
      setCheckFilters(
        checkTypes,
        firstLevelActiveTab,
        checkNameWithComparisonName ?? '',
        {
          ...filters,
          onChangeDataGroup: value
        }
      )
    );
    refetch(filters.month, value);
  };

  const onChangeMonth = (value: string) => {
    dispatch(
      setCheckFilters(
        checkTypes,
        firstLevelActiveTab,
        checkNameWithComparisonName ?? '',
        {
          ...filters,
          month: value
        }
      )
    );
    refetch(value, filters.dataGroup);
  };

  const refetch = (month: string, name?: string) => {
    fetchCheckErrors(month, name);
    fetchCheckResults(month, name);
    fetchCheckReadouts(month, name);
  };

  useEffect(() => {
    if (onChangeRefreshCheckObject) {
      onChangeRefreshCheckObject({
        fetchCheckResults: () =>
          fetchCheckResults(filters.month, filters.dataGroup)
      });
    }
  }, [fetchCheckResults]);

  useEffect(() => {
    if (activeTab === 'check_results') {
      fetchCheckResults(filters.month, filters.dataGroup);
    } else if (activeTab === 'sensor_readouts') {
      fetchCheckReadouts(filters.month, filters.dataGroup);
    } else if (activeTab === 'execution_errors') {
      fetchCheckErrors(filters.month, filters.dataGroup);
    }
  }, [activeTab]);
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

        {(!!checkResults.length ||
          !!sensorErrors.length ||
          !!sensorReadouts.length) && (
          <IconButton
            className="absolute right-16 top-1.5 w-8 h-8 bg-gray-50 hover:bg-gray-100 text-gray-700"
            onClick={openDeleteDialog}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        )}
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
              dataGroup={filters.onChangeDataGroup}
              month={filters.month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
              category={category}
              comparisonName={comparisonName}
            />
          )}
          {activeTab === 'sensor_readouts' && (
            <SensorReadoutsTab
              sensorReadouts={readoutsData || []}
              dataGroup={filters.dataGroup}
              month={filters.month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
          {activeTab === 'execution_errors' && (
            <CheckErrorsTab
              errors={errorsData || []}
              dataGroup={filters.dataGroup}
              month={filters.month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
        </div>

        <DeleteOnlyDataDialog
          open={deleteDataDialogOpened}
          onClose={() => setDeleteDataDialogOpened(false)}
          onDelete={(params) => {
            setDeleteDataDialogOpened(false);
            JobApiClient.deleteStoredData(undefined, false, undefined, {
              ...(data_clean_job_template || {}),
              ...params
            });
          }}
        />
      </div>
    </div>
  );
};

export default IncidentCheckDetails;
