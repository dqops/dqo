import React, { useCallback, useEffect, useState } from "react";
import Tabs from "../../Tabs";
import { useParams } from "react-router-dom";
import {
  CheckResultsDetailedDataModel,
  CheckSearchFiltersCheckTypeEnum,
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum,
  ErrorsDetailedDataModel,
  SensorReadoutsDetailedDataModel,
  UICheckModel
} from "../../../api";
import { CheckResultApi, ErrorsApi, JobApiClient, SensorReadoutsApi } from "../../../services/apiClient";
import CheckResultsTab from "./CheckResultsTab";
import IconButton from "../../IconButton";
import SvgIcon from "../../SvgIcon";
import { useTree } from "../../../contexts/treeContext";
import SensorReadoutsTab from "./SensorReadoutsTab";
import CheckErrorsTab from "./CheckErrorsTab";
import DeleteOnlyDataDialog from "../../CustomTree/DeleteOnlyDataDialog";
import moment from "moment/moment";
import { useActionDispatch } from "../../../hooks/useActionDispatch";
import { CheckTypes } from "../../../shared/routes";
import {
  setCheckFilters,
  setCheckResults,
  setSensorErrors,
  setSensorReadouts
} from "../../../redux/actions/source.actions";
import { useSelector } from "react-redux";
import { getFirstLevelState } from "../../../redux/selectors";

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
  check: UICheckModel;
  onClose: () => void;
  job?: DqoJobHistoryEntryModel;
}

const CheckDetails = ({ check, onClose, job }: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const { checkTypes, connection, schema, table, column }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string } = useParams();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const {
    checkResults: resultsData,
    sensorReadouts: readoutsData,
    sensorErrors: errorsData,
    checkFilters: filtersData,
  } = useSelector(getFirstLevelState(checkTypes));

  const checkResults = resultsData ? resultsData[check?.check_name ?? ""] || [] : [];
  const sensorReadouts = readoutsData ? readoutsData[check?.check_name ?? ""] || [] : [];
  const sensorErrors = errorsData ? errorsData[check?.check_name ?? ""] || [] : [];
  const filters = filtersData ? filtersData[check?.check_name ?? ""] || {} : {};

  const dispatch = useActionDispatch();

  const { sidebarWidth } = useTree();

  const getCheckResult = (data: CheckResultsDetailedDataModel[]): CheckResultsDetailedDataModel[] => {
    return data.filter((item) => item.checkName === check.check_name);
  };

  const getSensorReadout = (data: SensorReadoutsDetailedDataModel[]): SensorReadoutsDetailedDataModel[] => {
    return data.filter((item) => item.singleSensorReadouts && item.singleSensorReadouts[0]?.checkName === check.check_name);
  };

  const getErrorItem = (data: ErrorsDetailedDataModel[]): ErrorsDetailedDataModel[] => {
    return data.filter((item) => item.checkName === check.check_name);
  };

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

  const fetchCheckErrors = useCallback((month: string, dataStreamName?: string) => {
    const startDate = month ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD') : '';
    const endDate = month ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD') : '';

    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.profiling) {
      if (column) {
        ErrorsApi.getColumnProfilingErrors(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      } else {
        ErrorsApi.getTableProfilingErrors(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.recurring) {
      if (column) {
        ErrorsApi.getColumnRecurringErrors(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      } else {
        ErrorsApi.getTableRecurringErrors(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.partitioned) {
      if (column) {
        ErrorsApi.getColumnPartitionedErrors(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      } else {
        ErrorsApi.getTablePartitionedErrors(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorErrors(checkTypes, check?.check_name ?? "", getErrorItem(res.data)));
        });
      }
    }
  }, [check, connection, schema, table, column]);

  const fetchCheckReadouts = useCallback((month: string, dataStreamName?: string) => {
    const startDate = month ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD') : '';
    const endDate = month ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD') : '';

    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.profiling) {
      if (column) {
        SensorReadoutsApi.getColumnProfilingSensorReadouts(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      } else {
        SensorReadoutsApi.getTableProfilingSensorReadouts(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.recurring) {
      if (column) {
        SensorReadoutsApi.getColumnRecurringSensorReadouts(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      } else {
        SensorReadoutsApi.getTableRecurringSensorReadouts(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.partitioned) {
      if (column) {
        SensorReadoutsApi.getColumnPartitionedSensorReadouts(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      } else {
        SensorReadoutsApi.getTablePartitionedSensorReadouts(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setSensorReadouts(checkTypes, check?.check_name ?? "", getSensorReadout(res.data)));
        });
      }
    }
  }, [check, connection, schema, table, column]);

  const fetchCheckResults = useCallback((month: string, dataStreamName?: string) => {
    const startDate = month ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD') : '';
    const endDate = month ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD') : '';

    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.profiling) {
      if (column) {
        CheckResultApi.getColumnProfilingChecksResults(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      } else {
        CheckResultApi.getTableProfilingChecksResults(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.recurring) {
      if (column) {
        CheckResultApi.getColumnRecurringChecksResults(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      } else {
        CheckResultApi.getTableRecurringChecksResults(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.partitioned) {
      if (column) {
        CheckResultApi.getColumnPartitionedChecksResults(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      } else {
        CheckResultApi.getTablePartitionedChecksResults(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          dispatch(setCheckResults(checkTypes, check?.check_name ?? "", getCheckResult(res.data)))
        });
      }
    }
  }, [check, connection, schema, table, column]);

  useEffect(() => {
    if(job && (job.status === DqoJobHistoryEntryModelStatusEnum.succeeded || job.status === DqoJobHistoryEntryModelStatusEnum.failed)) {
      refetch(filters.month, filters.dataStreamName);
    }
  }, [job]);

  const openDeleteDialog = () => {
    setDeleteDataDialogOpened(true);
  };

  const onChangeDataStream = (value: string) => {
    dispatch(setCheckFilters(checkTypes, check?.check_name ?? "", {
      ...filters,
      dataStreamName: value,
    }))
    refetch(filters.month, value);
  }

  const onChangeMonth = (value: string) => {
    dispatch(setCheckFilters(checkTypes, check?.check_name ?? "", {
      ...filters,
      month: value,
    }))
    refetch(value, filters.dataStreamName);
  }

  const refetch = (month: string, name?: string) => {
    fetchCheckErrors(month, name);
    fetchCheckResults(month, name);
    fetchCheckResults(month, name);
  }

  return (
    <div className="my-4" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 85}px` }}>
      <div className="bg-white px-4 py-6 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>

        {!!checkResults.length && (
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
              ...check.data_clean_job_template,
              ...params,
            });
          }}
        />
      </div>
    </div>
  );
};

export default CheckDetails;
