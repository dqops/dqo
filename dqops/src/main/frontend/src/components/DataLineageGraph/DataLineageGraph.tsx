import React, { useEffect, useRef, useState } from 'react';
import ReactDOMServer from 'react-dom/server';
import Chart from 'react-google-charts';
import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableLineageFlowModel,
  TableLineageModel
} from '../../api';
import { DataLineageApiClient } from '../../services/apiClient';
import Button from '../Button';
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
  table,
  configureSourceTables
}: {
  connection: string;
  schema: string;
  table: string;
  configureSourceTables: () => void;
}) {
  const [graphArray, setGraphArray] = useState<
    (string | number | undefined)[][]
  >([]);
  const [loading, setLoading] = useState(false);
  const [detailsDialogOpen, setDetailsDialogOpen] = useState(false);
  const [flow, setFlow] = useState<TableLineageFlowModel>({});

  const tableDataLineageRef = useRef<TableLineageModel | null>(null);

  const showDataLineage = (index: number) => {
    const tableDataLineage = tableDataLineageRef.current;
    if (tableDataLineage && tableDataLineage.flows) {
      const selectedFlow = tableDataLineage.flows[index] ?? {};
      setFlow(selectedFlow);
      setDetailsDialogOpen(true);
    }
  };

  useEffect(() => {
    window.showDataLineage = showDataLineage;
    let timer : NodeJS.Timeout;

    const fetchTableDataLineageGraph = async () => {
      setLoading(true);
      try {
        const response = await DataLineageApiClient.getTableDataLineageGraph(
          connection,
          schema,
          table
        );
        const data = response.data;


        tableDataLineageRef.current = data;

        if (!data.data_lineage_fully_loaded) {
          timer = setTimeout(() => fetchTableDataLineageGraph(), 500);
        }

        const colors: string[] = [];
        const graph = data.flows?.reverse().map((flow, index) => {
          const fromTable = flow.source_table?.compact_key;
          const toTable = flow.target_table?.compact_key;
          const weight =
            flow.row_count && flow.row_count > 10000
              ? Math.sqrt(Math.sqrt(flow.row_count))
              : 1;

          const tooltip = ReactDOMServer.renderToString(
            renderTooltip(flow, index)
          );
          const color = getColor(
            flow.upstream_combined_quality_status?.current_severity
          );
          colors.push(color);
          return [
            fromTable,
            toTable,
            weight,
            tooltip,
            'fill-color: ' + color + ';'
          ];
        });
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
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [connection, schema, table]);

  const options = {
    tooltip: { isHtml: true },
    sankey: {
      iterations: 32,
      node: {
        colors: ['#222222'],
        nodePadding: 30
      }
    }
  };

  return (
    <div className="relative w-full h-full">
      {loading && (
        <div className="absolute inset-0 z-10 flex justify-center items-center bg-white bg-opacity-70">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      )}

      {graphArray && graphArray.length > 0 ? (
        <Chart
          chartType="Sankey"
          width="100%"
          height="600px"
          data={[
            [
              'From',
              'To',
              'Weight',
              { type: 'string', role: 'tooltip' },
              { type: 'string', role: 'style' }
            ],
            ...graphArray
          ]}
          options={options}
        />
      ) : (
        !loading && (
          <Button
            label="Configure source tables"
            color="primary"
            className="!w-50 !my-5 ml-4"
            onClick={() => configureSourceTables()}
          />
        )
      )}

      <DataLineageDetailsDialog
        isOpen={detailsDialogOpen}
        onClose={() => {
          setDetailsDialogOpen(false), setFlow({});
        }}
        flow={flow}
      />
    </div>
  );
}

const renderTooltip = (flow: TableLineageFlowModel, index: number) => {
  return (
    <div className="w-130 p-2 text-sm">
      <div className="flex items-center py-1">
        <div className="!w-60 !max-w-60 !min-w-60 ">Source connection name</div>
        <div className="truncate">{flow.source_table?.connection_name}</div>
      </div>{' '}
      <div className="flex items-center  py-1">
        <div className="!w-60 !max-w-60 !min-w-60 ">Source schema name</div>
        <div className="truncate">
          {flow.source_table?.physical_table_name?.schema_name}
        </div>
      </div>{' '}
      <div className="flex items-center py-1">
        <div className="w-60 !max-w-60 !min-w-60">Source table name</div>
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
