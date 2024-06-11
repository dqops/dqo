import React, { ReactNode, useMemo } from 'react';

import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { useTree } from '../../contexts/treeContext';
import IncidentConnection from '../../pages/IncidentConnection';
import IncidentDetail from '../../pages/IncidentDetail';
import Incidents from '../../pages/Incidents';
import {
  closeFirstLevelTab,
  setActiveFirstLevelTab
} from '../../redux/actions/incidents.actions';
import { IRootState } from '../../redux/reducers';
import { ROUTES } from '../../shared/routes';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import Header from '../Header';
import PageTabs from '../PageTabs';
import { TabOption } from '../PageTabs/tab';
import IncidentsTree from './IncidentsTree';

interface LayoutProps {
  route: string;
}

const IncidentsLayout = ({ route }: LayoutProps) => {
  const { objectNotFound, setObjectNotFound } = useTree();

  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.incidents
  );

  const dispatch = useDispatch();
  const history = useHistory();

  const handleChange = (tab: TabOption) => {
    if (tab.url === window.location.pathname) {
      return;
    }
    dispatch(setActiveFirstLevelTab(tab.value));
    history.push(tab.value);
  };

  const closeTab = (value: string, _?: boolean, tab?: string) => {
    dispatch(closeFirstLevelTab(value));

    if (pageTabs.length === 1) {
      history.push(`/incidents`);
      return;
    }

    if (value === activeTab) {
      const tabIndex = pageTabs.findIndex((item) => item.url === value);
      if (tabIndex === 0 && pageTabs.length > 1) {
        history.push(pageTabs[1].url);
        return;
      } else {
        history.push(pageTabs[tabIndex - 1].url || pageTabs[0].url);
      }
    }

    if (tab) {
      history.push(tab);
      return;
    } else {
      //
    }
  };

  const tabOptions = useMemo(() => {
    return (
      pageTabs?.map((item) => ({
        value: item.url,
        url: item.url,
        label: item.label,
        state: { ...item.state }
      })) || []
    );
  }, [pageTabs]);

  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.INCIDENT_DETAIL:
        return <IncidentDetail />;
      case ROUTES.PATTERNS.INCIDENTS:
        return <Incidents />;
      case ROUTES.PATTERNS.INCIDENT_CONNECTION:
        return <IncidentConnection />;
      default:
        return null;
    }
  };

  const renderComponent: ReactNode = getComponent();

  return (
    <div
      className="flex min-h-screen h-full overflow-hidden "
      style={{ height: '100%', overflowY: 'hidden' }}
    >
      <Header />
      <IncidentsTree />
      <div
        className="flex min-h-screen flex-1 "
        style={{ height: '100%', overflowY: 'hidden' }}
      >
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: 320,
            maxWidth: `calc(100vw - 320px)`
          }}
        >
          <div className="flex-1 h-full flex flex-col ">
            <PageTabs
              tabs={tabOptions}
              activeTab={activeTab}
              onChange={handleChange}
              onRemoveTab={closeTab}
              limit={7}
            />
            <div className="bg-white border border-gray-300 flex-auto min-h-0 overflow-auto">
              {!!activeTab && activeTab !== '/incidents/new-tab' && (
                <>{renderComponent}</>
              )}
            </div>
          </div>
        </div>
      </div>
      <ConfirmDialog
        open={objectNotFound}
        onConfirm={() =>
          new Promise(() => {
            dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false);
          })
        }
        isCancelExcluded={true}
        onClose={() => {
          dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false);
        }}
        message="The definition of this object was deleted in the DQOps user home. The tab will be closed."
      />
    </div>
  );
};

export default IncidentsLayout;
