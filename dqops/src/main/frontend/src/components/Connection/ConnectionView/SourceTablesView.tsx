import React, { useEffect, useState } from 'react';
import { JobApiClient, DataSourcesApi } from '../../../services/apiClient';
import { RemoteTableListModel } from '../../../api';
import SvgIcon from '../../SvgIcon';
import Loader from '../../Loader';
import Checkbox from '../../Checkbox';
import Button from '../../Button';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  setAdvisorJobId,
  toggleAdvisor
} from '../../../redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { setCurrentJobId } from '../../../redux/actions/source.actions';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { getFirstLevelActiveTab } from '../../../redux/selectors';

const SourceTablesView = () => {
  const {
    checkTypes,
    connection,
    schema
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
  } = useParams();
  const [loading, setLoading] = useState(false);
  const [selectedTables, setSelectedTables] = useState<string[]>([]);
  const [tables, setTables] = useState<RemoteTableListModel[]>([]);

  const dispatch = useActionDispatch();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job
  );
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const fetchSourceTables = async () => {
    setLoading(true);
    setSelectedTables([]);
    DataSourcesApi.getRemoteDataSourceTables(connection, schema)
      .then((res) => {
        setTables(res.data);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchSourceTables();
  }, [connection, schema]);

  const selectAll = () => {
    setSelectedTables(tables.map((item) => item.tableName ?? ''));
  };

  const unselectAll = () => {
    setSelectedTables([]);
  };

  const importSelectedTables = async () => {
    const res = await JobApiClient.importTables(undefined, false, undefined, {
      connectionName: connection,
      schemaName: schema,
      tableNames: selectedTables
    });

    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );
    dispatch(setAdvisorJobId(res.data?.jobId?.jobId ?? 0));
    setJobId(res.data?.jobId?.jobId);
    dispatch(toggleAdvisor(true));
  };

  const importAllTables = async () => {
    const res = await JobApiClient.importTables(undefined, false, undefined, {
      connectionName: connection,
      schemaName: schema,
      tableNames: tables.map((item) => item.tableName ?? '')
    });

    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );
    setJobId(res.data?.jobId?.jobId);
    dispatch(setAdvisorJobId(res.data?.jobId?.jobId ?? 0));
    dispatch(toggleAdvisor(true));
  };

  const onSelectChange = (tableName: string) => {
    if (selectedTables?.includes(tableName)) {
      setSelectedTables(selectedTables.filter((item) => item !== tableName));
    } else {
      setSelectedTables([...selectedTables, tableName]);
    }
  };

  useEffect(() => {
    if (jobId !== 0 && jobId !== undefined && job?.status === 'finished') {
      fetchSourceTables();
    }
  }, [job?.status]);

  return (
    <div className="py-4 px-8">
      {/* <ConnectionActionGroup onImport={onBack} /> */}
      <div className="flex justify-end space-x-4 mb-4">
        <Button
          color="primary"
          label="Select All"
          onClick={selectAll}
          disabled={selectedTables.length === tables.length}
        />
        <Button
          color="primary"
          label="Unselect All"
          onClick={unselectAll}
          disabled={selectedTables.length === 0}
        />
        <Button
          color="primary"
          label="Import selected tables"
          onClick={importSelectedTables}
          disabled={selectedTables.length === 0}
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
        <table className="max-w-300">
          <thead>
            <tr className="border-b border-gray-300">
              <th />
              <th className="py-2 px-4 text-left">Source Table Name</th>
              <th className="py-2 px-4 text-left">Import status</th>
            </tr>
          </thead>
          <tbody>
            {tables.map((item) => (
              <tr
                key={item.tableName}
                className="border-b border-gray-300 last:border-b-0"
              >
                <td className="py-2 px-2 text-left">
                  <div className="flex">
                    <Checkbox
                      onChange={() => onSelectChange(item.tableName ?? '')}
                      checked={
                        selectedTables.indexOf(item.tableName ?? '') > -1
                      }
                    />
                  </div>
                </td>
                <td className="py-2 px-4 text-left">{item.tableName}</td>
                <td className="py-2 pl-14">
                  <SvgIcon
                    name={item.alreadyImported ? 'check' : 'close'}
                    className={
                      item.alreadyImported ? 'text-primary' : 'text-red-700'
                    }
                    width={22}
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

export default SourceTablesView;
