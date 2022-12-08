import React, { useEffect, useState } from 'react';
import { JobApiClient, SourceSchemasApi } from '../../../services/apiClient';
import {
  DqoJobHistoryEntryModelStatusEnum,
  SchemaRemoteModel
} from '../../../api';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import Loader from '../../Loader';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { toggleMenu } from '../../../redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { isEqual } from 'lodash';

interface ISourceSchemasViewProps {
  connectionName: string;
}

const SourceSchemasView = ({ connectionName }: ISourceSchemasViewProps) => {
  const [loading, setLoading] = useState(false);
  const [schemas, setSchemas] = useState<SchemaRemoteModel[]>([]);
  const { jobs } = useSelector((state: IRootState) => state.job);

  const dispatch = useActionDispatch();

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

  const onImportTables = (schema: SchemaRemoteModel) => {
    JobApiClient.importTables(schema.importTableJobParameters);
    dispatch(toggleMenu(true));
  };

  const isExist = (schema: SchemaRemoteModel) => {
    const job = jobs?.jobs?.find((item) =>
      isEqual(
        item.parameters?.importTableParameters,
        schema.importTableJobParameters
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
                    name={item.alreadyImported ? 'check' : 'close'}
                    className={
                      item.alreadyImported ? 'text-green-700' : 'text-red-700'
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
          </tbody>
        </table>
      )}
    </div>
  );
};

export default SourceSchemasView;
