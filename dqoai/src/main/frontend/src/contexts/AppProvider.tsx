import React from 'react';

import { TreeProvider } from './treeContext';
import { NotificationProvider } from './notificationContext';

function AppProvider({ children }: { children: any }) {
  return (
    <NotificationProvider>
      <TreeProvider>{children}</TreeProvider>
    </NotificationProvider>
  );
}

export default AppProvider;
