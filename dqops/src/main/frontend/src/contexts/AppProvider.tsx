import React from 'react';

import { TreeProvider } from './treeContext';
import { ErrorProvider } from './errrorContext';
import { DashboardProvider } from "./dashboardContext";

function AppProvider({ children }: { children: any }) {
  return (
    <ErrorProvider>
      <TreeProvider>
        <DashboardProvider>
          {children}
        </DashboardProvider>
      </TreeProvider>
    </ErrorProvider>
  );
}

export default AppProvider;
