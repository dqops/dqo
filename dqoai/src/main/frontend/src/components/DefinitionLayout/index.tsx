import React from 'react';

import LeftView from "../Dashboards/LeftView";
import Header from "../Header";
import DefinitionTree from "./DefinitionTree";

interface LayoutProps {
  children?: any;
}

const DefinitionLayout = ({ children }: LayoutProps) => {
  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header />
      <DefinitionTree />
      <div className="flex flex-1">
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: 320,
            maxWidth: `calc(100vw - 320px)`
          }}
        >
          {children}
        </div>
      </div>
    </div>
  );
};

export default DefinitionLayout;
