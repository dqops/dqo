import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import Input from '../../components/Input';
import TableList from '../../components/TableList';
import { LabelsApiClient, SearchApiClient } from '../../services/apiClient';

type TSearchFilters = {
  connection?: string | undefined;
  schema?: string | undefined;
  table?: string | undefined;
};

type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };

export default function GlobalTables() {
  const [tables, setTables] = useState<TTableWithSchema[]>([]);
  const [filters, setFilters] = useState<any>({ page: 1, limit: 50 });
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
    getTables(filteredlabels);
    setLabels(arr);
  };

  const getTables = (labels: string[] = []) => {
    const addPrefix = (str: string) => {
      return str.includes('*') || str.length === 0 ? str : '*' + str + '*';
    };
    SearchApiClient.findTables(
      addPrefix(searchFilters.connection ?? ''),
      addPrefix(searchFilters.schema ?? ''),
      addPrefix(searchFilters.table ?? ''),
      labels,
      filters.page,
      filters.pageSize,
      filters.checkType
    ).then((res) => {
      const arr: TTableWithSchema[] = [];
      res.data.forEach((item) => {
        const jItem = { ...item, schema: item.target?.schema_name };
        arr.push(jItem);
      });
      setTables(arr);
    });
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

    getTables();
    getLabels();
  }, [filters]);

  return (
    <>
      <div className="flex items-center gap-x-4 mb-4 mt-2 px-4">
        <Input
          label="Connection name"
          value={searchFilters.connection}
          onChange={(e) =>
            onChangeSearchFilters({ connection: e.target.value })
          }
        />
        <Input
          label="Schema name"
          value={searchFilters.schema}
          onChange={(e) => onChangeSearchFilters({ schema: e.target.value })}
        />
        <Input
          label="Table name"
          value={searchFilters.table}
          onChange={(e) => onChangeSearchFilters({ table: e.target.value })}
        />
        <Button
          label="Search"
          onClick={() =>
            getTables(
              labels
                .filter((x) => x.clicked && x.label)
                .map((x) => x.label)
                .filter((x): x is string => typeof x === 'string')
            )
          }
          color="primary"
          className="mt-5"
        />
      </div>
      <TableList
        tables={tables}
        setTables={setTables}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
      />
    </>
  );
}