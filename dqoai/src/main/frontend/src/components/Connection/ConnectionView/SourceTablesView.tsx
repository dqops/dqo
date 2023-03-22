import React, { useEffect, useState } from 'react';
import { JobApiClient, SourceTableApi } from '../../../services/apiClient';
import { TableRemoteBasicModel } from '../../../api';
import SvgIcon from '../../SvgIcon';
import Loader from '../../Loader';
import ConnectionActionGroup from './ConnectionActionGroup';
import Checkbox from '../../Checkbox';
import Button from '../../Button';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { toggleMenu } from '../../../redux/actions/job.actions';

interface ISourceSchemasViewProps {
  connectionName: string;
  schemaName: string;
  onBack: () => void;
}

const SourceSchemasView = ({
  connectionName,
  schemaName,
  onBack
}: ISourceSchemasViewProps) => {
  const [loading, setLoading] = useState(false);
  const [selectedTables, setSelectedTables] = useState<string[]>([]);
  const [tables, setTables] = useState<TableRemoteBasicModel[]>([]);
  const dispatch = useActionDispatch();

  useEffect(() => {
    setLoading(true);
    setSelectedTables([]);
    SourceTableApi.getRemoteTables(connectionName, schemaName)
      .then((res) => {
        setTables(res.data);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [connectionName, schemaName]);

  const selectAll = () => {
    setSelectedTables(tables.map((item) => item.tableName ?? ''));
  };

  const unselectAll = () => {
    setSelectedTables([]);
  };

  const importSelectedTables = () => {
    JobApiClient.importTables({
      connectionName,
      schemaName,
      tableNames: selectedTables
    });
    dispatch(toggleMenu(true));
  };

  const importAllTables = () => {
    JobApiClient.importTables({
      connectionName,
      schemaName,
      tableNames: tables.map((item) => item.tableName ?? '')
    });
    dispatch(toggleMenu(true));
  };

  const onSelectChange = (tableName: string) => {
    if (selectedTables?.includes(tableName)) {
      setSelectedTables(selectedTables.filter((item) => item !== tableName));
    } else {
      setSelectedTables([...selectedTables, tableName]);
    }
  };

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup onImport={onBack} />
      <div className="flex justify-end space-x-4 mb-4">
        <Button color="primary" label="Select All" onClick={selectAll} />
        <Button color="primary" label="Unselect All" onClick={unselectAll} />
        <Button
          color="primary"
          label="Import selected tables"
          onClick={importSelectedTables}
        />
        <Button
          color="primary"
          label="Import all tables"
          onClick={importAllTables}
        />
      </div>
      {loading ? (
        <div className="flex justify-center h-100">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      ) : (
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-300">
              <th />
              <th className="py-2 px-4 text-left">Source Table Name</th>
              <th className="py-2 px-4 text-left">Is already imported</th>
            </tr>
          </thead>
          <tbody>
            {tables.map((item) => (
              <tr
                key={item.tableName}
                className="border-b border-gray-300 last:border-b-0"
              >
                <td className="py-2 px-4 text-left">
                  <div className="flex">
                    <Checkbox
                      onChange={() => onSelectChange(item.tableName ?? '')}
                      checked={selectedTables.indexOf(item.tableName ?? '') > -1}
                    />
                  </div>
                </td>
                <td className="py-2 px-4 text-left">{item.tableName}</td>
                <td className="py-2 px-4 text-left">
                  <SvgIcon
                    name={item.alreadyImported ? 'check' : 'close'}
                    className={
                      item.alreadyImported ? 'text-primary' : 'text-red-700'
                    }
                    width={30}
                    height={22}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default SourceSchemasView;
