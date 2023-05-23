import React, { useEffect, useState } from 'react';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnStatisticsModel, TableColumnsStatisticsModel } from '../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../components/SvgIcon';
import ConfirmDialog from './ConfirmDialog';
import { CheckTypes, ROUTES } from "../../shared/routes";
import { useDispatch } from 'react-redux/es/hooks/useDispatch';
import { useParams, useHistory } from 'react-router-dom';
import { addFirstLevelTab } from '../../redux/actions/source.actions';

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps) => {
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [loadingJob, setLoadingJob] = useState(false);
  const dispatch = useDispatch();
  const { connection, schema, table }: { connection: string, schema: string, table: string, tab: string, checkTypes: CheckTypes } = useParams();
  const history = useHistory();
  
  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(connectionName, schemaName, tableName);
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns().then();
  }, [connectionName, schemaName, tableName]);

  const onRemoveColumn = (column: ColumnStatisticsModel) => {
    setIsOpen(true);
    setSelectedColumn(column);
  };

  const navigate = (column: string) =>{
    const url =  ROUTES.COLUMN_LEVEL_PAGE("sources", connectionName, schemaName, tableName, column, 'detail');
    const value = ROUTES.COLUMN_LEVEL_VALUE("sources", connection, schema, table, column)
    dispatch(addFirstLevelTab(CheckTypes.SOURCES, {
      url,
      value,
      state: {},
      label: table
    }))
    history.push(url)
  }
 
  const removeColumn = async () => {
    if (selectedColumn?.column_name) {
      await ColumnApiClient.deleteColumn(
        connectionName,
        schemaName,
        tableName,
        selectedColumn?.column_name
      );
      await fetchColumns();
    }
  };

  const collectStatistics = async (index : number) => {
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnDataStreams(statistics?.column_statistics?.at(index)?.collect_column_statistics_job_template);
    } finally {
      setLoadingJob(false);
    }
  };
   const collectAllStatistics = async () => {
    try {
      setLoadingJob(true);
      await JobApiClient.collectStatisticsOnDataStreams(statistics?.collect_column_statistics_job_template);
    } finally {
      setLoadingJob(false);
    }
  };

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const datatype_detected = (numberForFile : any) => {
    if(Number(numberForFile) === 1){ return 'INTEGER'}
    if(Number(numberForFile) === 2){ return 'FLOAT'}
    if(Number(numberForFile) === 3){ return 'DATETIME'}
    if(Number(numberForFile) === 4){ return 'TIMESTAMP'}
    if(Number(numberForFile) === 5){ return 'BOOLEAN'}
    if(Number(numberForFile) === 6){ return 'STRING'}
    if(Number(numberForFile) === 7){ return 'Mixed data type'}
  }
  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-full">
        <thead>
          <th className="border-b border-gray-100 text-left px-4 py-2">Name</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">Type</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Detected datatype
          </th>
          <th className="border-b border-gray-100 text-right px-4 py-2">
            Length
          </th>
          <th className="border-b border-gray-100 text-right px-4 py-2">
            Scale
          </th>
          <th className="border-b border-gray-100 text-right px-4 py-2">
            Null count
          </th>
          <th className="border-b border-gray-100 text-right px-4 py-2">
            Null percent
          </th>
          <th className="border-b border-gray-100 text-right px-4 py-2">
            Unique count
          </th>
          <th className="border-b border-gray-100 text-right px-7.5 py-2">
            Action
          </th>
        </thead>
        {statistics &&
          statistics?.column_statistics?.map((column, index) => (
            <tr key={index}>
              <td className="border-b border-gray-100 text-left px-4 py-2 underline cursor-pointer" 
              onClick={() => navigate(column.column_name ? column.column_name : "" )}>
                {column.column_name}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.column_type}
              </td>
              <td className="border-b border-gray-100 text-right px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'string_datatype_detect' ? 
                        <td key={index} className="truncate">
                        {metric.result ?  datatype_detected(metric.result) : ""}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-right px-4 py-2">
                <th className='float-right'>
                {column.type_snapshot?.length}
                </th>
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                
              <th className='float-right'>
                {column.type_snapshot?.scale}
                </th>
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'nulls_count' ? 
                        <td key={index} className="text-right float-right">
                        {metric.result ?  renderValue(metric.result) : "0"}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-right px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'nulls_percent' ? 
                        <td key={index} className="truncate float-right" >
                        {metric.result ? (renderValue(metric.result) ==="0" ? "0%" 
                        : renderValue(metric.result).toFixed(2))+"%"
                        : "0%"}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'unique_count' ? 
                        <td key={index} className="truncate float-right">
                        {metric.result ?  renderValue(metric.result) : ""}
                        </td>
                        : ""
                        ))}
              </td>
                
              <td className="border-b border-gray-100 text-right px-4 py-2 ">
                <IconButton
                  size="sm"
                  className="group bg-teal-500 ml-1.5"
                  onClick={() => collectStatistics(index)}
                  >
                  <SvgIcon name="boxplot" className="w-4 white" />
                <td className="hidden absolute right-3 bottom-3 p-2 bg-black text-white normal-case rounded-md group-hover:block whitespace-nowrap">
                 Collect statistic</td>
                </IconButton>
                
                <IconButton
                  size="sm"
                  className="group bg-teal-500 ml-3"
                  onClick={() => onRemoveColumn(column)}
                  >
                  <SvgIcon name="delete" className="w-4" />
                
                  <div className="hidden absolute right-3 bottom-3 p-2 normal-case bg-black text-white rounded-md group-hover:block whitespace-nowrap">
                  Click to delete</div>
                </IconButton>
              </td>
            <div></div>
            </tr>
          ))}
      </table>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        column={selectedColumn}
        onConfirm={removeColumn}
      />
    </div>
  );
};

export default TableColumns;

