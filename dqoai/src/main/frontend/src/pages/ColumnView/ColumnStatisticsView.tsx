import React, { useEffect, useState } from "react";
import { ColumnApiClient, TableApiClient } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import { StatisticsMetricModel, TableColumnsStatisticsModel, ColumnStatisticsModel, TableStatisticsModel } from "../../api";
import { CheckTypes } from "../../shared/routes";
import { AxiosResponse } from "axios";


const ColumnStatisticsView = () => {
  const { connection, schema, table, column }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string } = useParams();
  const [statistics, setStatistics] = useState<ColumnStatisticsModel>();
  const [tableStatistics, setTableStatistics] = useState<TableColumnsStatisticsModel>();
  const [rowCount, setRowCount] = useState<TableStatisticsModel>();

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<ColumnStatisticsModel> =
        await ColumnApiClient.getColumnStatistics(connection, schema, table, column);
      setStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };
  const fetchTable = async () => {
    try {
      const res: AxiosResponse<TableColumnsStatisticsModel> =
        await ColumnApiClient.getColumnsStatistics(connection, schema, table, column);
      setTableStatistics(res.data);
    } catch (err) {
      console.error(err);
    }
  };
  const fetchRows = async () => {
    try {
      const res: AxiosResponse<TableStatisticsModel> =
        await TableApiClient.getTableStatistics(connection, schema, table);
      setRowCount(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns()
    fetchTable()
    fetchRows()
  }, [connection, schema, table, column]);

  const datatype_detected = (numberForFile : any) => {
    if(Number(numberForFile) === 1){ return 'INTEGER'}
    if(Number(numberForFile) === 2){ return 'FLOAT'}
    if(Number(numberForFile) === 3){ return 'DATETIME'}
    if(Number(numberForFile) === 4){ return 'TIMESTAMP'}
    if(Number(numberForFile) === 5){ return 'BOOLEAN'}
    if(Number(numberForFile) === 6){ return 'STRING'}
    if(Number(numberForFile) === 7){ return 'Mixed data type'}
  }
  
  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  return (
    <div className="p-4">
    <div className="flex w-full text-xl">
      <div className="w-1/5 flex font-bold ml-5">Datatype <div className="font-light ml-5">{statistics?.type_snapshot?.column_type}</div></div>
      <div className="w-1/5 flex font-bold">Detected Datatype<div className="font-light ml-5">{datatype_detected(statistics?.statistics?.at(15)?.result)}</div></div>
      <div className="w-1/5 flex font-bold">Total Rows<div className="font-light ml-5">{renderValue(rowCount?.statistics?.at(0)?.result)}</div></div>
      <div className="w-1/4 flex font-bold">Collected at<div className="font-light ml-5">{statistics?.statistics?.at(0)?.collectedAt}</div></div>
    </div>
    </div>
  );
};

export default ColumnStatisticsView;

{/* <table>
<thead>
  <tr>
    <th className="px-4 py-2 min-w-40">Category</th>
    <th className="px-4 py-2 min-w-40">Metric</th>
    <th className="px-4 py-2 min-w-40">Metric Type</th>
    <th className="px-4 py-2 min-w-40">Metric Value</th>
    <th className="px-4 py-2 min-w-40">Collected At</th>
  </tr>
</thead>
<tbody>
{statistics.map((statistic, index) => (
  <tr key={index}>
    <td className="px-4 py-2 min-w-40">{statistic.category}</td>
    <td className="px-4 py-2 min-w-40">{statistic.collector}</td>
    <td className="px-4 py-2 min-w-40">{statistic.resultDataType}</td>
    <td className="px-4 py-2 min-w-40">{JSON.stringify(statistic.result)}</td>
    <td className="px-4 py-2 min-w-40">{statistic.collectedAt}</td>
  </tr>
))}
</tbody>
</table> */}