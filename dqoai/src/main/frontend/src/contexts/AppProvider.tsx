import React from 'react';

import { TreeProvider } from './treeContext';
import { NotificationProvider } from './notificationContext';
import { DashboardProvider } from "./dashboardContext";

function AppProvider({ children }: { children: any }) {
  return (
    <NotificationProvider>
      <TreeProvider>
        <DashboardProvider>
          {children}
        </DashboardProvider>
      </TreeProvider>
    </NotificationProvider>
  );
}

export default AppProvider;
