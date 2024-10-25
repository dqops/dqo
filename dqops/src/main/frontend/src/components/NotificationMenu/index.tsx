import React, { memo } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleMenu } from '../../redux/actions/job.actions';
import { IRootState } from '../../redux/reducers';
import NotificationMenuContent from './NotificationMenuContent.tsx';
import NotificationMenuHeader from './NotificationMenuContent.tsx/NotificationMenuHeader';

const NotificationMenu = () => {
  const { isOpen } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();

  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };

  return (
    <div style={{ position: 'relative', display: 'inline-block' }}>
      <div
        onClick={toggleOpen}
        className="flex items-center justify-center mt-3 ml-3 mr-4 cursor-pointer "
      >
        <NotificationMenuHeader />
      </div>

      {isOpen && (
        <div
          style={{
            position: 'absolute',
            top: '130%',
            right: 0,
            padding: 0,
            backgroundColor: 'white',
            boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.15)'
          }}
          className="!z-[9999]"
        >
          <NotificationMenuContent />
        </div>
      )}
    </div>
  );
};

export default memo(NotificationMenu);
