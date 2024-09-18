import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  DimensionCurrentDataQualityStatusModel,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableLineageSourceListModel
} from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { DataLineageApiClient } from '../../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { sortPatterns, useDecodedParams } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import Loader from '../../../Loader';
import ClientSidePagination from '../../../Pagination/ClientSidePagination';
import SvgIcon from '../../../SvgIcon';
import { getDimensionColor } from '../TableQualityStatus/TableQualityStatusUtils';
import moment from 'moment';

const HEADER_ELEMENTS = [
  { label: 'Source connection', key: 'source_connection' },
  { label: 'Source schema', key: 'source_schema' },
  { label: 'Source table', key: 'source_table' },
  { label: 'Action', key: 'action' }
];

export default function SourceTablesTable({
  connection: parentConnection,
  schema: parentSchema,
  table: parentTable,
  setSourceTableEdit,
  showHeader: showHeader = true
}: {
  connection?: string;
  schema?: string;
  table?: string;
  setSourceTableEdit?: (
    obj: { connection: string; schema: string; table: string } | null
  ) => void;
  showHeader?: boolean;
}) {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } =
    parentConnection && parentSchema && parentTable
      ? {
          connection: parentConnection,
          schema: parentSchema,
          table: parentTable
        }
      : useDecodedParams();

  const [dir, setDir] = useState<'asc' | 'desc'>('asc');
  const [sorceTableToDelete, setSorceTableToDelete] = useState<{
    connection: string;
    schema: string;
    table: string;
  } | null>(null);
  const [indexSortingElement, setIndexSortingElement] = useState(1);
  const [displayedTables, setDisplayedTables] = useState<
    TableLineageSourceListModel[]
  >([]);
  const [expandedLineage, setExpandedLineage] = useState<
    { connection: string; schema: string; table: string }[] | null
  >(null);
  const [tables, setTables] = useState<TableLineageSourceListModel[]>([]);
  const [loading, setLoading] = useState(false);

  const getTables = async () => {
    setLoading(true);
    DataLineageApiClient.getTableSourceTables(connection, schema, table)
      .then((res) => {
        setTables(res.data);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    getTables();
  }, [connection, schema, table]);

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    setDisplayedTables(
      sortPatterns(
        tables,
        elem.key as keyof TableLineageSourceListModel,
        newDir
      )
    );
    setDir(newDir);
    setIndexSortingElement(index);
  };

  const handleDeleteSourceTable = (
    source: { connection: string; schema: string; table: string } | null
  ) => {
    DataLineageApiClient.deleteTableSourceTable(
      connection,
      schema,
      table,
      source?.connection ?? '',
      source?.schema ?? '',
      source?.table ?? ''
    ).then(() => {
      const newTables = tables.filter(
        (t) =>
          !(
            t.source_connection === source?.connection &&
            t.source_schema === source?.schema &&
            t.source_table === source?.table
          )
      );
      setDisplayedTables(newTables);
    });
  };

  const dispatch = useActionDispatch();
  const history = useHistory();

  const goTable = (dataLineage: TableLineageSourceListModel) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? '',
      'detail'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      dataLineage.source_connection ?? '',
      dataLineage.source_schema ?? '',
      dataLineage.source_table ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        label: dataLineage.source_table ?? '',
        state: {}
      })
    );
    history.push(url);
  };

  const onChangeExpandedLineage = (lineage: TableLineageSourceListModel) => {
    const newExpandedLineage = [...(expandedLineage ?? [])];
    if (
      newExpandedLineage.find(
        (expandedLiceage) =>
          expandedLiceage.connection === lineage.source_connection &&
          expandedLiceage.schema === lineage.source_schema &&
          expandedLiceage.table === lineage.source_table
      )
    ) {
      newExpandedLineage.splice(
        newExpandedLineage.findIndex(
          (expandedLiceage) =>
            expandedLiceage.connection === lineage.source_connection &&
            expandedLiceage.schema === lineage.source_schema &&
            expandedLiceage.table === lineage.source_table
        ),
        1
      );
    } else {
      newExpandedLineage.push({
        connection: lineage.source_connection ?? '',
        schema: lineage.source_schema ?? '',
        table: lineage.source_table ?? ''
      });
    }
    setExpandedLineage(newExpandedLineage);
  };

  const getBasicDimmensionsKeys = (
    column: TableLineageSourceListModel,
    type: string
  ) => {
    const basicDimensions = Object.keys(
      column?.source_table_data_quality_status?.dimensions ?? {}
    )?.find((x) => x === type);
    return basicDimensions;
  };
  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const getAdditionalDimentionsKeys = (column: TableLineageSourceListModel) => {
    return (
      Object.keys(
        column?.source_table_data_quality_status?.dimensions ?? {}
      )?.filter((x) => !basicDimensionTypes.includes(x)) ?? []
    );
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <>
      <table className="text-sm w-full">
        {showHeader && (
          <thead>
            <tr>
              <th></th>
              <th></th>
              {HEADER_ELEMENTS.map((elem, index) => {
                if (elem.key === 'action' && !setSourceTableEdit) {
                  return null;
                }
                return (
                  <th key={index} onClick={() => handleSort(elem, index)}>
                    <div
                      className={clsx(
                        'flex items-center gap-x-1 px-4',
                        elem.key === 'action' && 'ml-10'
                      )}
                    >
                      {elem.label}
                      {elem.key !== 'action' && (
                        <div>
                          {!(indexSortingElement === index && dir === 'asc') ? (
                            <SvgIcon
                              name="chevron-up"
                              className="w-2 h-2 text-black cursor-pointer"
                              onClick={() => handleSort(elem, index)}
                            />
                          ) : (
                            <div className="w-2 h-2" />
                          )}
                          {!(
                            indexSortingElement === index && dir === 'desc'
                          ) ? (
                            <SvgIcon
                              name="chevron-down"
                              className="w-2 h-2 text-black cursor-pointer"
                              onClick={() => handleSort(elem, index)}
                            />
                          ) : (
                            <div className="w-2 h-2" />
                          )}
                        </div>
                      )}
                    </div>
                  </th>
                );
              })}
            </tr>
          </thead>
        )}
        <tbody className={clsx('', showHeader && 'border-t border-gray-100')}>
          {displayedTables.map((table, index) => (
            <React.Fragment key={index}>
              <tr className="h-10">
                <td
                  onClick={() => onChangeExpandedLineage(table)}
                  className="cursor-pointer px-4"
                >
                  {expandedLineage?.find(
                    (lineage) =>
                      lineage.connection === table.source_connection &&
                      lineage.schema === table.source_schema &&
                      lineage.table === table.source_table
                  ) ? (
                    <SvgIcon name="chevron-down" className="w-4" />
                  ) : (
                    <SvgIcon name="chevron-right" className="w-4" />
                  )}
                </td>
                <td>
                  {' '}
                  <div className="flex items-center gap-x-0.5">
                    {basicDimensionTypes.map((dimType) => {
                      const dimensionKey = getBasicDimmensionsKeys(
                        table,
                        dimType
                      );
                      const currentSeverity = (table
                        ?.source_table_data_quality_status?.dimensions ?? {})?.[
                        dimensionKey as any
                      ]?.current_severity;
                      const lastCheckExecutedAt = (table
                        ?.source_table_data_quality_status?.dimensions ?? {})?.[
                        dimensionKey as any
                      ]?.last_check_executed_at;
                      const severityColor = getDimensionColor(
                        currentSeverity as any
                      );
                      const hasNoSeverity = severityColor.length === 0;

                      const dimensionsClassNames = clsx(
                        'w-3 h-3 border border-gray-150',
                        {
                          'bg-gray-150': hasNoSeverity && lastCheckExecutedAt,
                          [severityColor]: !hasNoSeverity
                        }
                      );
                      return (
                        <Tooltip
                          key={`Dimensionindex${dimType}`}
                          content={renderSecondLevelTooltip(
                            (table?.source_table_data_quality_status
                              ?.dimensions ?? {})?.[dimensionKey as any] ?? {
                              dimension: dimType
                            }
                          )}
                        >
                          <div
                            className={dimensionsClassNames}
                            style={{ borderRadius: '6px' }}
                          />
                        </Tooltip>
                      );
                    })}
                    {getAdditionalDimentionsKeys(table).map(
                      (dimensionKey: string, dimIndex) => {
                        return (
                          <Tooltip
                            key={`DimensionTooltipindex${dimIndex}`}
                            content={renderSecondLevelTooltip(
                              (table?.source_table_data_quality_status
                                ?.dimensions ?? {})?.[dimensionKey as any]
                            )}
                          >
                            <div
                              className={clsx(
                                'w-3 h-3 border border-gray-150',
                                getDimensionColor(
                                  (table?.source_table_data_quality_status
                                    ?.dimensions ?? {})?.[dimensionKey as any]
                                    ?.current_severity as
                                    | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                                    | undefined
                                ).length === 0
                                  ? 'bg-gray-150'
                                  : getDimensionColor(
                                      (table?.source_table_data_quality_status
                                        ?.dimensions ?? {})?.[
                                        dimensionKey as any
                                      ]?.current_severity as
                                        | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                                        | undefined
                                    )
                              )}
                              style={{ borderRadius: '6px' }}
                            />
                          </Tooltip>
                        );
                      }
                    )}
                  </div>
                </td>
                <td className="px-4">{table.source_connection}</td>
                <td className="px-4">{table.source_schema}</td>
                <td className="px-4">{table.source_table}</td>
                {setSourceTableEdit && (
                  <td className="px-4">
                    <div className="flex items-center gap-x-4">
                      <IconButton
                        ripple={false}
                        onClick={() =>
                          setSourceTableEdit &&
                          setSourceTableEdit({
                            connection: table.source_connection ?? '',
                            schema: table.source_schema ?? '',
                            table: table.source_table ?? ''
                          })
                        }
                        size="sm"
                        color="teal"
                        className={clsx(
                          '!shadow-none hover:!shadow-none hover:bg-[#028770]'
                        )}
                      >
                        <SvgIcon name="edit" className="w-4" />
                      </IconButton>
                      <IconButton
                        onClick={() =>
                          setSorceTableToDelete({
                            connection: table.source_connection ?? '',
                            schema: table.source_schema ?? '',
                            table: table.source_table ?? ''
                          })
                        }
                        size="sm"
                        color="teal"
                        className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      >
                        <SvgIcon name="delete" className="w-4" />
                      </IconButton>
                      <IconButton
                        onClick={() => goTable(table)}
                        size="sm"
                        color="teal"
                        className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                      >
                        <SvgIcon name="data_sources_white" className="w-5" />
                      </IconButton>
                    </div>
                  </td>
                )}
              </tr>
              {expandedLineage?.find(
                (lineage) =>
                  lineage.connection === table.source_connection &&
                  lineage.schema === table.source_schema &&
                  lineage.table === table.source_table &&
                  table.source_table_data_quality_status?.table_exist
              ) && (
                <tr>
                  <td colSpan={5} className="pl-4 pt-4">
                    <SourceTablesTable
                      connection={table.source_connection}
                      schema={table.source_schema}
                      table={table.source_table}
                      showHeader={false}
                    />
                  </td>
                </tr>
              )}
            </React.Fragment>
          ))}
        </tbody>
      </table>

      <ConfirmDialog
        open={!!sorceTableToDelete}
        onConfirm={async () => {
          handleDeleteSourceTable(sorceTableToDelete);
          setSorceTableToDelete(null);
        }}
        onClose={() => setSorceTableToDelete(null)}
        message={`Are you sure you want to delete this data lineage?`}
      />
      <div className="px-4">
        <ClientSidePagination
          items={tables}
          onChangeItems={setDisplayedTables}
          className="pl-0 !w-full pr-4"
        />
      </div>
    </>
  );
}

const renderSecondLevelTooltip = (
  data: DimensionCurrentDataQualityStatusModel | undefined
) => {
  if (data && data.last_check_executed_at) {
    return (
      <div>
        <div className="flex gap-x-2">
          <div className="w-49">Last executed at:</div>
          <div>
            {moment(data?.last_check_executed_at).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Current severity level:</div>
          <div>{data?.current_severity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Highest historical severity level:</div>
          <div>{data?.highest_historical_severity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Quality Dimension:</div>
          <div>{data?.dimension}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Executed checks:</div>
          <div>{data?.executed_checks}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Valid results:</div>
          <div>{data?.valid_results}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Warnings:</div>
          <div>{data?.warnings}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Errors:</div>
          <div>{data?.errors}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Fatals:</div>
          <div>{data?.fatals}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Data quality KPI:</div>
          <div>
            {data.data_quality_kpi !== undefined
              ? Number(data.data_quality_kpi).toFixed(2) + ' %'
              : '-'}
          </div>
        </div>
      </div>
    );
  }
  return (
    <div>
      <div className="flex gap-x-2">
        <div className="w-42">Quality Dimension:</div>
        <div>{data?.dimension}</div>
      </div>
      <div className="w-full">No data quality checks configured</div>
    </div>
  );
};
