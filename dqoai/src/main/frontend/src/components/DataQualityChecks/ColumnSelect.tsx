import React, { useEffect, useState } from 'react';
import Select from '../Select';
import { useLocation } from 'react-router-dom';
import qs from 'query-string';
import {
  ColumnApiClient,
  ConnectionApiClient,
  SchemaApiClient
} from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel, CommonColumnModel } from '../../api';

interface Option {
  label: string;
  value: string;
}

interface IColumnSelectProps {
  label?: string;
  value?: string;
  tooltipText?: string;
  onChange: (value: string) => void;
  disabled?: boolean;
  scope?: string;
  error?: boolean;
}

const ColumnSelect = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled,
  scope = 'column',
  error
}: IColumnSelectProps) => {
  const [options, setOptions] = useState<Option[]>([]);

  const location = useLocation();

  const setColumns = (
    res: AxiosResponse<CommonColumnModel[] | ColumnBasicModel[]>
  ) => {
    setOptions(
      res.data.map((item) => ({
        label: item.column_name || '',
        value: item.column_name || ''
      }))
    );
  };

  useEffect(() => {
    const params: any = qs.parse(location.search);
    const { connection, schema, table } = params;
    if (connection && scope === 'connection') {
      ConnectionApiClient.getConnectionCommonColumns(connection).then(
        setColumns
      );
    } else if (table) {
      ColumnApiClient.getColumns(connection, schema, table).then(setColumns);
    }
  }, [location.search]);

  return (
    <div>
      <Select
        disabled={disabled}
        label={label}
        value={value}
        tooltipText={tooltipText}
        options={options}
        triggerClassName="!h-8"
        onChange={onChange}
        error={error}
      />
    </div>
  );
};

export default ColumnSelect;
