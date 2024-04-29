import React, { useEffect, useState } from 'react';
import { TableListModel } from '../../api';
import { Pagination } from '../../components/Pagination';
import SvgIcon from '../../components/SvgIcon';
import { TableApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SchemaTableItem from './SchemaTableItem';

type TButtonTabs = {
  label: string;
  value: string;
  sortable?: boolean;
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
  },
  { label: 'Data Quality KPI', value: 'data-quality-kpi', sortable: false }
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
  const [tables, setTables] = useState<TableListModel[]>([]);
  const [sortingDir, setSortingDir] = useState<'asc' | 'desc'>('asc');
  const [filters, setFilters] = useState<any>({ page: 1, limit: 50 });

  const onChangeFilters = (obj: Partial<any>) => {
    setFilters((prev: any) => ({
      ...prev,
      ...obj
    }));
  };

  useEffect(() => {
    TableApiClient.getTables(
      connection,
      schema,
      filters.page,
      filters.pageSize,
      filters.filter,
      checkTypes === CheckTypes.SOURCES ? CheckTypes.PROFILING : checkTypes
    ).then((res) => {
      setTables(res.data);
    });
  }, [schema, connection, filters]);

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

  const renderItem = (label: string, key: string, sortable?: boolean) => {
    return (
      <th
        className="px-4 text-left cursor-pointer"
        onClick={() => sortable !== false && sortTables(key)}
        key={key}
      >
        <div className="flex text-sm">
          {label}
          {sortable !== false && (
            <div className="flex flex-col items-center">
              <SvgIcon name="chevron-up" className="w-3 h-2" />
              <SvgIcon name="chevron-down" className="w-3 h-2" />
            </div>
          )}
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

  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const headerItems = [
    ...headeritems,
    ...basicDimensionTypes.map((x) => ({
      label: x,
      value: x,
      sortable: false
    })),
    ...getDimensionKey().map((x) => ({ label: x, value: x, sortable: false }))
  ];
  return (
    <>
      <table className="min-w-350 max-w-400">
        <thead>
          <tr>
            {headerItems.map((item) =>
              renderItem(item.label, item.value, item.sortable)
            )}
          </tr>
        </thead>
        <tbody>
          {tables.map((item, index) => (
            <SchemaTableItem
              key={index}
              item={item}
              dimensionKeys={getDimensionKey()}
            />
          ))}
        </tbody>
      </table>
      <div className="px-4">
        <Pagination
          page={filters.page || 1}
          pageSize={filters.pageSize || 50}
          totalPages={10}
          onChange={(page, pageSize) =>
            onChangeFilters({
              page,
              pageSize
            })
          }
        />
      </div>
    </>
  );
};
