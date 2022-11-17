import React from 'react';

import { TreeProvider } from './treeContext';

function AppProvider({ children }: { children: any }) {
  return <TreeProvider>{children}</TreeProvider>;
}

export default AppProvider;
