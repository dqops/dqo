import {
  IconButton,
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
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
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler style={{ position: 'relative' }}>
        <IconButton
          className="!mr-3 !bg-transparent"
          ripple={false}
          variant="text"
        >
          <NotificationMenuHeader />
        </IconButton>
      </PopoverHandler>
      <PopoverContent
        className="min-w-120 max-w-120 px-0 relative z-[101]"
        style={{ position: 'relative', zIndex: '100000' }}
      >
        <NotificationMenuContent />
      </PopoverContent>
    </Popover>
  );
};

export default memo(NotificationMenu);
