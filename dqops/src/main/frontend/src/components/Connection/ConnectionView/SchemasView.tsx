import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { SchemaModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import { IRootState } from '../../../redux/reducers';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import Button from '../../Button';
import ConnectionActionGroup from './ConnectionActionGroup';

const SchemasView = () => {
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useParams();
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);
  const history = useHistory();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const dispatch = useActionDispatch();

  useEffect(() => {
    SchemaApiClient.getSchemas(connection).then((res) => {
      setSchemas(res.data);
    });
  }, [connection]);

  const onImportTables = (schema: SchemaModel) => {
    const url = ROUTES.SCHEMA_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema.schema_name ?? '',
      'import-tables'
    );
    const value = ROUTES.SCHEMA_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connection,
      schema.schema_name ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: {},
        label: schema.schema_name
      })
    );
    history.push(url);
  };

  const goToSchemas = () => {
    history.push(
      `${ROUTES.CONNECTION_DETAIL(
        checkTypes,
        connection,
        'schemas'
      )}?import_schema=true`
    );
  };

  const goToTable = (schema: string, tab: string) => {
    const url = ROUTES.SCHEMA_LEVEL_PAGE(checkTypes, connection, schema, tab);
    const value = ROUTES.SCHEMA_LEVEL_VALUE(checkTypes, connection, schema);

    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value,
        label: schema
      })
    );
    history.push(url);
  };

  return (
    <div className="py-4 px-8 text-sm">
      {isSourceScreen && <ConnectionActionGroup />}
      <table className="w-full">
        <thead>
          <tr>
            <th className="py-2 pl-2 text-left">Schema name</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {schemas.map((item) => (
            <tr
              key={item.schema_name}
              className="border-b border-gray-300 last:border-b-0 relative"
            >
              <td>
                <Button
                  label={item.schema_name}
                  variant="text"
                  className="underline px-2 text-sm"
                  onClick={() => goToTable(item.schema_name ?? '', 'tables')}
                />
              </td>
              {isSourceScreen ? (
                <td className="left-80 absolute py-1 px-4 text-left">
                  <Button
                    className="!py-2 !rounded-md text-sm"
                    textSize="sm"
                    label="Import tables"
                    color="primary"
                    onClick={() => onImportTables(item)}
                    disabled={userProfile.can_manage_data_sources !== true}
                  />
                </td>
              ) : (
                <div className="left-80 absolute">
                  <Button
                    className="text-sm"
                    label="Manage checks"
                    color="primary"
                    variant="text"
                    onClick={() =>
                      goToTable(item.schema_name ?? '', 'multiple_checks')
                    }
                    disabled={userProfile.can_manage_data_sources !== true}
                  />
                </div>
              )}
            </tr>
          ))}
        </tbody>
      </table>
      {isSourceScreen && (
        <Button
          variant="contained"
          color="primary"
          label="Import more schemas"
          className="mt-4 text-sm"
          onClick={goToSchemas}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      )}
    </div>
  );
};

export default SchemasView;
