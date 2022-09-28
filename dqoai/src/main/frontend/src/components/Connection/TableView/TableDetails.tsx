import React, { useEffect, useState } from 'react';
import { TableApiClient } from '../../../services/apiClient';
import { AxiosResponse } from 'axios';
import { TableBasicModel } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';

interface ITableDetailsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableDetails = ({
  connectionName,
  schemaName,
  tableName
}: ITableDetailsProps) => {
  const [tableBasic, setTableBasic] = useState<TableBasicModel>();

  const fetchDetail = async () => {
    try {
      const res: AxiosResponse<TableBasicModel> =
        await TableApiClient.getTableBasic(
          connectionName,
          schemaName,
          tableName
        );
      setTableBasic(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchDetail().then();
  }, []);

  const handleChange = (obj: any) => {
    setTableBasic({
      ...tableBasic,
      ...obj
    });
  };

  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-160">
        <tr>
          <td className="px-4 py-2">Connection Name</td>
          <td className="px-4 py-2">{tableBasic?.connection_name}</td>
        </tr>
        <tr>
          <td className="px-4 py-2">Schema Name</td>
          <td className="px-4 py-2">{tableBasic?.target?.schema_name}</td>
        </tr>
        <tr>
          <td className="px-4 py-2">Disable Table</td>
          <td className="px-4 py-2">
            <Checkbox
              onChange={(value) => handleChange({ disabled: value })}
              checked={tableBasic?.disabled}
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">Filter</td>
          <td className="px-4 py-2">
            <Input
              value={tableBasic?.filter}
              onChange={(e) => handleChange({ filter: e.target.value })}
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">Stage</td>
          <td className="px-4 py-2">
            <Input
              value={tableBasic?.stage}
              onChange={(e) => handleChange({ stage: e.target.value })}
            />
          </td>
        </tr>
        <tr>
          <td className="px-4 py-2">Table Hash</td>
          <td className="px-4 py-2">
            <Input
              value={tableBasic?.table_hash}
              onChange={(e) => handleChange({ table_hash: e.target.value })}
            />
          </td>
        </tr>
        {tableBasic?.target?.properties && (
          <>
            <tr>
              <td className="px-4 py-2 font-semibold" colSpan={2}>
                Properties
              </td>
            </tr>
            {Object.entries(tableBasic.target.properties).map(
              ([key, value], index) => (
                <tr key={index}>
                  <td className="px-4 py-2">{key}</td>
                  <td className="px-4 py-2">
                    <Input
                      value={value}
                      onChange={(e) =>
                        handleChange({
                          target: { properties: { [key]: e.target.value } }
                        })
                      }
                    />
                  </td>
                </tr>
              )
            )}
          </>
        )}
      </table>
    </div>
  );
};

export default TableDetails;
