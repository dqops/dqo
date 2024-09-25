import React, { useEffect } from 'react';
import ReactDOMServer from 'react-dom/server';
import Chart from 'react-google-charts';
import { TableLineageFlowModel, TableLineageModel } from '../../api';
import { DataLineageApiClient } from '../../services/apiClient';
import { useDecodedParams } from '../../utils';
import QualityDimensionStatuses from '../DataQualityChecks/QualityDimension/QualityDimensionStatuses';

export default function DataLineageGraph() {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const [tableDataLineageGraph, setTableDataLineageGraph] =
    React.useState<TableLineageModel>({});
  const [graphArray, setGraphArray] = React.useState<
    (string | number | undefined)[][]
  >([]);

  const fetchTableDataLineageGraph = async () => {
    try {
      const response = await DataLineageApiClient.getTableDataLineageGraph(
        connection,
        schema,
        table
      );
      const data = response.data;
      setTableDataLineageGraph(data);

      const incompleteData = data.flows?.some((flow) => {
        return (
          !flow.source_table_quality_status?.table_exist ||
          !flow.target_table_quality_status?.table_exist ||
          !flow.upstream_combined_quality_status?.table_exist
        );
      });

      if (incompleteData) {
        setTimeout(() => fetchTableDataLineageGraph(), 5000);
        return;
      }

      const graph = data.flows?.map((flow) => {
        const fromTable = data.relative_table?.compact_key;
        const toTable = flow.source_table?.compact_key;
        const weight = flow.weight;

        const tooltip = ReactDOMServer.renderToString(renderTooltip(flow));

        return [toTable, fromTable, weight, tooltip];
      });

      setGraphArray(graph ?? []);
    } catch (error) {
      console.error('Error fetching data lineage graph:', error);
    }
  };

  useEffect(() => {
    fetchTableDataLineageGraph();
  }, [connection, schema, table]);

  const options = {
    tooltip: { isHtml: true } // Enable HTML tooltips
  };

  return (
    <div>
      <Chart
        chartType="Sankey"
        width="60%"
        height="300px"
        data={[
          ['From', 'To', 'Weight', { type: 'string', role: 'tooltip' }],
          ...graphArray
        ]}
        options={options}
      />
    </div>
  );
}

// Tooltip rendering component
const renderTooltip = (flow: TableLineageFlowModel) => {
  return (
    <div className="w-85 p-2 text-sm">
      <div className="flex items-center justify-between py-1">
        <div className="w-60">Upstream combined quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.upstream_combined_quality_status?.dimensions}
          />
        </div>
      </div>
      <div className="flex items-center justify-between py-1">
        <div>Source table quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.source_table_quality_status?.dimensions}
          />
        </div>
      </div>
      <div className="flex items-center justify-between py-1">
        <div>Target table quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.target_table_quality_status?.dimensions}
          />
        </div>
      </div>
    </div>
  );
};
