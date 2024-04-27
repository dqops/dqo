import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch } from 'react-redux';
import {
  DimensionCurrentDataQualityStatusModel,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableListModel
} from '../../api';
import Button from '../../components/Button';
import { getColor } from '../../components/Connection/TableView/TableQualityStatus/TableQualityStatusUtils';
import SvgIcon from '../../components/SvgIcon';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { TableApiClient } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

type TButtonTabs = {
  label: string;
  value: string;
};
const headeritems: TButtonTabs[] = [
  {
    label: 'Table',
    value: 'target.table_name'
  },
  {
    label: 'Disabled',
    value: 'disabled'
  },
  {
    label: 'Stage',
    value: 'stage'
  },
  {
    label: 'Filter',
    value: 'filter'
  }
];

function getValueForKey<T>(obj: T, key: string): string | undefined {
  const keys = key.split('.');
  let value: any = obj;

  for (const k of keys) {
    value = value?.[k];
    if (value === undefined) {
      break;
    }
  }

  return value?.toString();
}

export const SchemaTables = () => {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useDecodedParams();
  const dispatch = useDispatch();
  const [tables, setTables] = useState<TableListModel[]>([]);
  const [sortingDir, setSortingDir] = useState<'asc' | 'desc'>('asc');

  useEffect(() => {
    TableApiClient.getTables(connection, schema).then((res) => {
      setTables(res.data);
    });
  }, [schema, connection]);

  const goToTable = (item: TableListModel, tab: string) => {
    dispatch(
      addFirstLevelTab(checkTypes, {
        url: ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          item.connection_name ?? '',
          item.target?.schema_name ?? '',
          item.target?.table_name ?? '',
          tab
        ),
        value: ROUTES.TABLE_LEVEL_VALUE(
          checkTypes,
          item.connection_name ?? '',
          item.target?.schema_name ?? '',
          item.target?.table_name ?? ''
        ),
        state: {},
        label: item.target?.table_name ?? ''
      })
    );
    return;
  };

  const buttonTabs: TButtonTabs[] = useMemo(() => {
    switch (checkTypes) {
      case CheckTypes.PROFILING:
        return [
          {
            label: 'Basic statistics',
            value: 'statistics'
          },
          { label: 'Profiling checks', value: 'advanced' },
          { label: 'Profiling table status', value: 'table-quality-status' }
        ];

      case CheckTypes.SOURCES:
        return [
          {
            label: 'Table configuration',
            value: 'detail'
          },
          { label: 'Date and time column', value: 'timestamps' },
          { label: 'Incident configuration', value: 'incident_configuration' }
        ];

      case CheckTypes.MONITORING:
      case CheckTypes.PARTITIONED:
        return [
          { label: 'Daily table status', value: 'table-quality-status-daily' },
          { label: 'Daily checks', value: 'daily' }
        ];

      default:
        return [];
    }
  }, [checkTypes]);

  const sortTables = (key: string): void => {
    setTables((prev) => {
      const array = [...prev];
      array.sort((a, b) => {
        const valueA = getValueForKey(a, key);
        const valueB = getValueForKey(b, key);

        return sortingDir === 'asc'
          ? (valueA || '').localeCompare(valueB || '')
          : (valueB || '').localeCompare(valueA || '');
      });

      setSortingDir((prev) => (prev === 'asc' ? 'desc' : 'asc'));
      return array;
    });
  };

  const renderItem = (label: string, key: string) => {
    return (
      <th
        className="px-4 text-left cursor-pointer"
        onClick={() => sortTables(key)}
        key={key}
      >
        <div className="flex text-sm">
          {label}
          <div className="flex flex-col items-center">
            <SvgIcon name="chevron-up" className="w-3 h-2" />
            <SvgIcon name="chevron-down" className="w-3 h-2" />
          </div>
        </div>
      </th>
    );
  };

  const getDimensionKey = () => {
    const uniqueDimensions: string[] = [];
    tables.forEach((table) => {
      Object.keys(table.data_quality_status?.dimensions ?? {}).forEach((x) => {
        if (!uniqueDimensions.includes(x) && !basicDimensionTypes.includes(x)) {
          uniqueDimensions.push(x);
        }
      });
    });

    return uniqueDimensions;
  };
  const getBasicDimmensionsKeys = (dimentions: any, type: string) => {
    const basicDimensions = Object.keys(dimentions ?? {})?.find(
      (x) => x === type
    );
    return basicDimensions;
  };
  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const getAdditionalDimentionsKeys = (dimentions: any) => {
    return (
      Object.keys(dimentions ?? {})?.filter(
        (x) => !basicDimensionTypes.includes(x)
      ) ?? []
    );
  };
  return (
    <table className="min-w-350 max-w-400">
      <thead>
        <tr>
          {[
            ...headeritems,
            ...basicDimensionTypes.map((x) => ({ label: x, value: x })),
            ...getDimensionKey().map((x) => ({ label: x, value: x }))
          ].map((item) => renderItem(item.label, item.value))}
        </tr>
      </thead>
      <tbody>
        {tables.map((item, index) => (
          <tr key={index} className="text-sm">
            <Button
              className="px-4 underline cursor-pointer text-sm"
              label={item.target?.table_name}
              onClick={() => goToTable(item, buttonTabs[0].value)}
            />
            <td className="px-4">
              {item?.disabled ? (
                <SvgIcon
                  name="close"
                  className="text-red-700"
                  width={30}
                  height={22}
                />
              ) : null}
            </td>
            <td className="px-4 text-sm">{item?.stage}</td>
            <td className="px-4 text-sm">{item?.filter}</td>
            {/* {Object.entries(item.data_quality_status?.dimensions ?? {}).map(
              ([key, value]) => {
                return (
                  <td className="px-4 " key={value.dimension}>
                    <Tooltip content={renderSecondLevelTooltip(value)}>
                      <div
                        className={clsx(
                          'w-3 h-3',
                          getColor(
                            value?.current_severity as
                              | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                              | undefined
                          ).length === 0
                            ? 'bg-gray-150'
                            : getColor(
                                value?.current_severity as
                                  | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                                  | undefined
                              )
                        )}
                        style={{ borderRadius: '6px' }}
                      />
                    </Tooltip>
                  </td>
                );
              }
            )} */}

            {basicDimensionTypes.map((dimType) => {
              const dimensionKey = getBasicDimmensionsKeys(
                item.data_quality_status?.dimensions,
                dimType
              );
              const currentSeverity = (item.data_quality_status?.dimensions ??
                {})[dimensionKey as any]?.current_severity;
              const lastCheckExecutedAt = (item.data_quality_status
                ?.dimensions ?? {})?.[dimensionKey as any]
                ?.last_check_executed_at;
              const severityColor = getColor(currentSeverity as any);
              const hasNoSeverity = severityColor.length === 0;

              const dimensionsClassNames = clsx('w-3 h-3', {
                'bg-gray-150': hasNoSeverity && lastCheckExecutedAt,
                [severityColor]: !hasNoSeverity,
                'border border-gray-150': hasNoSeverity
              });
              return (
                <td key={`Dimensionindex${dimType}`}>
                  <Tooltip
                    content={renderSecondLevelTooltip(
                      (item.data_quality_status?.dimensions ?? {})?.[
                        dimensionKey as any
                      ] ?? {
                        dimension: dimType
                      }
                    )}
                  >
                    <div
                      className={dimensionsClassNames}
                      style={{ borderRadius: '6px' }}
                    />
                  </Tooltip>
                </td>
              );
            })}
            {getAdditionalDimentionsKeys(
              item.data_quality_status?.dimensions ?? {}
            ).map((dimensionKey: string, dimIndex) => {
              return (
                <td key={`DimensionTooltipindex${dimIndex}`}>
                  <Tooltip
                    content={renderSecondLevelTooltip(
                      (item.data_quality_status?.dimensions ?? {})?.[
                        dimensionKey as any
                      ]
                    )}
                  >
                    <div
                      className={clsx(
                        'w-3 h-3',
                        getColor(
                          (item.data_quality_status?.dimensions ?? {})?.[
                            dimensionKey as any
                          ]?.current_severity as
                            | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                            | undefined
                        ).length === 0
                          ? 'bg-gray-150'
                          : getColor(
                              (item.data_quality_status?.dimensions ?? {})?.[
                                dimensionKey as any
                              ]?.current_severity as
                                | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                                | undefined
                            )
                      )}
                      style={{ borderRadius: '6px' }}
                    />
                  </Tooltip>
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

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
