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
      bottom: -4,
      left: 30
    }
  };

  return (
    <th
      className={clsx(
        "px-4 text-xs",
        toRotate ? 'items-start' : 'items-end'
      )}
      key={key}
    >
      <div
        className={clsx(
          'flex text-sm relative',
          toRotate ? 'items-start' : 'items-end'
        )}
      >
        <span
          className={clsx(
            'inline-block text-xs',
            toRotate ? 'rotate-90 origin-bottom-left font-normal' : '',
            label === 'Actions' ? 'ml-6' : ''
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
