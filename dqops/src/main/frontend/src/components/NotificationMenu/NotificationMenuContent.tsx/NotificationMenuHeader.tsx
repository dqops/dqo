import React, { useEffect, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

export default function NotificationMenuHeader() {
  const { jobList } = useSelector((state: IRootState) => state.job || {});

  const [showNewIcon, setShowNewIcon] = useState(false);

  useEffect(() => {
    setShowNewIcon(true);
  }, [jobList]);

  return (
    <div className="relative" onClick={() => setShowNewIcon(false)}>
      <SvgIcon name="bell" className="w-5 h-5 text-gray-700" />
      <span
        className={
          showNewIcon && Object.keys(jobList).length !== 0
            ? 'absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-teal-500 text-white px-1 py-0.5 text-xxs'
            : ''
        }
      >
        {showNewIcon && Object.keys(jobList).length !== 0 ? 'New' : ''}
      </span>
    </div>
  );
}
