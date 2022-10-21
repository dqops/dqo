import React, { useEffect, useState } from 'react';
import Select from '../Select';
import { useLocation } from 'react-router-dom';
import qs from 'query-string';
import { ColumnApiClient } from '../../services/apiClient';

interface Option {
  label: string;
  value: string;
}

interface IColumnSelectProps {
  label?: string;
  value?: string;
  tooltipText?: string;
  onChange: (value: string) => void;
}

const ColumnSelect = ({
  label,
  value,
  tooltipText,
  onChange
}: IColumnSelectProps) => {
  const [options, setOptions] = useState<Option[]>([]);

  const location = useLocation();

  useEffect(() => {
    const params: any = qs.parse(location.search);
    const { connection, schema, table } = params;

    if (connection && schema && table) {
      ColumnApiClient.getColumns(connection, schema, table).then((res) => {
        setOptions(
          res.data.map((item) => ({
            label: item.column_name || '',
            value: item.column_name || ''
          }))
        );
      });
    }
  }, [location.search]);

  return (
    <div>
      <Select
        label={label}
        value={value}
        tooltipText={tooltipText}
        options={options}
        triggerClassName="!h-8"
        onChange={onChange}
      />
    </div>
  );
};

export default ColumnSelect;
