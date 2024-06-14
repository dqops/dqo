import React from 'react';

import PropTypes from 'prop-types';

import { useTree } from '../../contexts/treeContext';
import Header from '../Header';
import Sidebar from '../Sidebar';

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
          className="mt-16 p-5 overflow-auto relative flex-1"
          style={{
            marginLeft: sidebarWidth,
            maxWidth: `calc(100vw - ${sidebarWidth}px)`,
            maxHeight: 'calc(100vh - 64px)',
            backgroundColor: '#F9FAFC'
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
