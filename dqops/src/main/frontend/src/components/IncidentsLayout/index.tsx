import React, { ReactNode, useEffect, useMemo } from 'react';

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
import { urlencodeDecoder } from '../../utils';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import Header from '../Header';
import PageTabs from '../PageTabs';
import { TabOption } from '../PageTabs/tab';
import IncidentsTree from './IncidentsTree';

interface LayoutProps {
  route: string;
}

const IncidentsLayout = ({ route }: LayoutProps) => {
  const { objectNotFound, setObjectNotFound } = useTree()
  
  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.incidents
  );
  
  const dispatch = useDispatch();
  const history = useHistory();


  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(tab.value));
    history.push(urlencodeDecoder(tab?.url ?? ''));
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(value));
  };

  const tabOptions = useMemo(() => {
    return (
      pageTabs?.map((item) => ({
        value: item.url,
        url: item.url,
        label: item.label
      })) || []
    );
  }, [pageTabs]);

  useEffect(() => {
    if (activeTab) {
      dispatch(setActiveFirstLevelTab(activeTab));
      history.push(activeTab);
    }
  }, [activeTab]);

  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.INCIDENT_DETAIL:
        return <IncidentDetail/>;
      case ROUTES.PATTERNS.INCIDENTS:
        return <Incidents/>;
      case ROUTES.PATTERNS.INCIDENT_CONNECTION:
        return <IncidentConnection/>;  
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
              {!!activeTab && activeTab !== '/incidents/new-tab' && <>{renderComponent}</>}
            </div>
          </div>
        </div>
      </div>
      <ConfirmDialog
      open={objectNotFound}
      onConfirm={() => new Promise(() => {dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false)})}
      isCancelExcluded={true} 
      onClose={() => {dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false)}}
      message='The definition of this object was deleted in the DQOps user home. The tab will be closed.'/>
    </div>
  );
};

export default IncidentsLayout;
