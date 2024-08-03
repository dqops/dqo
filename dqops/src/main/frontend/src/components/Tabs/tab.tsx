import { Tooltip } from '@material-tailwind/react';
import React from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setJobAllert } from '../../redux/actions/job.actions';
import { IRootState } from '../../redux/reducers';
import SvgIcon from '../SvgIcon';

export interface TabOption {
  label: string;
  value: string;
  isUpdated?: boolean;
  isDisabled?: boolean;
}

export interface TabProps {
  tab: TabOption;
  active?: boolean;
  onChange?: (tab: TabOption) => void;
  closable?: boolean;
  onClose?: () => void;
}

const Tab = ({ tab, active, onChange, closable, onClose }: TabProps) => {
  const { job_allert } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();
  const handleClose = (e: any) => {
    e.stopPropagation();
    if (onClose) {
      onClose();
    }
  };
  const handleClick = () => {
    if (onChange) {
      onChange(tab);
    }
    if (!active) {
      dispatch(setJobAllert({}));
    }
  };
  return (
    <Tooltip
      content={job_allert.tooltipMessage}
      className={
        job_allert.tooltipMessage && job_allert.action === tab.value
          ? 'max-w-60 py-2 px-2 bg-gray-800'
          : 'hidden'
      }
    >
      <div
        className={`relative text-sm leading-20 py-2 cursor-pointer px-8 rounded-t-md ${
          active ? 'font-semibold relative border-b-2 border-primary' : ''
        } ${tab.isDisabled ? 'border-gray-150 bg-gray-100' : 'text-gray-800'}
        ${
          job_allert.tooltipMessage && job_allert.action === tab.value
            ? 'flash-red-border'
            : ''
        }
     `}
        style={active ? { backgroundColor: '#E4F7F4' } : {}}
        onClick={() => !tab.isDisabled && handleClick()}
      >
        <div className="text-center truncate">
          {tab.label}
          {tab.isUpdated ? '*' : ''}
        </div>
        {closable && active && (
          <SvgIcon
            name="close"
            onClick={handleClose}
            className="absolute right-1.5 top-1/2 -translate-y-1/2 transform"
          />
        )}
      </div>
    </Tooltip>
  );
};

export default Tab;
