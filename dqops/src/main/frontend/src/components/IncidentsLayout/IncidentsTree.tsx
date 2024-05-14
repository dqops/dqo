import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { IncidentsPerConnectionModel } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  addSelectedConnection,
  getConnections
} from '../../redux/actions/incidents.actions';
import { IRootState } from '../../redux/reducers';
import { ROUTES } from '../../shared/routes';
import Button from '../Button';
import SvgIcon from '../SvgIcon';

const IncidentsTree = () => {
  const dispatch = useActionDispatch();
  const { connections, activeTab, selectedConnections } = useSelector(
    (state: IRootState) => state.incidents
  );
  const selectedConnection =
    activeTab && activeTab.length > 0 && activeTab !== '/incidents/new-tab'
      ? activeTab?.split('/')[2]
      : '';
  const history = useHistory();

  useEffect(() => {
    dispatch(getConnections());
  }, []);

  const onRefresh = () => {
    dispatch(getConnections());
  };

  const openFirstLevelTab = (connection: IncidentsPerConnectionModel) => {
    openConnection(connection);
    const url = ROUTES.INCIDENT_CONNECTION(connection?.connection ?? '');
    if (activeTab === url) {
      return;
    }
    dispatch(
      addFirstLevelTab({
        url,
        value: ROUTES.INCIDENT_CONNECTION_VALUE(connection?.connection ?? ''),
        state: {
          filters: {
            openIncidents: true,
            acknowledgedIncidents: true,
            page: 1,
            pageSize: 50
          }
        },
        label: connection?.connection ?? ''
      })
    );
    history.push(url);
  };

  const openConnection = (connection: IncidentsPerConnectionModel) => {
    dispatch(
      addSelectedConnection({
        [connection.connection ?? '']: moment().format('YYYY-MM-DD HH:mm:ss')
      })
    );
  };

  const isExpired = (connectionDate?: number, localStorageDate?: string) => {
    if (!connectionDate) {
      return false;
    }

    if (!localStorageDate && connectionDate) {
      return true;
    }
    return (
      new Date(connectionDate).getTime() >
      new Date(localStorageDate ?? '').getTime()
    );
  };

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 bg-white">
      <Button
        className="w-full mb-4"
        label="Refresh"
        variant="outlined"
        color="primary"
        onClick={onRefresh}
      />

      <div>
        {connections.map((connection, index) => (
          <div
            key={index}
            className={clsx(
              'flex space-x-2 py-1 px-2 flex-1 w-full text-[13px] cursor-pointer',
              selectedConnection === connection.connection ? 'bg-gray-100' : '',
              isExpired(
                connection.mostRecentFirstSeen,
                selectedConnections?.[connection.connection ?? '']
              )
                ? 'font-bold '
                : ''
            )}
            onClick={() => openFirstLevelTab(connection)}
          >
            <SvgIcon name="database" className={clsx('w-4 shrink-0 min-w-4')} />
            <div className="flex-1 truncate mr-7">{connection.connection}</div>
            {connection.openIncidents ? (
              <div>({connection.openIncidents})</div>
            ) : null}
          </div>
        ))}
      </div>
    </div>
  );
};

export default IncidentsTree;
