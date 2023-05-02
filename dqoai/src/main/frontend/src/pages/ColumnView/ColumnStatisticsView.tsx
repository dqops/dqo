import React, { useEffect, useState } from "react";
import { ColumnApiClient } from "../../services/apiClient";
import { useParams } from "react-router-dom";
import { StatisticsMetricModel } from "../../api";
import { CheckTypes } from "../../shared/routes";

const ColumnStatisticsView = () => {
  const { connection, schema, table, column }: { checkTypes: CheckTypes, connection: string, schema: string, table: string, column: string } = useParams();
  const [statistics, setStatistics] = useState<StatisticsMetricModel[]>([]);

  useEffect(() => {
    ColumnApiClient.getColumnStatistics(connection, schema, table, column).then(res => {
      setStatistics(res?.data?.statistics || [])
    });
  }, [connection, schema, table, column]);

  return (
    <div className="p-4">
      <table>
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
      </table>
    </div>
  );
};

export default ColumnStatisticsView;
