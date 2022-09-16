import React from 'react';

import { TabProvider } from './tabContext';

function AppProvider({ children }: { children: any }) {
  return <TabProvider>{children}</TabProvider>;
}

export default AppProvider;
