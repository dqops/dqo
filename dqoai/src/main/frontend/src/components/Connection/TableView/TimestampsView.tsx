import React, { useEffect, useState } from 'react';
import {
  ColumnBasicModel,
  TimestampColumnsSpec,
  TimestampColumnsSpecPartitionedChecksTimestampSourceEnum
} from '../../../api';
import Select from '../../Select';
import { AxiosResponse } from 'axios';
import { ColumnApiClient } from '../../../services/apiClient';
import qs from 'query-string';
import { useLocation } from 'react-router-dom';
import { Option } from '../../DataQualityChecks/ColumnSelect';
import SelectInput from '../../SelectInput';

interface TimestampsViewProps {
  columnsSpec?: TimestampColumnsSpec;
  onChange: (columns: TimestampColumnsSpec) => void;
}

const TimestampsView = ({ columnsSpec, onChange }: TimestampsViewProps) => {
  const partitionedChecksOptions = [
    {
      label: 'event_timestamp',
      value:
        TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.event_timestamp
    },
    {
      label: 'ingestion_timestamp',
      value:
        TimestampColumnsSpecPartitionedChecksTimestampSourceEnum.ingestion_timestamp
    }
  ];
  const [options, setOptions] = useState<Option[]>([]);
  const location = useLocation();

  const setColumns = (res: AxiosResponse<ColumnBasicModel[]>) => {
    const data = res.data.map((item) => ({
      label: item.column_name || '',
      value: item.column_name || ''
    }));

    setOptions([{ label: 'None', value: '' }, ...data]);
  };

  const fetchColumns = async () => {
    try {
      const params: any = qs.parse(location.search);
      const { connection, schema, table } = params;
      const res: AxiosResponse<ColumnBasicModel[]> =
        await ColumnApiClient.getColumns(connection, schema, table);
      setColumns(res);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns().then();
  }, []);

  return (
    <div className="py-4 px-8 flex flex-col">
      <div className="mb-4">
        <SelectInput
          label="Event Timestamp Column"
          options={options}
          value={columnsSpec?.event_timestamp_column}
          onChange={(column) =>
            onChange({
              ...columnsSpec,
              event_timestamp_column: column
            })
          }
        />
      </div>

      <div className="mb-4">
        <SelectInput
          label="Ingestion Timestamp Column"
          options={options}
          value={columnsSpec?.ingestion_timestamp_column}
          onChange={(column) =>
            onChange({
              ...columnsSpec,
              ingestion_timestamp_column: column
            })
          }
        />
      </div>

      <div className="mb-4">
        <Select
          label="Partitioned Checks Timestamp Source"
          options={partitionedChecksOptions}
          value={columnsSpec?.partitioned_checks_timestamp_source}
          onChange={(column) =>
            onChange({
              ...columnsSpec,
              partitioned_checks_timestamp_source: column
            })
          }
        />
      </div>
    </div>
  );
};

export default TimestampsView;
