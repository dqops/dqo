import React, { useEffect, useState } from 'react';
import { LabelModel, TableListModel } from '../../api';
import Button from '../../components/Button';
import Input from '../../components/Input';
import TableList from '../../components/TableList';
import { LabelsApiClient, TableApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';

type TLabel = LabelModel & { clicked: boolean };
type TTableWithSchema = TableListModel & { schema?: string };

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
  const [filters, setFilters] = useState<any>({ page: 1, limit: 50 });
  const [table, setTable] = useState('');
  const [labels, setLabels] = useState<TLabel[]>([]);

  const onChangeFilters = (obj: Partial<any>) => {
    setFilters((prev: any) => ({
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
    const filter =
      table.includes('*') || table.length === 0 ? table : '*' + table + '*';

    return TableApiClient.getTables(
      connection,
      schema,
      labels,
      filters.page,
      filters.pageSize,
      filter,
      checkTypes === CheckTypes.SOURCES ? undefined : checkTypes
    ).then((res) => {
      const arr: TTableWithSchema[] = [];
      res.data.forEach((item) => {
        const jItem = { ...item, schema: item.target?.schema_name };
        arr.push(jItem);
      });
      setTables(arr);
      return arr;
    });
  };

  const refetchTables = (tables?: TableListModel[]) => {
    const shouldRefetch = tables?.some((table) => !table?.data_quality_status);

    if (shouldRefetch) {
      setTimeout(() => {
        getTables();
      }, 5000);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      const tables = await getTables();
      refetchTables(tables);
    };

    fetchData();
    const getLabels = () => {
      LabelsApiClient.getAllLabelsForTables().then((res) => {
        const array: TLabel[] = res.data.map((item) => {
          return { ...item, clicked: false };
        });
        setLabels(array);
      });
    };
    getLabels();
  }, [schema, connection, filters]);

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
  }, [table]);

  return (
    <>
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-x-4 mb-4 mt-2 px-4">
          <Input
            label="Table name"
            value={table}
            onChange={(e) => setTable(e.target.value)}
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
        <Button
          label="Refresh"
          color="primary"
          className="mb-4 mt-2 mr-4"
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
        setTables={setTables}
        filters={filters}
        onChangeFilters={onChangeFilters}
        labels={labels}
        onChangeLabels={onChangeLabels}
      />
    </>
  );
};

// jezeli dluzsze niz 50 znakow to 3 kropki, wyswietlamy wszystkie labelki, posortowane
