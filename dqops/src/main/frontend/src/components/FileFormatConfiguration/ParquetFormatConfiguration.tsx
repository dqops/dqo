import React, { useMemo } from 'react';
import { ParquetFileFormatSpec } from '../../api';
import ConfigurationItemRow from './RowItem/ConfigurationItemRow';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow'

type TParquetConfigurationProps = {
  configuration: ParquetFileFormatSpec;
  onChangeConfiguration: (params: Partial<ParquetFileFormatSpec>) => void;
};

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
          onChange={x.onChange}
          defaultValue={x.defaultValue}
        />
      ))}
    </div>
  );
}
