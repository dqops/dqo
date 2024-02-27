import React, { ReactNode, useEffect, useMemo } from 'react';

import Header from '../Header';
import { useDispatch, useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import PageTabs from '../PageTabs';
import { useHistory, useLocation } from 'react-router-dom';
import {
  closeFirstLevelTab,
  setActiveFirstLevelTab
} from '../../redux/actions/definition.actions';
import { TabOption } from '../PageTabs/tab';
import { ROUTES } from '../../shared/routes';
import SensorDetail from '../../pages/SensorDetail';
import CheckDetail from '../../pages/CheckDetail';
import DataDictionary from '../../pages/DataDictionaryConfiguration';
import DataDictionaryItemOverview from '../../pages/DataDictionaryConfiguration/DataDictionaryItemOverview';
import DefaultWebhooksDetail from '../../pages/DefaultWebhooksDetail';
import Definitions from '../../pages/Definitions';
import IncidentConnection from '../../pages/IncidentConnection';
import RuleDetail from '../../pages/RuleDetail';
import SharedCredentialsDetail from '../../pages/SharedCredentialsDetail';
import SingleSharedCredential from '../../pages/SharedCredentialsDetail/SingleSharedCredential';
import UserListDetail from '../../pages/UserListDetail';
import UserDetail from '../../pages/UserListDetail/UserDetail';
import DefaultCheckDetail from '../../pages/DefaultChecksDetail'
import DefaultSchedules from '../../pages/DefaultSchedulesDetail'
import LeftView from './LeftView';
import { useDefinition } from '../../contexts/definitionContext';
import DefaultCheckPatterns from '../../pages/DefaultCheckPatterns/DefaultCheckPatterns';

interface LayoutProps {
  route: string
}

const DefinitionLayout = ({ route }: LayoutProps) => {
  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.definition
  );

  const dispatch = useDispatch();
  const history = useHistory();
  const location = useLocation();

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
    if (activeTab && activeTab !== location.pathname) {
      history.push(activeTab);
    }
  }, [activeTab]);

  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.SENSOR_DETAIL:
        return <SensorDetail/>;
      case ROUTES.PATTERNS.RULE_DETAIL:
        return <RuleDetail />;
      case ROUTES.PATTERNS.CHECK_DETAIL:
        return <CheckDetail />;
      case ROUTES.PATTERNS.CHECK_DEFAULT_DETAIL:
        return <DefaultCheckDetail />;
      case ROUTES.PATTERNS.DEFINITIONS:
        return <Definitions />;
      case ROUTES.PATTERNS.INCIDENT_CONNECTION:
        return <IncidentConnection />;
      case ROUTES.PATTERNS.USERS_LIST_DETAIL:
        return <UserListDetail />;
      case ROUTES.PATTERNS.USER_DETAIL:
        return <UserDetail />;
      case ROUTES.PATTERNS.SCHEDULES_DEFAULT_DETAIL:
        return <DefaultSchedules />;
      case ROUTES.PATTERNS.WEBHOOKS_DEFAULT_DETAIL:
        return <DefaultWebhooksDetail />;
      case ROUTES.PATTERNS.SHARED_CREDENTIALS_LIST_DETAIL:
        return <SharedCredentialsDetail />;
      case ROUTES.PATTERNS.SHARED_CREDENTIALS_DETAIL:
        return <SingleSharedCredential />;
      case ROUTES.PATTERNS.DATA_DICTIONARY_LIST_DETAIL:
        return <DataDictionary />;
      case ROUTES.PATTERNS.DATA_DICTIONARY_DETAIL:
        return <DataDictionaryItemOverview />;
      case ROUTES.PATTERNS.DEFAULT_CHECKS_PATTERNS:
        return <DefaultCheckPatterns />;  
      default:
        return null;
    }
  };

  const renderComponent: ReactNode = getComponent();
  const { sidebarWidth } = useDefinition(); 
  return (
    <div className="flex min-h-screen overflow-hidden z-30">
      <Header />
      <LeftView />
      <div className="flex flex-1">
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: sidebarWidth,
            maxWidth: `calc(100vw - ${sidebarWidth}px)`
          }}
        >
          <div className="flex-1 h-full flex flex-col">
            <PageTabs
              tabs={tabOptions}
              activeTab={activeTab}
              onChange={handleChange}
              onRemoveTab={closeTab}
              limit={7}
            />
            <div
              className=" bg-white border border-gray-300 flex-auto min-h-0 overflow-auto"
            >
              {!!activeTab && pageTabs.length !== 0 && <>{renderComponent}</>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DefinitionLayout;
