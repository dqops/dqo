import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import ColumnList from '../../components/ColumnList';
import Input from '../../components/Input';
import { LabelsApiClient, SearchApiClient } from '../../services/apiClient';
import { useDecodedParams } from '../../utils';

type TSearchFilters = {
  table?: string | undefined;
  column?: string | undefined;
  columnType?: string | undefined;
};

type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };

export default function SchemaColumns() {
  const { connection, schema }: { connection: string; schema: string } =
    useDecodedParams();
  const [columns, setColumns] = useState<TTableWithSchema[]>([]);
  const [filters, setFilters] = useState<any>({ page: 1, pageSize: 50 });
  const [searchFilters, setSearchFilters] = useState<TSearchFilters>({});
  const [labels, setLabels] = useState<TLabel[]>([]);

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
    const res = await SearchApiClient.findColumns(
      connection,
      schema,
      addPrefix(searchFilters.table ?? ''),
      addPrefix(searchFilters.column ?? ''),
      addPrefix(searchFilters.columnType ?? ''),
      labels,
      filters.page,
      filters.pageSize,
      filters.checkType
    );
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
        <div className="flex items-center gap-x-4 mb-4 mt-2 px-4">
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
          label="Refresh"
          color="primary"
          className="mb-2 mt-5 mr-4"
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
        setColumns={setColumns}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
      />
    </>
  );
}
