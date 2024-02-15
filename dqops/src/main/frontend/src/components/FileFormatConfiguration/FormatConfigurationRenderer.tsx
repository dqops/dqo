import React from 'react';
import ConfigurationItemRow from './RowItem/ConfigurationItemRow';
import ConfigurationItemRowBoolean from './RowItem/ConfigurationItemRowBoolean';
import { TConfigurationItemRow } from './RowItem/TConfigurationItemRow'
import { TConfigurationItemRowBoolean } from './RowItem/TConfigurationItemRowBoolean'

type TCsvConfigurationProps = {
  configuraitonStrings?: TConfigurationItemRow[],
  configurationBooleans?: TConfigurationItemRowBoolean[]
};

export default function FormatConfigurationRenderer({
   configuraitonStrings,
   configurationBooleans
}: TCsvConfigurationProps) {

  return (
    <>
      {configuraitonStrings && <>
        <br/>
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: '1fr 1fr 1fr',
            gap: '16px'
          }}
        >
          {configuraitonStrings.map((x, index) => (
            <ConfigurationItemRow
              key={index}
              label={x.label}
              value={x.value}
              onChange={x.onChange}
              defaultValue={x.defaultValue}
            />
          ))}
        </div>
      </>}

      {configurationBooleans && <>
        <br/>
          <div
            style={{
              display: 'grid',
              gridTemplateColumns: '1fr 1fr 1fr',
              gap: '16px'
            }}
          >
          {configurationBooleans.map((x, index) => (
            <ConfigurationItemRowBoolean
              key={index}
              label={x.label}
              value={x.value}
              onChange={x.onChange}
              defaultValue={x.defaultValue}
            />
          ))}
        </div>
      </>}
    </>
  );
}
