import React, { useCallback, useEffect, useRef, useState } from 'react';

import { useHistory } from 'react-router-dom';

import Button from '../Button';
import SvgIcon from '../SvgIcon';
import { useTree } from '../../contexts/treeContext';
import Tree from '../Tree';

const Sidebar = () => {
  const history = useHistory();
  const sidebarRef = useRef<HTMLDivElement>(null);
  const [isResizing, setIsResizing] = useState(false);
  const { sidebarWidth, setSidebarWidth, sourceRoute } = useTree();

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
      onMouseDown={(e) => e.preventDefault()}
      style={{ width: sidebarWidth, maxHeight: 'calc(100vh - 64px)' }}
    >
      <div className="px-4 flex mb-0">
        {sourceRoute === 'sources' ? (
          <Button
            label="Create new connection"
            color="primary"
            className="px-4"
            leftIcon={<SvgIcon name="add" className="text-white mr-2 w-5" />}
            onClick={() => history.push('/create')}
          />
        ) : (
          <div />
        )}
      </div>
      <Tree />
      <div
        className="cursor-ew-resize fixed bottom-0 w-2 transform -translate-x-1/2 z-50 top-16"
        onMouseDown={startResizing}
        style={{ left: sidebarWidth }}
      />
    </div>
  );
};

export default Sidebar;
