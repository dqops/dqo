import React, { useState } from 'react';
import { CheckModel } from '../../api';
import CommentsView from '../Connection/CommentsView';
import IconButton from '../IconButton';
import SvgIcon from '../SvgIcon';
import Tabs from '../Tabs';
import { ITab } from './CheckListItem';
import CheckSettingsTab from './CheckSettingsTab';
import ScheduleTab from './ScheduleTab';
import SensorParametersSettings from './SensorParametersSettings';

interface ICheckSettingsProps {
  check?: CheckModel;
  activeTab: string;
  setActiveTab: (value: string) => void;
  tabs: ITab[];
  onClose: () => void;
  onChange: (check: CheckModel) => void;
  isDefaultEditing?: boolean;
}

const CheckSettings = ({
  check,
  activeTab,
  setActiveTab,
  tabs,
  onClose,
  onChange,
  isDefaultEditing
}: ICheckSettingsProps) => {
  const [text, setText] = useState('');

  const handleChange = (obj: any) => {
    onChange({
      ...check,
      ...obj
    });
  };

  return (
    <div className="my-4">
      <div className="bg-white py-4 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-1 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>
        <div className="border-b border-gray-100">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
        <div className="pt-5 px-4">
          {activeTab === 'check-settings' && (
            <CheckSettingsTab
              check={check}
              onChange={onChange}
              isDefaultEditing={isDefaultEditing}
            />
          )}
          {activeTab === 'schedule' && (
            <ScheduleTab
              schedule={check?.schedule_override}
              onChange={(value) => handleChange({ schedule_override: value })}
            />
          )}
          {activeTab === 'comments' && (
            <div className="overflow-auto">
              <CommentsView
                text={text}
                setText={setText}
                comments={check?.comments || []}
                onChange={(comments) => handleChange({ comments })}
                className="!mb-3 !mt-0"
              />
            </div>
          )}
          {activeTab === 'parameters' && <SensorParametersSettings />}
        </div>
      </div>
    </div>
  );
};

export default CheckSettings;
