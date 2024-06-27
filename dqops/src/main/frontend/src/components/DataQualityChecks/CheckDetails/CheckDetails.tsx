import moment from 'moment/moment';
import React, { useCallback, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckModel,
  DeleteStoredDataQueueJobParameters,
  DqoJobHistoryEntryModelStatusEnum
} from '../../../api';
import { useTree } from '../../../contexts/treeContext';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getCheckErrors,
  getCheckReadouts,
  getCheckResults,
  getErrorSamples,
  setCheckFilters
} from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { JobApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import DeleteOnlyDataDialog from '../../CustomTree/DeleteOnlyDataDialog';
import IconButton from '../../IconButton';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import CheckErrorsTab from './CheckErrorsTab';
import CheckResultsTab from './CheckResultsTab';
import ErrorSamplesTab from './ErrorSamplesTab';
import SensorReadoutsTab from './SensorReadoutsTab';

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
const CheckDetails = ({
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
  onChangeRefreshCheckObject,
  check
}: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkResults: resultsData,
    sensorReadouts: readoutsData,
    sensorErrors: errorsData,
    checkFilters: filtersData,
    errorSamples,
    currentJobId
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  console.log(errorSamples);
  const currentJob = currentJobId
    ? job_dictionary_state[currentJobId]
    : undefined;

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
      const { startDate, endDate } = calculateDateRange(month);

      dispatch(
        getCheckErrors(checkTypes, firstLevelActiveTab, {
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
        })
      );
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
      const { startDate, endDate } = calculateDateRange(month);

      dispatch(
        getCheckReadouts(checkTypes, firstLevelActiveTab, {
          connection,
          schema,
          table,
          column,
          startDate,
          dataGrouping,
          endDate,
          runCheckType,
          timeScale,
          checkName: checkName ?? '',
          category,
          comparisonName
        })
      );
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
      const { startDate, endDate } = calculateDateRange(month);

      dispatch(
        getCheckResults(checkTypes, firstLevelActiveTab, {
          connection,
          schema,
          table,
          column,
          dataGrouping,
          startDate,
          endDate,
          runCheckType,
          checkName: checkName ?? '',
          timeScale,
          category,
          comparisonName
        })
      );
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
      const { startDate, endDate } = calculateDateRange(month);

      dispatch(
        getErrorSamples(checkTypes, firstLevelActiveTab, {
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
        })
      );
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

  useEffect(() => {
    if (
      currentJob?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
      currentJob?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      refetch(filters.month, filters.dataGroup);
    }
  }, [currentJob?.status]);

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
    fetchErrorSamples(month, name);
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
    } else if (activeTab === 'error_sampling') {
      fetchErrorSamples(filters.month, filters.dataGroup);
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
  console.log(check);
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
              results={checkResults || []}
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
              sensorReadouts={sensorReadouts || []}
              dataGroup={filters.dataGroup}
              month={filters.month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
          {activeTab === 'execution_errors' && (
            <CheckErrorsTab
              errors={sensorErrors || []}
              dataGroup={filters.dataGroup}
              month={filters.month}
              onChangeMonth={onChangeMonth}
              onChangeDataGroup={onChangeDataGroup}
            />
          )}
          {activeTab === 'error_sampling' && (
            <ErrorSamplesTab
              errorSamples={errorSamples || []}
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

export default CheckDetails;
