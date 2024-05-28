import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import Input from '../../components/Input';
import SvgIcon from '../../components/SvgIcon';
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
  const [tables, setTables] = useState<TTableWithSchema[]>([]);
  const [filters, setFilters] = useState<any>({ page: 1, pageSize: 50 });
  const [searchFilters, setSearchFilters] = useState<TSearchFilters>({});
  const [labels, setLabels] = useState<TLabel[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
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
    console.log(
      connection ? addPrefix(connection) : addPrefix(searchFilters.connection),
      schema ? addPrefix(schema) : addPrefix(searchFilters.schema)
    );
    const res = await SearchApiClient.findTables(
      connection ? addPrefix(connection) : addPrefix(searchFilters.connection),
      schema ? addPrefix(schema) : addPrefix(searchFilters.schema),
      addPrefix(searchFilters.table ?? ''),
      labels,
      filters.page,
      filters.pageSize,
      (checkTypes === CheckTypes.SOURCES
        ? CheckTypes.MONITORING
        : checkTypes) ?? filters.checkType
    ).finally(() => setLoading(false));
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
  }, [filters]);

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
      setTimeout(() => {
        getTables();
      }, 5000);
    }
  };

  return (
    <>
      <div className="flex items-center justify-between bg-white w-full relative">
        <div className="flex items-center gap-x-4 mb-4 mt-2 px-4">
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
            className="mt-5 z-[100]"
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
          className="mb-4 mt-7 !mr-8 pr-0 pl-3 z-[1]"
          onClick={() =>
            getTables(
              labels
                .filter((x) => x.clicked && x.label)
                .map((x) => x.label)
                .filter((x): x is string => typeof x === 'string')
            )
          }
        />
      </div>
      <TableList
        tables={tables}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
        loading={loading}
      />
    </>
  );
}
