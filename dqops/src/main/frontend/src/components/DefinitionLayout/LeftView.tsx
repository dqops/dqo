import React, { useCallback, useEffect, useRef, useState } from 'react';
import DefinitionTree from './DefinitionTree';
import { useDefinition } from '../../contexts/definitionContext';

export default function LeftView() {
  const { setSidebarWidth, sidebarWidth } = useDefinition();
  const sidebarRef = useRef<HTMLDivElement>(null);
  const [isResizing, setIsResizing] = useState(false);

  const stopResizing = useCallback(() => {
    setIsResizing(false);
  }, []);

  const startResizing = useCallback((mouseDownEvent: MouseEvent) => {
    setIsResizing(true);
    mouseDownEvent.preventDefault();
    mouseDownEvent.stopPropagation();
  }, []);

  const resize = useCallback(
    (mouseMoveEvent: MouseEvent) => {
      if (isResizing) {
        const newWidth =
          mouseMoveEvent.clientX -
          (sidebarRef.current as HTMLDivElement).getBoundingClientRect().left;
        if (newWidth < 240 || newWidth > 700) return;

        setSidebarWidth(newWidth);
        mouseMoveEvent.preventDefault();
        mouseMoveEvent.stopPropagation();
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
      className="fixed left-0 top-14 bottom-0 overflow-y-auto shadow border-r border-gray-300 pl-4 pt-6 bg-white overflow-x-hidden"
      ref={sidebarRef}
      style={{ width: sidebarWidth }}
    >
      <DefinitionTree />
      <div
        className="cursor-ew-resize fixed bottom-0 w-2 transform -translate-x-1/2 z-20 top-16"
        onMouseDown={(event) => startResizing(event as any)}
        style={{ left: sidebarWidth, userSelect: 'none' }}
      />
    </div>
  );
}
