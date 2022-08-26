import React, { useMemo } from 'react';
import { useRouteMatch, useHistory } from 'react-router-dom';
import IconButton from "../IconButton";
import SvgIcon from "../SvgIcon";
import Avatar from "react-avatar";

interface HeaderProps {
  backInfo?: {
    text: string;
    path: string;
  }
}

const Header: React.FC<HeaderProps> = ({ backInfo }) => {
  const history = useHistory();

  return (
    <div className="fixed top-0 left-70 right-0 min-h-16 bg-white shadow-header flex items-center justify-between px-4 z-50">
      <IconButton>
        <SvgIcon name="search" className="w-5 h-5 text-gray-500" />
      </IconButton>
      <div className="flex items-center space-x-1">
        <IconButton>
          <SvgIcon name="users" className="w-5 h-5 text-gray-500" />
        </IconButton>
        <IconButton className="!mr-3">
          <div className="relative">
            <SvgIcon name="bell" className="w-5 h-5 text-gray-500" />
            <span className="w-2 h-2 absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-purple-500" />
          </div>
        </IconButton>

        <Avatar src="images/avatars/avatar_1.png" className="!w-10 !h-10" round />
      </div>
    </div>
  );
};

export default Header;
