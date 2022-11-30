import React from 'react';
import { useTree } from '../../contexts/treeContext';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';

const Header = () => {
  const { sidebarWidth } = useTree();
  return (
    <div
      className="fixed top-0 right-0 min-h-16 bg-white shadow-header flex items-center justify-between z-10 px-4"
      style={{ left: sidebarWidth }}
    >
      <div>
        <Logo className="w-30" />
      </div>
      <NotificationMenu />
    </div>
  );
};

export default Header;
