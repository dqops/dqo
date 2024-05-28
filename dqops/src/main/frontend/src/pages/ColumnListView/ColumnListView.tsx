import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import ColumnList from '../../components/ColumnList';
import Input from '../../components/Input';
import { LabelsApiClient, SearchApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import SvgIcon from '../../components/SvgIcon';
import clsx from 'clsx';

type TSearchFilters = {
  connection?: string | undefined;
  schema?: string | undefined;
  table?: string | undefined;
  column?: string | undefined;
  columnType?: string | undefined;
};

type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };

export default function ColumnListView() {
  const {
    checkTypes,
    connection,
    schema
  }: { checkTypes: CheckTypes; connection: string; schema: string } =
    useDecodedParams();
  const [columns, setColumns] = useState<TTableWithSchema[]>([]);
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
    getColumns(filteredlabels);
    setLabels(arr);
  };

  const getColumns = async (labels: string[] = []) => {
    const addPrefix = (str: string) => {
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    setLoading(true);
    const res = await SearchApiClient.findColumns(
      connection ?? addPrefix(searchFilters.connection ?? ''),
      schema ?? addPrefix(searchFilters.schema ?? ''),
      addPrefix(searchFilters.table ?? ''),
      addPrefix(searchFilters.column ?? ''),
      searchFilters.columnType?.length
        ? addPrefix(searchFilters.columnType ?? '')
        : undefined,
      labels,
      filters.page,
      filters.pageSize,
      (checkTypes === CheckTypes.SOURCES
        ? CheckTypes.MONITORING
        : checkTypes) ?? filters.checkType
    ).finally(() => setLoading(false));
    const arr: TTableWithSchema[] = [];
    res.data.forEach((item) => {
      const jItem = {
        ...item,
        schema: item.table?.schema_name,
        table_name: item.table?.table_name,
        column_type: item.type_snapshot?.column_type
      };
      arr.push(jItem);
    });
    setColumns(arr);
    return arr;
  };

  useEffect(() => {
    const getLabels = () => {
      LabelsApiClient.getAllLabelsForColumns().then((res) => {
        const array: TLabel[] = res.data.map((item) => {
          return { ...item, clicked: false };
        });
        setLabels(array);
      });
    };

    const fetchData = async () => {
      const columns = await getColumns();
      refetchTables(columns);
    };

    fetchData();
    getLabels();
  }, [filters]);

  useEffect(() => {
    const handleKeyPress = (event: KeyboardEvent) => {
      if (event.key === 'Enter') {
        getColumns(
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

  const refetchTables = (columns?: TableListModel[]) => {
    const shouldRefetch = columns?.some((table) => !table?.data_quality_status);

    if (shouldRefetch) {
      setTimeout(() => {
        getColumns();
      }, 5000);
    }
  };

  return (
    <>
      <div className="flex items-center justify-between bg-white">
        <div className="flex items-center gap-x-4 mb-4 mt-5 px-4">
          {!connection && (
            <Input
              label="Connection name"
              value={searchFilters.connection}
              onChange={(e) =>
                onChangeSearchFilters({ connection: e.target.value })
              }
            />
          )}
          {!schema && (
            <Input
              label="Schema name"
              value={searchFilters.schema}
              onChange={(e) =>
                onChangeSearchFilters({ schema: e.target.value })
              }
            />
          )}
          <Input
            label="Table name"
            value={searchFilters.table}
            onChange={(e) => onChangeSearchFilters({ table: e.target.value })}
          />
          <Input
            label="Column name"
            value={searchFilters.column}
            onChange={(e) => onChangeSearchFilters({ column: e.target.value })}
          />
          <Input
            label="Column type"
            value={searchFilters.columnType}
            onChange={(e) =>
              onChangeSearchFilters({ columnType: e.target.value })
            }
          />
          <Button
            label="Search"
            onClick={() => {
              getColumns(
                labels
                  .filter((x) => x.clicked && x.label)
                  .map((x) => x.label)
                  .filter((x): x is string => typeof x === 'string')
              );
            }}
            color="primary"
            className="mt-5"
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
            getColumns(
              labels
                .filter((x) => x.clicked && x.label)
                .map((x) => x.label)
                .filter((x): x is string => typeof x === 'string')
            )
          }
        />
      </div>
      <ColumnList
        columns={columns}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
        loading={loading}
      />
    </>
  );
}
