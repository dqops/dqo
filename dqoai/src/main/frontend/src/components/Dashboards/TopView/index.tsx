import React from "react";
import NotificationMenu from "../../NotificationMenu";
import { useHistory } from "react-router-dom";

const TopView = () => {
  const history = useHistory();

  return (
    <div className="fixed top-0 left-70 right-0 min-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4">
      <div className="flex items-center">
        <div
          className="px-4 cursor-pointer"
          onClick={() => history.push('/checks')}
        >
          Data Quality Checks
        </div>
        <div
          className="px-4 cursor-pointer font-bold"
          onClick={() => history.push('/dashboards')}
        >
          Data Quality Dashboards
        </div>
      </div>
      <NotificationMenu />
    </div>
  );
};

export default TopView;
