import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import Input from '../../components/Input';
import SvgIcon from '../../components/SvgIcon';
import Switch from '../../components/Switch';
import TableList from '../../components/TableList';
import { LabelsApiClient, SearchApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

type TSearchFilters = {
  connection?: string | undefined;
  schema?: string | undefined;
  table?: string | undefined;
};

type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };

export default function TableListView() {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useDecodedParams();
  const [showDimensions, setShowDimensions] = useState<boolean>(true);
  const [tables, setTables] = useState<TTableWithSchema[]>([]);
  const [filters, setFilters] = useState<any>({ page: 1, pageSize: 50 });
  const [searchFilters, setSearchFilters] = useState<TSearchFilters>({});
  const [labels, setLabels] = useState<TLabel[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [refreshTimer, setRefreshTimer] = useState<NodeJS.Timer>();

  const onChangeFilters = (obj: Partial<any>) => {
    setFilters((prev: any) => ({
      ...prev,
      ...obj
    }));
  };

  const onChangeSearchFilters = (obj: Partial<TSearchFilters>) => {
    setSearchFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  const onChangeLabels = (index: number) => {
    const arr = [...labels];
    arr[index] = { ...arr[index], clicked: !arr[index].clicked };
    const filteredlabels: string[] = arr
      .filter((x) => x.clicked && x.label)
      .map((x) => x.label)
      .filter((x): x is string => typeof x === 'string');
    getTables(filteredlabels);
    setLabels(arr);
  };

  const getTables = async (labels: string[] = []) => {
    const addPrefix = (str?: string) => {
      if (!str) return '';
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    setLoading(true);
    //    console.log(
    //      connection ? addPrefix(connection) : addPrefix(searchFilters.connection),
    //      schema ? addPrefix(schema) : addPrefix(searchFilters.schema)
    //    );
    const res = await SearchApiClient.findTables(
      connection ? connection : addPrefix(searchFilters.connection),
      schema ? schema : addPrefix(searchFilters.schema),
      addPrefix(searchFilters.table ?? ''),
      labels,
      filters.page,
      filters.pageSize,
      (checkTypes === CheckTypes.SOURCES
        ? CheckTypes.MONITORING
        : checkTypes) ?? filters.checkType
    ).finally(() => {
      setLoading(false);
      setRefreshTimer(undefined);
    });
    const arr: TTableWithSchema[] = [];
    res.data.forEach((item) => {
      const jItem = { ...item, schema: item.target?.schema_name };
      arr.push(jItem);
    });
    setTables(arr);
    return arr;
  };

  useEffect(() => {
    const getLabels = () => {
      LabelsApiClient.getAllLabelsForTables().then((res) => {
        const array: TLabel[] = res.data.map((item) => {
          return { ...item, clicked: false };
        });
        setLabels(array);
      });
    };

    const fetchData = async () => {
      const tables = await getTables();
      refetchTables(tables);
    };

    fetchData();
    getLabels();
    return () => {
      if (refreshTimer) {
        clearTimeout(refreshTimer);
      }
    };
  }, [filters, checkTypes, connection, schema]);

  useEffect(() => {
    const handleKeyPress = (event: KeyboardEvent) => {
      if (event.key === 'Enter') {
        getTables(
          labels
            .filter((x) => x.clicked && x.label)
            .map((x) => x.label)
            .filter((x): x is string => typeof x === 'string')
        );
      }
    };

    document.addEventListener('keypress', handleKeyPress);

    return () => {
      document.removeEventListener('keypress', handleKeyPress);
    };
  }, [searchFilters]);

  const refetchTables = (tables?: TableListModel[]) => {
    const shouldRefetch = tables?.some((table) => !table?.data_quality_status);

    if (shouldRefetch) {
      setRefreshTimer(
        setTimeout(() => {
          getTables();
        }, 5000)
      );
    }
  };
  console.log(tables);
  return (
    <div className="bg-white">
      <div className="flex items-center bg-white w-full relative">
        <div className="flex items-center gap-x-4 mb-4 mt-4 px-4">
          {!connection && (
            <Input
              label="Connection name"
              value={searchFilters.connection}
              onChange={(e) =>
                onChangeSearchFilters({ connection: e.target.value })
              }
              className="z-[100]"
            />
          )}
          {!schema && (
            <Input
              label="Schema name"
              value={searchFilters.schema}
              onChange={(e) =>
                onChangeSearchFilters({ schema: e.target.value })
              }
              className="z-[100]"
            />
          )}
          <Input
            label="Table name"
            value={searchFilters.table}
            onChange={(e) => onChangeSearchFilters({ table: e.target.value })}
            className="z-[100]"
          />
          <Button
            label="Search"
            onClick={() => {
              getTables(
                labels
                  .filter((x) => x.clicked && x.label)
                  .map((x) => x.label)
                  .filter((x): x is string => typeof x === 'string')
              );
            }}
            color="primary"
            className="mt-5 z-[10]"
          />
        </div>
        <Button
          color="primary"
          leftIcon={
            <SvgIcon
              name="sync"
              className={clsx('w-4 h-4 mr-3', loading ? 'animate-spin' : '')}
            />
          }
          className="!mr-8 pr-0 pl-3 z-[1] mt-5"
          onClick={() =>
            getTables(
              labels
                .filter((x) => x.clicked && x.label)
                .map((x) => x.label)
                .filter((x): x is string => typeof x === 'string')
            )
          }
        />
        <div className="flex items-center mt-5 gap-x-2">
          <div className="text-sm font-bold">Dimensions</div>
          <Switch
            checked={showDimensions}
            onChange={(c) => setShowDimensions(c)}
          />
        </div>
      </div>
      <TableList
        tables={tables}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
        loading={loading}
        showDimensions={showDimensions}
      />
    </div>
  );
}
