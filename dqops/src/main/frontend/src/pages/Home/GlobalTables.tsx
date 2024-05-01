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
  label?: string[] | undefined;
  page?: number | undefined;
  limit?: number | undefined;
  checkType?: 'profiling' | 'monitoring' | 'partitioned' | undefined;
};

type TLabel = LabelModel & { clicked: boolean };

export default function GlobalTables() {
  const [tables, setTables] = useState<TableListModel[]>([]);
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
    console.log(
      searchFilters.connection,
      searchFilters.schema,
      searchFilters.table,
      labels,
      searchFilters.page,
      searchFilters.limit,
      searchFilters.checkType
    );
    const addPrefix = (str: string) => {
      return str.includes('*') || str.length === 0 ? str : str + '*';
    };
    SearchApiClient.findTables(
      addPrefix(searchFilters.connection ?? ''),
      addPrefix(searchFilters.schema ?? ''),
      addPrefix(searchFilters.table ?? ''),
      labels,
      searchFilters.page,
      searchFilters.limit,
      searchFilters.checkType
    ).then((res) => setTables(res.data));
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
  }, []);

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
