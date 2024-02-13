import React, { useMemo } from 'react';
import { ParquetFileFormatSpec } from '../../api';
import ConfigurationItemRow from './ConfigurationItemRow';

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};
type TConfigurationItemRow = {
  label: string;
  value?: string | number;
  onChangeBoolean: (str: string) => void;
};

// todo: set input fields as in duckdb docs defaults to parquet

export default function ParquetFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TParquetConfigurationProps) {
  const parquetConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      // {
      //   label: 'Binary as string',
      //   value: configuration.binary_as_string,
      //   onChangeBoolean: (boolean) => onChangeConfiguration({ binary_as_string: boolean })
      // },
      
      // binaryAsString
      // filename
      // fileRowNumber
      // hivePartitioning
      // unionByName
    ];
  }, [configuration]);

  return (
    <div
      style={{
        display: 'grid',
        gridTemplateColumns: '1fr 1fr 1fr',
        gap: '16px'
      }}
    >
      {parquetConfiguration.map((x, index) => (
        <ConfigurationItemRow
          key={index}
          label={x.label}
          value={x.value}
          onChange={x.onChangeBoolean}
        />
      ))}
    </div>
  );
}
