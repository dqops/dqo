import React, { useCallback, useEffect, useState } from 'react';
import Tabs from '../../Tabs';
import { useParams } from 'react-router-dom';
import {
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum,
  UICheckModel
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
  setCheckFilters,
} from '../../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import { getFirstLevelActiveTab, getFirstLevelState } from '../../../redux/selectors';

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

interface CheckDetailsProps {
  check?: UICheckModel;
  onClose: () => void;
  job?: DqoJobHistoryEntryModel;
}

const CheckDetails = ({ check, onClose, job }: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const {
    checkTypes,
    connection,
    schema,
    table,
    column
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useParams();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkResults: resultsData,
    sensorReadouts: readoutsData,
    sensorErrors: errorsData,
    checkFilters: filtersData
  } = useSelector(getFirstLevelState(checkTypes));
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const checkResults = resultsData
    ? resultsData[check?.check_name ?? ''] || []
    : [];
  const sensorReadouts = readoutsData
    ? readoutsData[check?.check_name ?? ''] || []
    : [];
  const sensorErrors = errorsData
    ? errorsData[check?.check_name ?? ''] || []
    : [];
  const filters = filtersData ? filtersData[check?.check_name ?? ''] || {
    month: moment().format('MMMM YYYY')
  } : {
    month: moment().format('MMMM YYYY')
  };

  const dispatch = useActionDispatch();

  const { sidebarWidth } = useTree();

  useEffect(() => {
    if (!sensorErrors.length) {
      fetchCheckErrors(filters.month, filters.dataStreamName);
    }
  }, []);

  useEffect(() => {
    if (!sensorReadouts.length) {
      fetchCheckReadouts(filters.month, filters.dataStreamName);
    }
  }, []);

  useEffect(() => {
    if (!checkResults.length) {
      fetchCheckResults(filters.month, filters.dataStreamName);
    }
  }, []);

  const fetchCheckErrors = useCallback(
    (month: string, dataStreamName?: string) => {
      const startDate = month
        ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD')
        : '';
      const endDate = month
        ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
        : '';

      dispatch(getCheckErrors(checkTypes, firstLevelActiveTab, {
        connection,
        schema,
        table,
        column,
        dataStreamName,
        startDate,
        endDate,
        check
      }));
    },
    [check, connection, schema, table, column]
  );

  const fetchCheckReadouts = useCallback(
    (month: string, dataStreamName?: string) => {
      const startDate = month
        ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD')
        : '';
      const endDate = month
        ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
        : '';

      dispatch(getCheckReadouts(
        checkTypes,
        firstLevelActiveTab,
        {
          connection,
          schema,
          table,
          column,
          dataStreamName,
          check,
          startDate,
          endDate
        }
      ));
    },
    [check, connection, schema, table, column]
  );

  const fetchCheckResults = useCallback(
    (month: string, dataStreamName?: string) => {
      const startDate = month
        ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD')
        : '';
      const endDate = month
        ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD')
        : '';

      dispatch(getCheckResults(
        checkTypes,
        firstLevelActiveTab,
        {
          connection,
          schema,
          table,
          column,
          dataStreamName,
          check,
          startDate,
          endDate
        }
      ));
    },
    [check, connection, schema, table, column]
  );

  useEffect(() => {
    if (
      (job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
        job?.status === DqoJobHistoryEntryModelStatusEnum.failed)
    ) {
      refetch(filters.month, filters.dataStreamName);
    }
  }, [job?.status]);

  const openDeleteDialog = () => {
    setDeleteDataDialogOpened(true);
  };

  const onChangeDataStream = (value: string) => {
    dispatch(
      setCheckFilters(checkTypes, firstLevelActiveTab,
      check?.check_name ?? '', {
        ...filters,
        dataStreamName: value
      })
    );
    refetch(filters.month, value);
  };

  const onChangeMonth = (value: string) => {
    dispatch(
      setCheckFilters(checkTypes, firstLevelActiveTab, check?.check_name ?? '', {
        ...filters,
        month: value
      })
    );
    refetch(value, filters.dataStreamName);
  };

  const refetch = (month: string, name?: string) => {
    fetchCheckErrors(month, name);
    fetchCheckResults(month, name);
    fetchCheckReadouts(month, name);
  };

  return (
    <div
      className="my-4"
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 85}px` }}
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
            check={check}
            results={checkResults || []}
            dataStreamName={filters.dataStreamName}
            month={filters.month}
            onChangeMonth={onChangeMonth}
            onChangeDataStream={onChangeDataStream}
          />
        )}
        {activeTab === 'sensor_readouts' && (
          <SensorReadoutsTab
            sensorReadouts={sensorReadouts || []}
            dataStreamName={filters.dataStreamName}
            month={filters.month}
            onChangeMonth={onChangeMonth}
            onChangeDataStream={onChangeDataStream}
          />
        )}
        {activeTab === 'execution_errors' && (
          <CheckErrorsTab
            errors={sensorErrors || []}
            dataStreamName={filters.dataStreamName}
            month={filters.month}
            onChangeMonth={onChangeMonth}
            onChangeDataStream={onChangeDataStream}
          />
        )}

        <DeleteOnlyDataDialog
          open={deleteDataDialogOpened}
          onClose={() => setDeleteDataDialogOpened(false)}
          onDelete={(params) => {
            setDeleteDataDialogOpened(false);
            JobApiClient.deleteStoredData({
              ...check?.data_clean_job_template,
              ...params
            });
          }}
        />
      </div>
    </div>
  );
};

export default CheckDetails;
