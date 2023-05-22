import React, { useEffect, useState } from 'react';
import { ColumnApiClient, JobApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnStatisticsModel, TableColumnsStatisticsModel } from '../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../components/SvgIcon';
import ConfirmDialog from './ConfirmDialog';
import { COLUMN_LEVEL_TABS, CONNECTION_LEVEL_TABS, PageTab, TABLE_LEVEL_TABS } from "../../shared/constants";
import { CheckTypes, ROUTES } from "../../shared/routes";
import path from 'path';
import { useDispatch } from 'react-redux/es/hooks/useDispatch';
import { useParams, useHistory } from 'react-router-dom';
import { addFirstLevelTab } from '../../redux/actions/source.actions';

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}
type NavigationMenu = {
  label: string;
  value: CheckTypes;
}
interface connectStats{
  onConnectStats: () =>void
}
const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps, onConnectStats: connectStats) => {
  const [statistics, setStatistics] = useState<TableColumnsStatisticsModel>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [selectColumn, setSelectColumn] = useState("")
  const [loadingJob, setLoadingJob] = useState(false);
  const dispatch = useDispatch();
  const { connection, schema, table, tab: activeTab, checkTypes }: { connection: string, schema: string, table: string, tab: string, checkTypes: CheckTypes } = useParams();
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
  const onChangeNavigation = async (column: string, item: NavigationMenu) => {
    const value = ROUTES.TABLE_LEVEL_VALUE("sources", connection, schema, table);
    const url = (column: string)=> ROUTES.COLUMN_LEVEL_PAGE("sources", connectionName, schemaName, tableName, column, 'detail');


    dispatch(addFirstLevelTab(item.value, {
      url,
      value,
      state: {},
      label: table
    }))

    history.push(url(column));
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
          <th className="border-b border-gray-100 text-left px-4 py-2">Hash</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">Name</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">Type</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Detected datatype
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Length
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Scale
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Null count
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Null percent
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Unique count
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Action
          </th>
        </thead>
        {statistics &&
          statistics?.column_statistics?.map((column, index) => (
            <tr key={index}>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.column_hash}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2 underline" onClick={() => navigate(column.column_name ? column.column_name : "" )}>
                {/* <a href={ROUTES.COLUMN_LEVEL_PAGE("profiling", connectionName, schemaName, tableName, column.column_name ? column.column_name : "", "detail")}> */}
                {column.column_name}
                {/* </a> */}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.column_type}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'string_datatype_detect' ? 
                        <td key={index} className="px-2 truncate">
                        {metric.result ?  datatype_detected(metric.result) : ""}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.length}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.scale}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'nulls_count' ? 
                        <td key={index} className="px-2 truncate">
                        {metric.result ?  renderValue(metric.result) : ""}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'nulls_percent' ? 
                        <td key={index} className="px-2 truncate">
                        {metric.result ?  renderValue(metric.result).toFixed(2) : ""}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
              {column?.statistics?.map((metric, index) => (
                        metric.collector === 'unique_count' ? 
                        <td key={index} className="px-2 truncate">
                        {metric.result ?  renderValue(metric.result) : ""}
                        </td>
                        : ""
                        ))}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2 ">
                <IconButton
                  size="sm"
                  className="bg-teal-500"
                  onClick={() => collectStatistics(index)}
                  >
                  <SvgIcon name="boxplot" className="w-4" />
                </IconButton>
                <IconButton
                  size="sm"
                  className="bg-teal-500"
                  onClick={() => onRemoveColumn(column)}
                  >
                  <SvgIcon name="delete" className="w-4" />
                </IconButton>
                  </td>
            
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
