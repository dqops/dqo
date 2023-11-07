import React, { useEffect, useState } from 'react';
import { DataSourcesApi } from '../../../services/apiClient';
import {
  DqoJobHistoryEntryModelStatusEnum,
  SchemaRemoteModel
} from '../../../api';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Loader from '../../Loader';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { isEqual } from 'lodash';
import SourceTablesView from './SourceTablesView';
import { useParams } from 'react-router-dom';

interface SourceSchemasViewProps {
  defaultSchema?: string;
}

const SourceSchemasView = ({ defaultSchema }: SourceSchemasViewProps) => {
  const { connection }: { connection: string } = useParams();
  const [loading, setLoading] = useState(false);
  const [schemas, setSchemas] = useState<SchemaRemoteModel[]>([]);
  const [error, setError] = useState<string>()

  const [selectedSchema, setSelectedSchema] = useState<string>();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );

  useEffect(() => {
    if (defaultSchema) {
      setSelectedSchema(defaultSchema);
    }
  }, [defaultSchema]);

  useEffect(() => {
    setLoading(true);
    DataSourcesApi.getRemoteDataSourceSchemas(connection)
      .then((res) => {
        setSchemas(res.data);
      })
      .catch((err) => {
        setError(err.message)
      })
      .finally(() => {
        setLoading(false);
      });
  }, [connection]);

  const onImportTables = (schema: SchemaRemoteModel) => {
    setSelectedSchema(schema.schemaName);
  };

  const isExist = (schema: SchemaRemoteModel) => {
    const job = Object.values(job_dictionary_state)?.find((item) =>
      isEqual(
        item.parameters?.importTableParameters,
        schema.importTableJobParameters
      )
    );
    return (
      !!job &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        job.status === DqoJobHistoryEntryModelStatusEnum.running ||
        job.status === DqoJobHistoryEntryModelStatusEnum.waiting)
    );
  };

  if (selectedSchema) {
    return (
      <SourceTablesView
        connectionName={connection}
        schemaName={selectedSchema ?? ''}
        onBack={() => setSelectedSchema(undefined)}
      />
    );
  }

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
              <th className="py-2 pr-4 text-left">Source Schema Name</th>
              <th className="py-2 px-4 text-left">Import status</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {schemas.map((item) => (
              <tr
                key={item.schemaName}
                className="border-b border-gray-300 last:border-b-0"
              >
                <td className="py-2 pr-4 text-left">{item.schemaName}</td>
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
                <td className="py-2 px-4 text-left">
                  {!isExist(item) && (
                    <Button
                      label="Import tables"
                      color="primary"
                      onClick={() => onImportTables(item)}
                    />
                  )}
                </td>
              </tr>
            ))}
            {error ? <div className='text-red-500'>{error}</div> : null}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default SourceSchemasView;
