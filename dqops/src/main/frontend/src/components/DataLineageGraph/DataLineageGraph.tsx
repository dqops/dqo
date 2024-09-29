import React, { useCallback, useEffect, useState } from 'react';
import ReactDOMServer from 'react-dom/server';
import Chart from 'react-google-charts';
import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableLineageFlowModel,
  TableLineageModel
} from '../../api';
import { DataLineageApiClient } from '../../services/apiClient';
import QualityDimensionStatuses from '../DataQualityChecks/QualityDimension/QualityDimensionStatuses';
import Loader from '../Loader';
import DataLineageDetailsDialog from './DataLineageDetailsDialog';
import './DataLineageGraphStyle.css';
declare global {
  interface Window {
    showDataLineage: ((index: number) => void) | null;
  }
}

export default function DataLineageGraph({
  connection,
  schema,
  table
}: {
  connection: string;
  schema: string;
  table: string;
}) {
  const [graphArray, setGraphArray] = useState<
    (string | number | undefined)[][]
  >([]);
  const [loading, setLoading] = useState(false);
  const [severityColors, setSeverityColors] = useState<string[]>([]);
  const [detailsDialogOpen, setDetailsDialogOpen] = useState(false);
  const [tableDataLineage, setTableDataLineage] = useState<TableLineageModel>(
    {}
  );
  const [flow, setFlow] = useState<TableLineageFlowModel>({});

  const showDataLineage = useCallback(
    (index: number) => {
      const selectedFlow = tableDataLineage.flows?.[index] ?? {};
      setFlow(selectedFlow);
      setDetailsDialogOpen(true);
    },
    [tableDataLineage]
  );

  useEffect(() => {
    window.showDataLineage = showDataLineage;

    const fetchTableDataLineageGraph = async () => {
      setLoading(true);
      try {
        const response = await DataLineageApiClient.getTableDataLineageGraph(
          connection,
          schema,
          table
        );
        const data = response.data;
        setTableDataLineage(data);

        if (!data.data_lineage_fully_loaded) {
          // TODO: Show a spinner over the graph, not instead of the graph (we can show partial graphs)
          setTimeout(() => fetchTableDataLineageGraph(), 500);
        }

        const colors: string[] = [];
        const graph = data.flows?.map((flow, index) => {
          const fromTable = flow.source_table?.compact_key;
          const toTable = flow.target_table?.compact_key;
          const rowCount = flow.row_count;

          const tooltip = ReactDOMServer.renderToString(
            renderTooltip(flow, index)
          );
          const color = getColor(
            flow.upstream_combined_quality_status?.current_severity
          );
          colors.push(color);
          return [fromTable, toTable, rowCount, tooltip];
        });
        setSeverityColors(colors);
        setGraphArray(graph ?? []);
      } catch (error) {
        console.error('Error fetching data lineage graph:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchTableDataLineageGraph();

    return () => {
      window.showDataLineage = null;
    };
  }, [connection, schema, table]);

  const options = {
    tooltip: { isHtml: true },
    sankey: {
      link: { colors: [...severityColors], colorMode: 'source' },
      node: {
        colors: [...severityColors]
      }
    }
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  if (!graphArray.length) {
    return (
      <></>
    );
  }

  return (
    <div>
      <Chart
        chartType="Sankey"
        width="100%"
        height="100%"
        data={[
          ['From', 'To', 'Weight', { type: 'string', role: 'tooltip' }],
          ...graphArray
        ]}
        options={options}
      />
      <DataLineageDetailsDialog
        isOpen={detailsDialogOpen}
        onClose={() => setDetailsDialogOpen(false)}
        flow={flow}
      />
    </div>
  );
}

const renderTooltip = (flow: TableLineageFlowModel, index: number) => {
  return (
    <div className="w-100 p-2 text-sm">
      <div className="flex items-center py-1">
        <div className="w-60">Connection name</div>
        <div className="truncate">{flow.source_table?.connection_name}</div>
      </div>{' '}
      <div className="flex items-center  py-1">
        <div className="w-60">Schema name</div>
        <div className="truncate">
          {flow.source_table?.physical_table_name?.schema_name}
        </div>
      </div>{' '}
      <div className="flex items-center py-1">
        <div className="w-60">Table name</div>
        <div className="truncate">
          {flow.source_table?.physical_table_name?.table_name}
        </div>
      </div>
      <div className="flex items-center py-1">
        <div className="w-60">Upstream combined quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.upstream_combined_quality_status?.dimensions}
          />
        </div>
      </div>
      <div className="flex items-center py-1">
        <div className="w-60">Source table quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.source_table_quality_status?.dimensions}
          />
        </div>
      </div>
      <div className="flex items-center py-1">
        <div className="w-60">Target table quality status</div>
        <div>
          <QualityDimensionStatuses
            dimensions={flow.target_table_quality_status?.dimensions}
          />
        </div>
      </div>
      <div className="flex items-center py-1 underline ">
        <a href={`javascript:window.showDataLineage(${index})`}>
          Show Data Lineage Details
        </a>
      </div>
    </div>
  );
};

const getColor = (
  status:
    | CheckCurrentDataQualityStatusModelCurrentSeverityEnum
    | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
    | null
    | undefined
) => {
  switch (status) {
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.execution_error:
      return 'gray';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.fatal:
      return '#F9C1BE';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.error:
      return '#FAD8A4';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.warning:
      return '#F8F8BF';
    case CheckCurrentDataQualityStatusModelCurrentSeverityEnum.valid:
      return '#B9E4DE';
    default:
      return 'silver';
  }
};
