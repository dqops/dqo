import React from 'react';
import { useTree } from '../../contexts/treeContext';
import NotificationMenu from '../NotificationMenu';

const Header = () => {
  const { sidebarWidth } = useTree();
  return (
    <div
      className="fixed top-0 right-0 min-h-16 bg-white shadow-header flex items-center justify-end z-10 px-4"
      style={{ left: sidebarWidth }}
    >
      <NotificationMenu />
    </div>
  );
};

export default Header;
