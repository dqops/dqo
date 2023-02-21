import React, { useEffect, useState } from "react";
import Tabs from "../../Tabs";
import { useParams } from "react-router-dom";
import {
  CheckResultsDetailedDataModel,
  CheckSearchFiltersCheckTypeEnum, ErrorsDetailedDataModel,
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
}

const CheckDetails = ({ check, onClose }: CheckDetailsProps) => {
  const [activeTab, setActiveTab] = useState('check_results');
  const { connection, schema, table, column }: { connection: string, schema: string, table: string, column: string } = useParams();
  const [checkResults, setCheckResults] = useState<CheckResultsDetailedDataModel[]>([
    {
      "checkHash": 0,
      "checkCategory": "string",
      "checkName": "string",
      "checkDisplayName": "string",
      "checkType": "string",
      "dataStreamNames": [
        "string"
      ],
      "dataStream": "string",
      "singleCheckResults": [
        {
          "actualValue": 0,
          "expectedValue": 0,
          "warningLowerBound": 0,
          "warningUpperBound": 0,
          "errorLowerBound": 0,
          "errorUpperBound": 0,
          "fatalLowerBound": 0,
          "fatalUpperBound": 0,
          "severity": 1,
          "columnName": "string",
          "dataStream": "string",
          "durationMs": 0,
          "executedAt": 0,
          "timeGradient": "string",
          "timePeriod": "2023-02-21T17:08:00.338Z",
          "includeInKpi": true,
          "includeInSla": true,
          "provider": "string",
          "qualityDimension": "string",
          "sensorName": "string"
        },
        {
          "actualValue": 0,
          "expectedValue": 0,
          "warningLowerBound": 0,
          "warningUpperBound": 0,
          "errorLowerBound": 0,
          "errorUpperBound": 0,
          "fatalLowerBound": 0,
          "fatalUpperBound": 0,
          "severity": 1,
          "columnName": "string",
          "dataStream": "string",
          "durationMs": 0,
          "executedAt": 0,
          "timeGradient": "string",
          "timePeriod": "2023-02-21T17:08:00.338Z",
          "includeInKpi": true,
          "includeInSla": true,
          "provider": "string",
          "qualityDimension": "string",
          "sensorName": "string"
        },
        {
          "actualValue": 0,
          "expectedValue": 0,
          "warningLowerBound": 0,
          "warningUpperBound": 0,
          "errorLowerBound": 0,
          "errorUpperBound": 0,
          "fatalLowerBound": 0,
          "fatalUpperBound": 0,
          "severity": 3,
          "columnName": "string",
          "dataStream": "string",
          "durationMs": 0,
          "executedAt": 0,
          "timeGradient": "string",
          "timePeriod": "2023-02-21T17:08:00.338Z",
          "includeInKpi": true,
          "includeInSla": true,
          "provider": "string",
          "qualityDimension": "string",
          "sensorName": "string"
        },
        {
          "actualValue": 0,
          "expectedValue": 0,
          "warningLowerBound": 0,
          "warningUpperBound": 0,
          "errorLowerBound": 0,
          "errorUpperBound": 0,
          "fatalLowerBound": 0,
          "fatalUpperBound": 0,
          "severity": 1,
          "columnName": "string",
          "dataStream": "string",
          "durationMs": 0,
          "executedAt": 0,
          "timeGradient": "string",
          "timePeriod": "2023-02-21T17:08:00.338Z",
          "includeInKpi": true,
          "includeInSla": true,
          "provider": "string",
          "qualityDimension": "string",
          "sensorName": "string"
        },
        {
          "actualValue": 0,
          "expectedValue": 0,
          "warningLowerBound": 0,
          "warningUpperBound": 0,
          "errorLowerBound": 0,
          "errorUpperBound": 0,
          "fatalLowerBound": 0,
          "fatalUpperBound": 0,
          "severity": 2,
          "columnName": "string",
          "dataStream": "string",
          "durationMs": 0,
          "executedAt": 0,
          "timeGradient": "string",
          "timePeriod": "2023-02-21T17:08:00.338Z",
          "includeInKpi": true,
          "includeInSla": true,
          "provider": "string",
          "qualityDimension": "string",
          "sensorName": "string"
        },
      ]
    }
  ]);
  const [sensorReadouts, setSensorReadouts] = useState<SensorReadoutsDetailedDataModel[]>([]);
  const [errors, setErrors] = useState<ErrorsDetailedDataModel[]>([]);
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [dataStreamName, setDataStreamName] = useState<string>();
  const [month, setMonth] = useState(moment().format('MMMM YYYY'));

  const { sidebarWidth } = useTree();

  useEffect(() => {
    const startDate = month ? moment(month, 'MMMM YYYY').startOf('month').format('YYYY-MM-DD') : '';
    const endDate = month ? moment(month, 'MMMM YYYY').endOf('month').format('YYYY-MM-DD') : '';

    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.adhoc) {
      if (column) {
        CheckResultApi.getColumnAdHocChecksResults(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          // setCheckResults(res.data);
        });
        SensorReadoutsApi.getColumnAdHocSensorReadouts(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getColumnAdHocErrors(connection, schema, table, column, dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      } else {
        CheckResultApi.getTableAdHocChecksResults(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          // setCheckResults(res.data);
        });
        SensorReadoutsApi.getTableAdHocSensorReadouts(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getTableAdHocErrors(connection, schema, table, dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.checkpoint) {
      if (column) {
        CheckResultApi.getColumnCheckpointsResults(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          // setCheckResults(res.data);
        });
        SensorReadoutsApi.getColumnCheckpointsSensorReadouts(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getColumnCheckpointsErrors(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      } else {
        CheckResultApi.getTableCheckpointsResults(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setCheckResults(res.data);
        });
        SensorReadoutsApi.getTableCheckpointsSensorReadouts(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getTableCheckpointsErrors(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      }
    }
    if (check.run_checks_job_template?.checkType === CheckSearchFiltersCheckTypeEnum.partitioned) {
      if (column) {
        CheckResultApi.getColumnPartitionedChecksResults(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          // setCheckResults(res.data);
        });
        SensorReadoutsApi.getColumnPartitionedSensorReadouts(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getColumnPartitionedErrors(connection, schema, table, column, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      } else {
        CheckResultApi.getTablePartitionedChecksResults(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setCheckResults(res.data);
        });
        SensorReadoutsApi.getTablePartitionedSensorReadouts(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setSensorReadouts(res.data);
        });
        ErrorsApi.getTablePartitionedErrors(connection, schema, table, check.run_checks_job_template?.timeScale || 'daily', dataStreamName, startDate, endDate).then((res) => {
          setErrors(res.data);
        });
      }
    }
  }, [check, dataStreamName, connection, schema, table, column, month]);

  const openDeleteDialog = () => {
    setDeleteDataDialogOpened(true);
  };

  const onChangeDataStream = (name: string) => {
    setDataStreamName(name);
  }

  return (
    <div className="my-4" style={{ maxWidth: `calc(100vw - ${sidebarWidth + 80}px` }}>
      <div className="bg-white px-4 py-6 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>

        <IconButton
          className="absolute right-16 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={openDeleteDialog}
        >
          <SvgIcon name="delete" className="w-5" />
        </IconButton>

        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        {activeTab === 'check_results' && (
          <CheckResultsTab
            results={checkResults}
            dataStreamName={dataStreamName}
            month={month}
            onChangeMonth={setMonth}
            onChangeDataStream={onChangeDataStream}
          />
        )}
        {activeTab === 'sensor_readouts' && (
          <SensorReadoutsTab
            sensorReadouts={sensorReadouts}
            dataStreamName={dataStreamName}
            month={month}
            onChangeMonth={setMonth}
            onChangeDataStream={onChangeDataStream}
          />
        )}
        {activeTab === 'execution_errors' && (
          <CheckErrorsTab
            errors={errors}
            dataStreamName={dataStreamName}
            month={month}
            onChangeMonth={setMonth}
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
