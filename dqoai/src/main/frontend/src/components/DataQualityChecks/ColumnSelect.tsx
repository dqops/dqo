import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import qs from 'query-string';
import { ColumnApiClient, ConnectionApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel, CommonColumnModel } from '../../api';
import SelectInput from '../SelectInput';

export interface Option {
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
  triggerClassName?: string;
}

const ColumnSelect = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled,
  scope = 'column',
  triggerClassName,
  error
}: IColumnSelectProps) => {
  const [options, setOptions] = useState<Option[]>([]);

  const location = useLocation();

  const setColumns = (
    res: AxiosResponse<CommonColumnModel[] | ColumnBasicModel[]>
  ) => {
    const data = res.data.map((item) => ({
      label: item.column_name || '',
      value: item.column_name || ''
    }));
    setOptions([{ label: 'None', value: '' }, ...data]);
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
      <SelectInput
        disabled={disabled}
        label={label}
        value={value}
        tooltipText={tooltipText}
        options={options}
        triggerClassName={triggerClassName}
        onChange={onChange}
        error={error}
      />
    </div>
  );
};

export default ColumnSelect;
