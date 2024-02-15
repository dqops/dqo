import React, { useMemo } from 'react';
import { JsonFileFormatSpec } from '../../api';
import ConfigurationItemRow from './RowItem/ConfigurationItemRow';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow'

type TJsonConfigurationProps = {
  configuration: JsonFileFormatSpec;
  onChangeConfiguration: (params: Partial<JsonFileFormatSpec>) => void;
};

export default function JsonFormatConfiguration({
  configuration,
  onChangeConfiguration
}: TJsonConfigurationProps) {
  const jsonConfiguration: TConfigurationItemRow[] = useMemo(() => {
    return [
      // {
      //   label: 'Compression',
      //   value: configuration.compression,
      //   onChange: (str) => onChangeConfiguration({ compression: str })
      // },
      // // convertStringsToIntegers
      // {
      //   label: 'Date format',
      //   value: configuration.dateformat,
      //   onChange: (str) => onChangeConfiguration({ dateformat: str })
      // },
      // filename
      // format
      // hivePartitioning
      // ignoreErrors
      // maximumDepth
      // maximumObjectSize
      // records
      // {
      //   label: 'Timestamp Format',
      //   value: configuration.timestampformat,
      //   onChange: (str) => onChangeConfiguration({ timestampformat: str })
      // }
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
      {jsonConfiguration.map((x, index) => (
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
