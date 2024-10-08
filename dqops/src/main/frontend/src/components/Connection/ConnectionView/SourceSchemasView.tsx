import { isEqual } from 'lodash';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  DqoJobHistoryEntryModelStatusEnum,
  SchemaRemoteModel
} from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import { DataSourcesApi } from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import Button from '../../Button';
import Loader from '../../Loader';
import SvgIcon from '../../SvgIcon';
import ConnectionActionGroup from './ConnectionActionGroup';

const SourceSchemasView = () => {
  const { connection }: { connection: string } = useDecodedParams();
  const [loading, setLoading] = useState(false);
  const [schemas, setSchemas] = useState<SchemaRemoteModel[]>([]);
  const [error, setError] = useState<string>();

  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const history = useHistory();
  const dispatch = useActionDispatch();

  useEffect(() => {
    setLoading(true);
    DataSourcesApi.getRemoteDataSourceSchemas(connection)
      .then((res) => {
        setSchemas(res.data);
      })
      .catch((err) => {
        setError(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [connection]);

  const onImportTables = (schema: SchemaRemoteModel) => {
    const url = ROUTES.SCHEMA_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema.schemaName ?? '',
      'import-tables'
    );
    const value = ROUTES.SCHEMA_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connection,
      schema.schemaName ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: {},
        label: schema.schemaName
      })
    );
    history.push(url);
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
  const onBack = () => {
    history.push(
      ROUTES.CONNECTION_DETAIL(CheckTypes.SOURCES, connection, 'schemas')
    );
  };

  return (
    <div className="py-4 px-8 text-sm">
      <ConnectionActionGroup />
      <div className="flex items-center justify-between  pb-2">
        <div className="text-lg font-semibold">
          List of schemas present in the data source, imported or not imported
          to DQOps
        </div>
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>
      {loading ? (
        <div className="flex justify-center h-100">
          <Loader isFull={false} className="w-8 h-8 fill-green-700" />
        </div>
      ) : (
        <table className="w-full">
          <thead className="border-b border-gray-300">
            <tr>
              <th className="py-2 pr-4 text-left">Source schema name</th>
              <th className="py-2 px-4 text-left">Import status</th>
              <th className="py-2">Actions</th>{' '}
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
                    width={22}
                    height={22}
                  />
                </td>
                <td className="py-2 px-4">
                  {!isExist(item) && (
                    <div className=" flex justify-center">
                      <Button
                        label="Import tables"
                        color="primary"
                        onClick={() => onImportTables(item)}
                      />
                    </div>
                  )}
                </td>
              </tr>
            ))}
            {error ? <div className="text-red-500">{error}</div> : null}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default SourceSchemasView;
