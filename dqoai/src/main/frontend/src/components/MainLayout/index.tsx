import React from 'react';

import PropTypes from 'prop-types';

import Header from '../Header';
import Sidebar from '../Sidebar';
import { useTree } from '../../contexts/treeContext';

interface LayoutProps {
  children?: any;
}

const MainLayout = ({ children }: LayoutProps) => {
  const { sidebarWidth } = useTree();

  return (
    <div className="flex min-h-screen overflow-hidden">
      <Sidebar />
      <div className="flex flex-1">
        <Header />
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: sidebarWidth,
            maxWidth: `calc(100vw - ${sidebarWidth}px)`
          }}
        >
          {children}
        </div>
      </div>
    </div>
  );
};

MainLayout.propTypes = {
  children: PropTypes.any.isRequired
};

export default MainLayout;
