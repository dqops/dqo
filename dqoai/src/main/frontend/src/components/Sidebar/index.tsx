import React from 'react';

import { useHistory } from 'react-router-dom';

import Button from '../Button';
import ConnectionsTree from '../ConnectionsTree';
import SvgIcon from '../SvgIcon';

const Sidebar = () => {
  const history = useHistory();

  return (
    <div className="fixed top-0 left-0 border-r border-gray-300 h-screen overflow-auto w-70 flex flex-col bg-white py-4">
      <div className="px-4 flex justify-end mb-4">
        <Button
          label="Create new connection"
          color="primary"
          className="px-4"
          leftIcon={<SvgIcon name="add" className="mr-2 w-5" />}
          onClick={() => history.push('/')}
        />
      </div>
      <ConnectionsTree />
    </div>
  );
};

export default Sidebar;
