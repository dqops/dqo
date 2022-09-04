import React from 'react';

import ConnectionsTree from '../ConnectionsTree';

interface ISidebarProps {
}

const Sidebar: React.FC<ISidebarProps> = () => {
  return (
    <div className="fixed top-0 left-0 bg-sidebar h-screen overflow-auto w-70 flex flex-col bg-white">
      <ConnectionsTree />
    </div>
  );
};

export default Sidebar;
