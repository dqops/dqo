import React, { useEffect, useMemo } from 'react';

import Header from '../Header';
import IncidentsTree from './IncidentsTree';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import PageTabs from '../PageTabs';
import { useHistory} from 'react-router-dom';
import {
  addFirstLevelTab,
  closeFirstLevelTab,
  setActiveFirstLevelTab
} from '../../redux/actions/incidents.actions';
import { TabOption } from '../PageTabs/tab';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { useTree } from '../../contexts/treeContext';
import { ROUTES } from '../../shared/routes';

interface LayoutProps {
  children?: any;
}

const IncidentsLayout = ({ children }: LayoutProps) => {
  const { objectNotFound, setObjectNotFound } = useTree()
  
  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.incidents
  );
  
  const dispatch = useDispatch();
  const history = useHistory();


  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(tab.value));
    history.push(tab?.url ?? '');
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
    if (activeTab && activeTab.length !== 0 && window.location.pathname !== '/incidents/' && window.location.pathname !== '/incidents') {
      history.push(activeTab);
    }
  }, [activeTab]);

  useEffect(() => {
    if (window.location.pathname === '/incidents/' || window.location.pathname === '/incidents') {
      const url = ROUTES.INCIDENT_CONNECTION('new-tab')
      dispatch(addFirstLevelTab({
        url,
        value: '',
        state: {
          filters: {
            openIncidents: true,
            acknowledgedIncidents: true,
            page: 1,
            pageSize: 50
          }
        },
        label: 'New Tab'
      }))
    }
  }, [window.location]);

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
              {!!activeTab && activeTab !== '/incidents/new-tab' && <div>{children}</div>}
            </div>
          </div>
        </div>
      </div>
      <ConfirmDialog
      open={objectNotFound}
      onConfirm={() => new Promise(() => {dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false)})}
      isCancelExcluded={true} 
      onClose={() => {dispatch(closeFirstLevelTab(activeTab)), setObjectNotFound(false)}}
      message='The definition of this object was deleted in DQOps user home. The tab will be closed.'/>
    </div>
  );
};

export default IncidentsLayout;
