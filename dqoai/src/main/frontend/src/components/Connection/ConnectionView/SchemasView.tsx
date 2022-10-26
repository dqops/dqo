import React, { useEffect, useState } from 'react';
import { SchemaApiClient } from '../../../services/apiClient';
import { SchemaModel } from '../../../api';

interface ISchemasViewProps {
  connectionName: string;
}

const SchemasView = ({ connectionName }: ISchemasViewProps) => {
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);

  useEffect(() => {
    SchemaApiClient.getSchemas(connectionName).then((res) => {
      setSchemas(res.data);
    });
  }, [connectionName]);

  return (
    <div className="py-4 px-8">
      <div className="font-semibold pb-2 mb-2 border-b border-gray-300">Schema Name</div>
      {schemas.map((item) => (
        <div key={item.schema_name} className="mb-3">{item.schema_name}</div>
      ))}
    </div>
  );
};

export default SchemasView;
