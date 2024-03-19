import React from 'react';
import { SchemaModel } from '../../../api';

interface ISchemaDetailProps {
  schema?: SchemaModel;
}

const SchemaDetail: React.FC<ISchemaDetailProps> = ({ schema }) => {
  return (
    <div className="p-4">
      <table className="mb-6 text-sm">
        <tbody>
          <tr>
            <td className="px-4 py-2">
              <div>Connection name:</div>
            </td>
            <td className="px-4 py-2">
              <div>{schema?.connection_name}</div>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">
              <div>Schema name:</div>
            </td>
            <td className="px-4 py-2">
              <div>{schema?.schema_name}</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default SchemaDetail;
