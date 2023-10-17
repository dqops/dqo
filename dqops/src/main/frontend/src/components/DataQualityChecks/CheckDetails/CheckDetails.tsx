import React, { useCallback, useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import {
  DqoJobHistoryEntryModelStatusEnum,
  CheckModel,
  DeleteStoredDataQueueJobParameters
} from '../../../api';
import { JobApiClient } from '../../../services/apiClient';
import CheckResultsTab from './CheckResultsTab';
import IconButton from '../../IconButton';
import SvgIcon from '../../SvgIcon';
import { useTree } from '../../../contexts/treeContext';
import SensorReadoutsTab from './SensorReadoutsTab';
import CheckErrorsTab from './CheckErrorsTab';
import DeleteOnlyDataDialog from '../../CustomTree/DeleteOnlyDataDialog';
import moment from 'moment/moment';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { CheckTypes } from '../../../shared/routes';
import {
  getCheckErrors,
  getCheckReadouts,
  getCheckResults,
  setCheckFilters
} from '../../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { IRootState } from '../../../redux/reducers';

const tabs = [
  {
    label: 'Check Results',
    value: 'check_results'
  },
  {
    label: 'Sensor Readouts',
    value: 'sensor_readouts'
  },
  {
    label: 'Execution Errors',
    value: 'execution_errors'
  }
];
interface IRefetchResultsProps {
  fetchCheckResults : () => void
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
  onChangeRefreshCheckObject?: (obj: IRefetchResultsProps) => void
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
  onChangeRefreshCheckObject
}: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkResults: resultsData,
    sensorReadouts: readoutsData,
    sensorErrors: errorsData,
    checkFilters: filtersData,
    currentJobId
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const currentJob = currentJobId
    ? job_dictionary_state[currentJobId]
    : undefined;

  const checkResults = resultsData ? resultsData[checkName ?? ''] || [] : [];
  const sensorReadouts = readoutsData
    ? readoutsData[checkName ?? ''] || []
    : [];
  const sensorErrors = errorsData ? errorsData[checkName ?? ''] || [] : [];
  const filters =
    filtersData && filtersData[checkName ?? '']
      ? filtersData[checkName ?? '']
      : defaultFilters || {
          month: "Last 3 months"
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
    [runCheckType, checkName, timeScale, connection, schema, table, column, category]
  );

  useEffect(() => {
    if (!sensorErrors?.length) {
      fetchCheckErrors(filters.month, filters.dataGroup);
    }
  }, []);

  useEffect(() => {
    if (!sensorReadouts?.length) {
      fetchCheckReadouts(filters.month, filters.dataGroup);
    }
  }, []);

  useEffect(() => {
    if (!checkResults?.length) {
      fetchCheckResults(filters.month, filters.dataGroup);
    }
  }, []);

  useEffect(() => {
    if (
      currentJob?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
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
      setCheckFilters(checkTypes, firstLevelActiveTab, checkName ?? '', {
        ...filters,
        onChangeDataGroup: value
      })
    );
    refetch(filters.month, value);
  };

  const onChangeMonth = (value: string) => {
    dispatch(
      setCheckFilters(checkTypes, firstLevelActiveTab, checkName ?? '', {
        ...filters,
        month: value
      })
    );
    refetch(value, filters.dataGroup);
  };

  const refetch = (month: string, name?: string) => {
    fetchCheckErrors(month, name);
    fetchCheckResults(month, name);
    fetchCheckReadouts(month, name);
  };

  useEffect(() => {
    if(onChangeRefreshCheckObject) {
      onChangeRefreshCheckObject({fetchCheckResults: () => fetchCheckResults(filters.month, filters.onChangeDataGroup)})
    }
  }, [])

  return (
    <div
      className="my-4"
      style={{
        maxWidth: `calc(100vw - ${sidebarWidth + 85}px`
      }}
    >
      <div className="bg-white px-4 py-6 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>

        {(!!checkResults.length ||
          !!sensorErrors.length ||
          !!sensorReadouts.length) && (
          <IconButton
            className="absolute right-16 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
            onClick={openDeleteDialog}
          >
            <SvgIcon name="delete" className="w-5" />
          </IconButton>
        )}

        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
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

        <DeleteOnlyDataDialog
          open={deleteDataDialogOpened}
          onClose={() => setDeleteDataDialogOpened(false)}
          onDelete={(params) => {
            setDeleteDataDialogOpened(false);
            JobApiClient.deleteStoredData(
              false,
              undefined,
              {
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
