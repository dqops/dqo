import React, { useEffect, useState } from 'react';
import { SchemaRemoteModel } from '../../api';
import { DataSourcesApi } from '../../services/apiClient';
import Button from '../Button';
import SvgIcon from '../SvgIcon';
import Tabs from '../Tabs';

interface IImportSchemasProps {
  connectionName: string;
  onPrev: () => void;
  onNext: () => void;
}

const tabs = [
  {
    label: 'Schemas',
    value: 'schemas'
  }
];

const ImportSchemas = ({
  connectionName,
  onPrev,
  onNext
}: IImportSchemasProps) => {
  const [activeTab, setActiveTab] = useState('schemas');
  const [schemas, setSchemas] = useState<SchemaRemoteModel[]>([]);

  useEffect(() => {
    DataSourcesApi.getRemoteDataSourceSchemas(connectionName).then((res) => {
      setSchemas(res.data);
    });
  }, [connectionName]);

  return (
    <div className="flex-1 bg-white border border-gray-300 flex-auto">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-lg font-semibold">{connectionName}</div>
        </div>
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>

      <div className="p-4">
        <table className="w-full">
          <thead>
            <tr>
              <th className="px-4 py-1.5 text-left">Connection</th>
              <th className="px-4 py-1.5 text-left">Schema</th>
              <th className="px-4 py-1.5 text-left" />
            </tr>
          </thead>
          <tbody>
            {schemas.map((item, index) => (
              <tr key={index} className="border-b last:border-b-0">
                <td className="px-4 py-2 text-left">{item.connectionName}</td>
                <td className="px-4 py-2 text-left">{item.schemaName}</td>
                <td className="px-4 py-2 text-left">
                  <Button
                    label="Import"
                    variant="contained"
                    color="primary"
                    className="text-sm px-6 py-1"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="flex space-x-4 justify-end mt-6">
          <Button
            color="primary"
            variant="outlined"
            label="Prev"
            className="w-40"
            onClick={onPrev}
          />
          <Button
            color="primary"
            variant="contained"
            label="Next"
            className="w-40"
            onClick={onNext}
          />
        </div>
      </div>
    </div>
  );
};

export default ImportSchemas;
