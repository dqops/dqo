import React, { useEffect, useState } from 'react';
import { JobApiClient, SchemaApiClient } from '../../../services/apiClient';
import { SchemaModel } from '../../../api';
import Button from '../../Button';
import { toggleMenu } from '../../../redux/actions/job.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../../shared/routes";
import { setActiveFirstLevelTab } from '../../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

const SchemasView = () => {
  const { connection, checkTypes }: { connection: string; checkTypes: CheckTypes } = useParams();
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);
  const history = useHistory();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const dispatch = useActionDispatch();

  useEffect(() => {
    SchemaApiClient.getSchemas(connection).then((res) => {
      setSchemas(res.data);
    });
  }, [connection]);

  const onImportTables = (schema: SchemaModel) => {
    history.push(`${ROUTES.CONNECTION_DETAIL(checkTypes, connection, 'schemas')}?import_schema=true&import_table=true&schema=${schema.schema_name}`);
  };

  const goToSchemas = () => {
    history.push(`${ROUTES.CONNECTION_DETAIL(checkTypes, connection, 'schemas')}?import_schema=true`)
  };

  return (
    <div className="py-4 px-8">
      <ConnectionActionGroup />
      <table className="w-full">
        <thead>
          <tr>
            <th className="py-2 pr-4 text-left">Schema Name</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {schemas.map((item) => (
            <tr
              key={item.schema_name}
              className="border-b border-gray-300 last:border-b-0"
            >
              <td className="py-2 pr-4 text-left">{item.schema_name}</td>
              <td className="py-2 px-4 text-left">
                <Button
                  className="!py-2 !rounded-md"
                  textSize="sm"
                  label="Import tables"
                  color="primary"
                  onClick={() => onImportTables(item)}
                  disabled={userProfile.can_manage_data_sources === false}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isSourceScreen && (
        <Button
          variant="contained"
          color="primary"
          label="Import more schemas"
          className="mt-4"
          onClick={goToSchemas}
          disabled={userProfile.can_manage_data_sources === false}
        />
      )}
    </div>
  );
};

export default SchemasView;
