import React, { useState } from 'react';
import JdbcPropertiesView from '../Dashboard/DatabaseConnection/JdbcProperties';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SvgIcon from '../SvgIcon';

export default function AdvancedProperties({
  properties,
  handleChange,
  sharedCredentials
}: {
  properties: any;
  handleChange: any;
  sharedCredentials: any;
}) {
  const [advancedPropertiesOpen, setAdvancedPropertiesOpen] = useState(false);

  return (
    <div>
      {advancedPropertiesOpen ? (
        <SectionWrapper
          title="Advanced properties"
          className="ml-4 !pb-1 !pt-1 !mt-4 !mb-4"
          svgIcon
          onClick={() => setAdvancedPropertiesOpen(!advancedPropertiesOpen)}
        >
          <JdbcPropertiesView
            properties={properties}
            onChange={(properties) =>
              handleChange({ advanced_properties: properties })
            }
            title="Advanced property name"
            sharedCredentials={sharedCredentials}
          />
        </SectionWrapper>
      ) : (
        <div className="flex items-center ml-4 mb-2 text-sm">
          <SvgIcon
            name="chevron-right"
            className="w-5 h-5"
            onClick={() => setAdvancedPropertiesOpen(!advancedPropertiesOpen)}
          />
          Advanced properties
        </div>
      )}
    </div>
  );
}
