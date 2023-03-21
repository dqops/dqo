import React, { useCallback, useEffect, useState } from 'react';

import { ITab } from '../shared/interfaces';
import {AuthenticatedDashboardModel, DashboardsFolderSpec, DashboardSpec} from "../api";
import { DashboardsApi } from "../services/apiClient";

const DashboardContext = React.createContext({} as any);

function DashboardProvider(props: any) {
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [activeTab, setActiveTab] = useState<string>();
  const [openedDashboards, setOpenedDashboards] = useState<AuthenticatedDashboardModel[]>([]);
  const [dashboardStatus, setDashboardStatus] = useState<Record<string, boolean>>({});

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

  const changeActiveTab = async (dashboard: DashboardSpec, folder: string, folders: DashboardsFolderSpec[], isNew = false) => {
    const existTab = tabs.find((item) => item.value === dashboard.dashboard_name);
    if (existTab) {
      setActiveTab(dashboard.dashboard_name);
    } else {
      const newTab = {
        label: dashboard.dashboard_name ?? '',
        value: dashboard.dashboard_name ?? '',
      };

      if (activeTab) {
        const newTabs = isNew
          ? [...tabs, newTab]
          : tabs.map((item) => (item.value === activeTab ? newTab : item));
        setTabs(newTabs);
      } else {
        setTabs([newTab]);
      }
      setActiveTab(dashboard.dashboard_name);

      let res: any;
      if (folders.length === 0) {
        res = await DashboardsApi.getDashboardLevel1(folder, dashboard.dashboard_name ?? '');
      } else if (folders.length === 1) {
        res = await DashboardsApi.getDashboardLevel2(folders[0].folder_name || '', folder, dashboard.dashboard_name ?? '');
      } else if (folders.length === 2) {
        res = await DashboardsApi.getDashboardLevel3(folders[0].folder_name || '', folders[1].folder_name || '', folder, dashboard.dashboard_name ?? '');
      } else if (folders.length === 3) {
        res = await DashboardsApi.getDashboardLevel4(folders[0].folder_name || '', folders[1].folder_name || '', folders[2].folder_name || '', folder, dashboard.dashboard_name ?? '');
      }

      const authenticatedDashboard: AuthenticatedDashboardModel = res?.data;
      setOpenedDashboards([...openedDashboards, authenticatedDashboard]);
    }
  };

  useEffect(() => {
    onAddTab();
  }, []);

  const toggleDashboardFolder = useCallback((key: string) => {
    setDashboardStatus({
      ...dashboardStatus,
      [key]: !dashboardStatus[key]
    });
  }, [dashboardStatus]);

  const openDashboardFolder = useCallback((keys: string[]) => {
    setDashboardStatus({
      ...dashboardStatus,
      ...keys.reduce((obj, key) => ({ ...obj, [key]: true }), {})
    });
  }, [dashboardStatus]);

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
