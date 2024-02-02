import React, { useState } from 'react';

const DefinitionContext = React.createContext({} as any);

function DefinitionProvider(props: any) {
  const [sidebarWidth, setSidebarWidth] = useState(310);

  return (
    <DefinitionContext.Provider
      value={{
        sidebarWidth,
        setSidebarWidth
      }}
      {...props}
    />
  );
}

function useDefinition() {
  const context = React.useContext(DefinitionContext);

  if (context === undefined) {
    throw new Error('useDefinition must be used within a DefinitionProvider');
  }
  return context;
}

export { DefinitionProvider, useDefinition };
