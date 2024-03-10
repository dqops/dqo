import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch } from 'react-redux';
import { TableListModel } from '../../api';
import Button from '../../components/Button';
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
          { label: 'Daily checks', value: 'daily' },
          { label: 'Daily table status', value: 'table-quality-status-daily' }
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
        <div className="flex">
          {label}
          <div>
            <SvgIcon name="chevron-up" className="w-3 h-3" />
            <SvgIcon name="chevron-down" className="w-3 h-3" />
          </div>
        </div>
      </th>
    );
  };

  return (
    <table className="min-w-350 max-w-400">
      <thead>
        <tr>{headeritems.map((item) => renderItem(item.label, item.value))}</tr>
      </thead>
      <tbody>
        {tables.map((item, index) => (
          <tr key={index}>
            <Button
              className="px-4 underline cursor-pointer"
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
            <td className="px-4">{item?.stage}</td>
            <td className="px-4">{item?.filter}</td>
            {buttonTabs.map((button) => {
              return (
                <td className="px-4 " key={button.value}>
                  <Button
                    variant="text"
                    label={button.label}
                    color="primary"
                    onClick={() => goToTable(item, button.value)}
                  />
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
};
