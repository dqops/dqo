import React, { useCallback, useEffect, useRef, useState } from 'react';

import { useHistory } from 'react-router-dom';

import { useSelector } from 'react-redux';
import { useTree } from '../../contexts/treeContext';
import { IRootState } from '../../redux/reducers';
import { CheckTypes } from "../../shared/routes";
import { useDecodedParams } from '../../utils';
import Button from '../Button';
import Tree from '../MainLayout/Tree';
import SvgIcon from '../SvgIcon';

const Sidebar = () => {
  const history = useHistory();
  const sidebarRef = useRef<HTMLDivElement>(null);
 
  const [isResizing, setIsResizing] = useState(false);
  const { sidebarWidth, setSidebarWidth, treeData } = useTree();
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const startResizing = useCallback(() => {
    setIsResizing(true);
  }, []);

  const stopResizing = useCallback(() => {
    setIsResizing(false);
  }, []);

  const resize = useCallback(
    (mouseMoveEvent: MouseEvent) => {
      if (isResizing) {
        const newWidth =
          mouseMoveEvent.clientX -
          (sidebarRef.current as HTMLDivElement).getBoundingClientRect().left;
        if (newWidth < 240 || newWidth > 700) return;

        setSidebarWidth(newWidth);
      }
    },
    [isResizing]
  );

  useEffect(() => {
    window.addEventListener('mousemove', resize);
    window.addEventListener('mouseup', stopResizing);
    return () => {
      window.removeEventListener('mousemove', resize);
      window.removeEventListener('mouseup', stopResizing);
    };
  }, [resize, stopResizing]);

  return (
    <div
      className="fixed top-16 left-0 border-r border-gray-300 h-screen overflow-y-auto overflow-x-hidden flex flex-col bg-white py-4 z-50"
      ref={sidebarRef}
      // onMouseDown={(e) => e.preventDefault()}
      style={{ width: sidebarWidth, maxHeight: 'calc(100vh - 64px)' }}
    >
      <div className="px-4 flex mb-0">
        {(checkTypes === 'sources' || Object.keys(treeData).length === 0) ? (
          <Button
            label="Add connection"
            color="primary"
            className="px-4"
            leftIcon={<SvgIcon name="add" className="text-white mr-2 w-5" />}
            onClick={() => history.push('/sources/create')}
            disabled={userProfile.can_manage_data_sources !== true}
          />
        ) : (
          <div />
        )}
      </div>
      <Tree />
      <div
        className="cursor-ew-resize fixed bottom-0 w-2 transform -translate-x-1/2 z-50 top-16"
        onMouseDown={startResizing}
        style={{ left: sidebarWidth, userSelect: 'none' }}
      />
    </div>
  );
};

export default Sidebar;
