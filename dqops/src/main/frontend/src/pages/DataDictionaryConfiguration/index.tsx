import React from 'react';
import DefinitionLayout from '../../components/DefinitionLayout';
import DataDictionaryConfigurationHeader from './DataDictionaryConfigurationHeader';
import DataDictionaryConfigurationTable from './DataDictionaryConfigurationTable';

export default function DataDictionary() {
  return (
    <DefinitionLayout>
      <table className="w-full ">
        <DataDictionaryConfigurationHeader />
        <DataDictionaryConfigurationTable />
      </table>
    </DefinitionLayout>
  );
}
