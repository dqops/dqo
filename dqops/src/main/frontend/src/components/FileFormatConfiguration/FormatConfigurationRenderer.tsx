import React, { useState } from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SvgIcon from '../SvgIcon';
import ConfigurationItemRow from './FormatsConfiguration/RowItem/ConfigurationItemRow';
import ConfigurationItemRowBoolean from './FormatsConfiguration/RowItem/ConfigurationItemRowBoolean';
import { TConfigurationItemRow } from './FormatsConfiguration/RowItem/TConfigurationItemRow';
import { TConfigurationItemRowBoolean } from './FormatsConfiguration/RowItem/TConfigurationItemRowBoolean';

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
  const [isSectionExpanded, setIsSectionExpanded] = useState(false);

  const title = `Additional ${type} format options`;

  return (
    <div>
      {isSectionExpanded === false ? (
        <div
          className="flex items-center text-black cursor-default"
          onClick={() => setIsSectionExpanded(true)}
        >
          <SvgIcon name="chevron-right" className="w-5 h-5" />
          <span className="font-bold">{title}</span>
        </div>
      ) : (
        <SectionWrapper
          title={title}
          onClick={() => setIsSectionExpanded(false)}
          svgIcon={true}
          className="mt-2"
        >
          <div />
          {configuraitonStrings && (
            <div style={divStyle} className="pt-2">
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
            <div style={divStyle} className="pb-2 pt-4">
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
      )}
    </div>
  );
}
