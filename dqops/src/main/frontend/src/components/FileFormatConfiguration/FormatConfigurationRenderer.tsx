import React from 'react';
import ConfigurationItemRow from './FormatsConfiguration/RowItem/ConfigurationItemRow';
import ConfigurationItemRowBoolean from './FormatsConfiguration/RowItem/ConfigurationItemRowBoolean';
import { TConfigurationItemRow } from './FormatsConfiguration/RowItem/TConfigurationItemRow';
import { TConfigurationItemRowBoolean } from './FormatsConfiguration/RowItem/TConfigurationItemRowBoolean';
import SectionWrapper from '../Dashboard/SectionWrapper';

type TConfigurationProps = {
  configuraitonStrings?: TConfigurationItemRow[];
  configurationBooleans?: TConfigurationItemRowBoolean[];
  type: string;
};

const divStyle = {
  display: 'grid',
  gridTemplateColumns: '1fr 1fr 1fr',
  gap: '16px'
};

export default function FormatConfigurationRenderer({
  configuraitonStrings,
  configurationBooleans,
  type
}: TConfigurationProps) {
  return (
    <SectionWrapper title={`${type} format options`}>
      <div className="py-2" />
      {configuraitonStrings && (
        <div style={divStyle}>
          {configuraitonStrings.map((x, index) => (
            <ConfigurationItemRow
              key={index}
              label={x.label}
              value={x.value}
              onChange={x.onChange}
              defaultValue={x.defaultValue}
              isEnum={x.isEnum}
              options={x.options}
            />
          ))}
        </div>
      )}

      {configurationBooleans && (
        <div style={divStyle} className="py-5">
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
      )}
    </SectionWrapper>
  );
}