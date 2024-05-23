import clsx from 'clsx';
import React from 'react';
const renderItem = (label: string, key: string, toRotate?: boolean) => {
  const styles = {
    rotate270: {
      transform: 'rotate(270deg)'
    },
    originBottomLeft: {
      transformOrigin: 'bottom left'
    },
    absoluteBottomLeft: {
      position: 'absolute',
      bottom: 2,
      left: 30
    }
  };

  return (
    <th
      className="px-4 "
      key={key}
      style={
        toRotate
          ? {}
          : {
              marginTop: '-30px'
            }
      }
    >
      <div
        className={clsx(
          'flex text-sm relative h-29',
          toRotate ? 'w-1 items-start' : 'items-end'
        )}
      >
        <span
          className={clsx(
            'inline-block',
            toRotate ? 'rotate-90 origin-bottom-left' : '',
            label === 'Actions' ? 'ml-3' : ''
          )}
          style={
            toRotate
              ? {
                  ...styles.rotate270,
                  ...styles.originBottomLeft,
                  ...styles.absoluteBottomLeft,
                  whiteSpace: 'nowrap',
                  position: 'absolute' as const
                }
              : { whiteSpace: 'nowrap' }
          }
        >
          {label}
        </span>
      </div>
    </th>
  );
};

export default renderItem;
