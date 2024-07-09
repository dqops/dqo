import React from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setNewNotification } from '../../../redux/actions/job.actions';
import { IRootState } from '../../../redux/reducers';
import SvgIcon from '../../SvgIcon';

export default function NotificationMenuHeader() {
  const { newNotification } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const dispatch = useActionDispatch();
  const toggleMenu = () => {
    dispatch(setNewNotification(false));
  };
  return (
    <div className="relative">
      <SvgIcon
        name="bell"
        className="w-5 h-5 text-gray-700"
        onClick={toggleMenu}
      />
      <span
        className={
          newNotification
            ? 'absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-teal-500 text-white px-1 py-0.5 text-xxs'
            : ''
        }
      >
        {newNotification ? 'New' : ''}
      </span>
    </div>
  );
}
