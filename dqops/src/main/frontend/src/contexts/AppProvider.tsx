import React from 'react';

import { TreeProvider } from './treeContext';
import { ErrorProvider } from './errrorContext';
import { DashboardProvider } from "./dashboardContext";
import { DefinitionProvider } from './definitionContext';

function AppProvider({ children }: { children: any }) {
  return (
    <ErrorProvider>
      <TreeProvider>
        <DashboardProvider>
            <DefinitionProvider>
              {children}
            </DefinitionProvider>
        </DashboardProvider>
      </TreeProvider>
    </ErrorProvider>
  );
}

export default AppProvider;
