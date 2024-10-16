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
import { getParamsFromURL, getSeverity } from '../../utils';
import Button from '../Button';
import SvgIcon from '../SvgIcon';
import IncidentsContextMenu from './IncidentsContextMenu';

const IncidentsTree = () => {
  const dispatch = useActionDispatch();
  const { connections, activeTab, selectedConnections, tabs, loading } =
    useSelector((state: IRootState) => state.incidents);
  const selectedConnection =
    activeTab && activeTab.length > 0 && activeTab !== '/incidents/new-tab'
      ? activeTab?.split('/')[2]
      : '';
  const history = useHistory();
  const filters = {
    open: true,
    acknowledged: true,
    page: 1,
    pageSize: 10
  };

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
          filters
        },
        label: connection?.connection ?? ''
      })
    );
    history.push(url);
  };
  const openCategoryTab = () => {
    const params = getParamsFromURL(window.location.search);
    const label = Object.entries(params).map(([key, value]) => {
      if (key === 'severity') {
        return getSeverity(String(value)) + ' severity incidents';
      }
      return value;
    });

    dispatch(
      addFirstLevelTab({
        url: window.location.pathname + window.location.search,
        value: window.location.pathname + window.location.search,
        state: {
          filters: {
            ...filters,
            ...params
          }
        },
        label: label.join(' / ')
      })
    );

    history.push(window.location.pathname + window.location.search);
  };

  const openCorrectTabFromUrl = () => {
    const path = window.location.pathname.split('/');
    if (
      window.location.pathname === '/incidents' ||
      window.location.pathname === '/incidents/'
    ) {
      if (activeTab && tabs.length > 0) {
        history.push(activeTab);
      }
      return;
    }
    // if (path.length < 3) {
    //   return;
    // }
    const connection = path[2];
    const selectedConnection = connections.find(
      (x) => x.connection === connection
    );

    if (selectedConnection && !path[3]) {
      openFirstLevelTab(selectedConnection);
    } else if (path[2] === '*') {
      openCategoryTab();
    } else if (path[3]) {
      const connection = path[2] || '';
      const year = Number(path[3]);
      const month = Number(path[4]);
      const incidentId = path[5] || '';
      const state = tabs.find(
        (x) =>
          x.url === ROUTES.INCIDENT_DETAIL(connection, year, month, incidentId)
      )?.state;
      dispatch(
        addFirstLevelTab({
          url: ROUTES.INCIDENT_DETAIL(connection, year, month, incidentId),
          value: ROUTES.INCIDENT_DETAIL_VALUE(
            connection,
            year,
            month,
            incidentId
          ),
          state: state || {},
          label: incidentId
        })
      );
      history.push(ROUTES.INCIDENT_DETAIL(connection, year, month, incidentId));
      return;
    }
  };
  useEffect(() => {
    openCorrectTabFromUrl();
  }, [window.location.pathname, connections]);

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
        leftIcon={
          loading && (
            <SvgIcon name="sync" className="w-4 h-4 mr-3 animate-spin" />
          )
        }
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
            <IncidentsContextMenu connection={connection.connection ?? ''} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default IncidentsTree;
