import React, { useCallback, useEffect, useState } from 'react';

import { ITab } from '../shared/interfaces';
import {
  AuthenticatedDashboardModel,
  DashboardsFolderSpec,
  DashboardSpec
} from '../api';
import { DashboardsApi } from '../services/apiClient';

const DashboardContext = React.createContext({} as any);

function DashboardProvider(props: any) {
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();
  const [openedDashboards, setOpenedDashboards] = useState<
    AuthenticatedDashboardModel[]
  >([]);
  const [dashboardStatus, setDashboardStatus] = useState<
    Record<string, boolean>
  >({});
  const [sidebarWidth, setSidebarWidth] = useState(310);
  const [error, setError] = useState<Record<string, boolean>>({});

  const closeTab = (value: string) => {
    const newTabs = tabs.filter((item) => item.value !== value);
    setTabs(newTabs);
    if (value === activeTab) {
      setActiveTab(newTabs[newTabs.length - 1]?.value);
    }
  };

  const onAddTab = () => {
    const arr = tabs
      .filter((item) => item.type === 'editor')
      .map((item) => parseInt(item.value, 10));
    const maxEditor = Math.max(...arr, 0);

    const newTab = {
      label: `New Tab`,
      value: `${maxEditor + 1}`,
      type: 'editor'
    };

    setTabs([...tabs, newTab]);
    setActiveTab(newTab.value);
  };

  const changeActiveTab = async (
    dashboard: DashboardSpec,
    folder: string,
    folders: DashboardsFolderSpec[],
    key: string,
    isNew = false
  ) => {
    const existTab = tabs.find((item) => item.value === key);
    if (existTab) {
      setActiveTab(key);
    } else {
      const newTab = {
        label: dashboard?.dashboard_name ?? '',
        value: key ?? ''
      };

      if (activeTab) {
        const activeNode = tabs.find((item) => item.value === activeTab);
        const editorTab = tabs.find((item) => item.type === 'editor');
        if (activeNode?.type === 'editor') {
          const newTabs = tabs.map((item) =>
            item.value === activeTab ? newTab : item
          );
          setTabs(newTabs);
        } else if (editorTab) {
          const newTabs = tabs.map((item) =>
            item.value === editorTab.value ? newTab : item
          );
          setTabs(newTabs);
        } else {
          const newTabs = isNew
            ? [...tabs, newTab]
            : tabs.map((item) => (item.value === activeTab ? newTab : item));
          setTabs(newTabs);
        }
      } else {
        setTabs([newTab]);
      }
      setActiveTab(key);
      let tabId = '';
      try {
        if (folders.length === 0) {
          tabId = [folder, dashboard?.dashboard_name].join('-');
            const res = await DashboardsApi.getDashboardLevel1(
            folder,
            dashboard?.dashboard_name ?? '',
            window.location.origin
          );
      
          const authenticatedDashboard: AuthenticatedDashboardModel = res.data;
          setOpenedDashboards([...openedDashboards, authenticatedDashboard]);
      
          setError((prev) => ({
            ...prev,
            [tabId]: false
          }));
        } else if (folders.length === 1) {
          tabId = [
            folders[0].folder_name,
            folder,
            dashboard?.dashboard_name
          ].join('-');
      
          const res = await DashboardsApi.getDashboardLevel2(
            folders[0].folder_name || '',
            folder,
            dashboard?.dashboard_name ?? '',
            window.location.origin
          );
          const authenticatedDashboard: AuthenticatedDashboardModel = res.data;
          setOpenedDashboards([...openedDashboards, authenticatedDashboard]);
      
          setError((prev) => ({
            ...prev,
            [tabId]: false
          }));
        } else if (folders.length === 2) {
          tabId = [
            folders[0].folder_name,
            folders[1].folder_name,
            folder,
            dashboard?.dashboard_name
          ].join('-');
      
          const res = await DashboardsApi.getDashboardLevel3(
            folders[0].folder_name || '',
            folders[1].folder_name || '',
            folder,
            dashboard?.dashboard_name ?? '',
            window.location.origin
          );
          const authenticatedDashboard: AuthenticatedDashboardModel = res.data;
          setOpenedDashboards([...openedDashboards, authenticatedDashboard]);
      
          setError((prev) => ({
            ...prev,
            [tabId]: false
          }));
        } else if (folders.length === 3) {
          tabId = [
            folders[0].folder_name,
            folders[1].folder_name,
            folders[2].folder_name,
            folder,
            dashboard?.dashboard_name
          ].join('-');
      
          const res = await DashboardsApi.getDashboardLevel4(
            folders[0].folder_name || '',
            folders[1].folder_name || '',
            folders[2].folder_name || '',
            folder,
            dashboard?.dashboard_name ?? '',
            window.location.origin
          );
          const authenticatedDashboard: AuthenticatedDashboardModel = res.data;
          setOpenedDashboards([...openedDashboards, authenticatedDashboard]);
      
          setError((prev) => ({
            ...prev,
            [tabId]: false
          }));
        }   
      } catch (err) {
        setError((prev) => ({
          ...prev,
          [tabId]: true
        }));
      }
    }
  };

  useEffect(() => {
    onAddTab();
  }, []);

  const toggleDashboardFolder = useCallback(
    (key: string) => {
      setDashboardStatus({
        ...dashboardStatus,
        [key]: !dashboardStatus[key]
      });
    },
    [dashboardStatus]
  );

  const openDashboardFolder = useCallback(
    (keys: string[]) => {
      setDashboardStatus({
        ...dashboardStatus,
        ...keys.reduce((obj, key) => ({ ...obj, [key]: true }), {})
      });
    },
    [dashboardStatus]
  );

  return (
    <DashboardContext.Provider
      value={{
        tabs,
        openedDashboards,
        activeTab,
        setActiveTab,
        closeTab,
        onAddTab,
        changeActiveTab,
        dashboardStatus,
        toggleDashboardFolder,
        openDashboardFolder,
        sidebarWidth,
        setSidebarWidth,
        error
      }}
      {...props}
    />
  );
}

function useDashboard() {
  const context = React.useContext(DashboardContext);

  if (context === undefined) {
    throw new Error('useDashboard must be used within a DashboardProvider');
  }
  return context;
}

export { DashboardProvider, useDashboard };
