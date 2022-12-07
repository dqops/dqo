import React, { useEffect, useState } from 'react';
import { SourceSchemasApi } from '../../../services/apiClient';
import { SchemaRemoteModel } from '../../../api';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Loader from '../../Loader';
import ConnectionActionGroup from './ConnectionActionGroup';

interface ISchemasViewProps {
  connectionName: string;
}

const SchemasView = ({ connectionName }: ISchemasViewProps) => {
  const [loading, setLoading] = useState(false);
  const [schemas, setSchemas] = useState<SchemaRemoteModel[]>([]);

  useEffect(() => {
    setLoading(true);
    SourceSchemasApi.getRemoteSchemas(connectionName)
      .then((res) => {
        setSchemas(res.data);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [connectionName]);

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup />
      {loading ? (
        <div className="flex justify-center h-100">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      ) : (
        <table className="w-full">
          <thead>
            <tr>
              <th className="py-2 px-4 text-left">Source Schema Name</th>
              <th className="py-2 px-4 text-left">Is already imported</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {schemas.map((item) => (
              <tr key={item.schemaName} className="mb-3">
                <td className="py-2 px-4 text-left">{item.schemaName}</td>
                <td className="py-2 px-4 text-left">
                  <SvgIcon
                    name={item.imported ? 'check' : 'close'}
                    className={
                      item.imported ? 'text-green-700' : 'text-red-700'
                    }
                    width={30}
                    height={22}
                  />
                </td>
                <td className="py-2 px-4 text-left">
                  <Button label="Import tables" color="primary" />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default SchemasView;
