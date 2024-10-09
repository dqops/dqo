import React, { useEffect, useState } from 'react';
import {
  TableLineageTableListModel,
  TableLineageSourceSpec
} from '../../../../api';
import { ColumnApiClient } from '../../../../services/apiClient';
import { useDecodedParams } from '../../../../utils';
import Loader from '../../../Loader';
import Select, { Option } from '../../../Select';

export default function SourceColumns({
  dataLineage,
  onChangeDataLineageSpec,
  dataLineageSpec,
  create,
  setIsUpdated
}: {
  dataLineage: TableLineageTableListModel;
  onChangeDataLineageSpec: (dataLineage: TableLineageSourceSpec) => void;
  dataLineageSpec: TableLineageSourceSpec;
  create: boolean;
  setIsUpdated: (value: boolean) => void;
}) {
  const {
    connection,
    schema,
    table
  }: {
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();

  const [sourceColumns, setSourceColumns] = useState<Option[]>([]);
  const [targetColumns, setTargetColumns] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    const fetchSourceColumns = async (targetColumns: string[]) => {
      try {
        // Fetch columns from API
        const res = await ColumnApiClient.getColumns(
          dataLineage.source_connection ?? '',
          dataLineage.source_schema ?? '',
          dataLineage.source_table ?? ''
        );
        const columns = (res.data ?? []).map((column) => ({
          label: column.column_name ?? '',
          value: column.column_name ?? ''
        }));

        setSourceColumns([{ label: '', value: '' }, ...columns]);

        if (!create) return;

        //only for creating new source table
        const updatedColumns = { ...dataLineageSpec.columns };

        targetColumns.forEach((targetColumn) => {
          const matchedColumn = columns.find(
            (c) =>
              c.value.toLowerCase().replaceAll('_', '') ===
                targetColumn.toLowerCase().replaceAll('_', '') ||
              (targetColumn.length > 3 &&
                c.value.length > 3 &&
                (c.value.length - targetColumn.length <= 4 ||
                  targetColumn.length - c.value.length <= 4) &&
                (targetColumn.toLowerCase().startsWith(c.value) ||
                  c.value.toLowerCase().startsWith(targetColumn)))
          );

          if (matchedColumn) {
            updatedColumns[targetColumn] = {
              source_columns: [matchedColumn.value]
            };
          }
        });

        onChangeDataLineageSpec({
          ...dataLineageSpec,
          columns: updatedColumns
        });
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    const fetchColumns = async () => {
      await ColumnApiClient.getColumns(connection, schema, table)
        .then((res) => {
          const columns = (res.data ?? []).map(
            (column) => column.column_name ?? ''
          );
          setTargetColumns(columns);
          if (
            dataLineage.source_connection &&
            dataLineage.source_schema &&
            dataLineage.source_table
          ) {
            fetchSourceColumns(columns);
          }
        })
        .catch((err) => console.error(err));
    };

    fetchColumns();
  }, [
    connection,
    schema,
    table,
    dataLineage.source_connection,
    dataLineage.source_schema,
    dataLineage.source_table
  ]);

  const handleSelectChange = (
    column: string,
    newValue: string,
    sourceColumnIndex: number
  ) => {
    const newColumns = [
      ...(dataLineageSpec.columns?.[column]?.source_columns ?? [])
    ];
    if (newValue === '') {
      newColumns.splice(sourceColumnIndex, 1);
    } else {
      if (newColumns[sourceColumnIndex] !== undefined) {
        newColumns[sourceColumnIndex] = newValue;
      } else {
        newColumns.push(newValue);
      }
    }

    onChangeDataLineageSpec({
      ...dataLineageSpec,
      columns: {
        ...dataLineageSpec.columns,
        [column]: {
          ...dataLineageSpec.columns?.[column],
          source_columns: newColumns
        }
      }
    });
    setIsUpdated(true);
  };

  if (loading && !create) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <table className="text-sm">
      <thead>
        <tr>
          <th className="px-4 py-2 text-left">Target column</th>
          <th className="px-4 py-2 text-left">Source column</th>
        </tr>
      </thead>
      <div className="w-full h-0.5"></div>
      <tbody className="border-t border-gray-100 mt-1">
        {targetColumns.map((column, columnIndex) => (
          <tr key={columnIndex}>
            <td className="px-4 py-2">{column}</td>
            {[
              ...(dataLineageSpec.columns?.[column]?.source_columns ?? []),
              ''
            ].map((sourceColumn, sourceColumnIndex) => (
              <td className="px-4 py-2" key={sourceColumnIndex}>
                <Select
                  value={sourceColumn}
                  options={sourceColumns}
                  onChange={(newValue) =>
                    handleSelectChange(column, newValue, sourceColumnIndex)
                  }
                  className="w-40"
                  error={
                    (
                      dataLineageSpec.columns?.[column]?.source_columns ?? []
                    ).filter((x) => x === sourceColumn).length > 1
                  }
                  truncateText
                  placeholder=""
                />
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
