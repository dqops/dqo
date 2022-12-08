import React, { useEffect, useState } from 'react';
import { JobApiClient, SchemaApiClient } from '../../../services/apiClient';
import { DqoJobHistoryEntryModelStatusEnum, SchemaModel } from '../../../api';
import Button from '../../Button';
import { toggleMenu } from '../../../redux/actions/job.actions';
import { isEqual } from 'lodash';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ConnectionActionGroup from './ConnectionActionGroup';

interface ISchemasViewProps {
  connectionName: string;
}

const SchemasView = ({ connectionName }: ISchemasViewProps) => {
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);
  const { jobs } = useSelector((state: IRootState) => state.job);

  const dispatch = useActionDispatch();

  useEffect(() => {
    SchemaApiClient.getSchemas(connectionName).then((res) => {
      setSchemas(res.data);
    });
  }, [connectionName]);

  const onImportTables = (schema: SchemaModel) => {
    JobApiClient.importTables(schema.import_table_job_parameters);
    dispatch(toggleMenu(true));
  };

  const isExist = (schema: SchemaModel) => {
    const job = jobs?.jobs?.find((item) =>
      isEqual(
        item.parameters?.importTableParameters,
        schema.import_table_job_parameters
      )
    );
    return (
      job &&
      (job.status === DqoJobHistoryEntryModelStatusEnum.queued ||
        DqoJobHistoryEntryModelStatusEnum.running ||
        DqoJobHistoryEntryModelStatusEnum.waiting)
    );
  };

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup />
      <table className="w-full">
        <thead>
          <tr>
            <th className="py-2 px-4 text-left">Schema Name</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {schemas.map((item) => (
            <tr key={item.schema_name} className="mb-3">
              <td className="py-2 px-4 text-left">{item.schema_name}</td>
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
        </tbody>
      </table>
    </div>
  );
};

export default SchemasView;
