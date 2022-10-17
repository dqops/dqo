import React, { useMemo } from 'react';
import DimensionItem from './DimensionItem';
import ScheduleTab from './ScheduleTab';
import TimeSeriesView from '../Connection/TimeSeriesView';
import CommentsView from '../Connection/CommentsView';
import SensorParametersSettings from './SensorParametersSettings';
import Tabs from '../Tabs';
import { ITab } from './CheckListItem';
import SvgIcon from '../SvgIcon';
import IconButton from '../IconButton';
import SpecRuleSettings from './SpecRuleSettings';

interface ICheckSettingsProps {
  checks?: any;
  activeTab: string;
  onChange: (value: string) => void;
  tabs: ITab[];
  onClose: () => void;
}

const CheckSettings = ({
  checks,
  activeTab,
  onChange,
  tabs,
  onClose
}: ICheckSettingsProps) => {
  const tab = useMemo(
    () => tabs.find((item) => item.value === activeTab),
    [tabs, activeTab]
  );

  return (
    <div className="my-4">
      <div className="bg-white px-4 py-6 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChange} />
        <div className="pt-5">
          {activeTab === 'dimension' && (
            <div>
              {Array(10)
                .fill(0)
                .map((item, index) => (
                  <DimensionItem idx={index} key={index} />
                ))}
            </div>
          )}
          {activeTab === 'schedule' && (
            <ScheduleTab
              schedule={checks?.schedule_override}
              onChange={() => {}}
            />
          )}
          {activeTab === 'time' && <TimeSeriesView setTimeSeries={() => {}} />}
          {activeTab === 'comments' && (
            <div className="max-w-160 overflow-auto">
              <CommentsView
                comments={checks?.comments || []}
                onChange={() => {}}
                className="!mb-3 !mt-0"
              />
            </div>
          )}
          {activeTab === 'parameters' && <SensorParametersSettings />}
          {tab?.type === 'rule' && <SpecRuleSettings />}
        </div>
      </div>
    </div>
  );
};

export default CheckSettings;
