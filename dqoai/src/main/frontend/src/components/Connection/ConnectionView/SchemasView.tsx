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
import qs from 'query-string';
import { useHistory, useLocation, useParams } from 'react-router-dom';

const SchemasView = () => {
  const { connection }: { connection: string } = useParams();
  const [schemas, setSchemas] = useState<SchemaModel[]>([]);
  const { jobs } = useSelector((state: IRootState) => state.job);
  const history = useHistory();
  const location = useLocation();

  const dispatch = useActionDispatch();

  useEffect(() => {
    SchemaApiClient.getSchemas(connection).then((res) => {
      setSchemas(res.data);
    });
  }, [connection]);

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

  const goToSchemas = () => {
    const params = qs.parse(location.search);
    const searchQuery = qs.stringify({
      ...params,
      tab: 'schemas',
      source: true
    });
    history.replace(`/checks?${searchQuery}`);
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
            <tr
              key={item.schema_name}
              className="border-b border-gray-300 last:border-b-0"
            >
              <td className="py-2 px-4 text-left">{item.schema_name}</td>
              <td className="py-2 px-4 text-left">
                {!isExist(item) && (
                  <Button
                    className="!py-2 !rounded-md"
                    textSize="sm"
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
      <Button
        variant="contained"
        color="primary"
        label="Import more schemas"
        className="mt-4"
        onClick={goToSchemas}
      />
    </div>
  );
};

export default SchemasView;
