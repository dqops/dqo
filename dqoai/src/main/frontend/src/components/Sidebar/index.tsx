import React from 'react';

import ConnectionsTree from '../ConnectionsTree';

const Sidebar = () => {
  return (
    <div className="fixed top-0 left-0 border-r border-gray-300 h-screen overflow-auto w-70 flex flex-col bg-white">
      <ConnectionsTree />
    </div>
  );
};

export default Sidebar;
