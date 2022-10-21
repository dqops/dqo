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
import { DimensionMappingSpec, UICheckModel } from '../../api';

interface ICheckSettingsProps {
  check?: UICheckModel;
  activeTab: string;
  setActiveTab: (value: string) => void;
  tabs: ITab[];
  onClose: () => void;
  onChange: (check: UICheckModel) => void;
}

const CheckSettings = ({
  check,
  activeTab,
  setActiveTab,
  tabs,
  onClose,
  onChange
}: ICheckSettingsProps) => {
  const tab = useMemo(
    () => tabs.find((item) => item.value === activeTab),
    [tabs, activeTab]
  );

  const getDimension = (index: number) => {
    if (index === 0) return check?.dimensions_override?.dimension_1;
    if (index === 1) return check?.dimensions_override?.dimension_2;
    if (index === 2) return check?.dimensions_override?.dimension_3;
    if (index === 3) return check?.dimensions_override?.dimension_4;
    if (index === 4) return check?.dimensions_override?.dimension_5;
    if (index === 5) return check?.dimensions_override?.dimension_6;
    if (index === 6) return check?.dimensions_override?.dimension_7;
    if (index === 7) return check?.dimensions_override?.dimension_8;
    if (index === 8) return check?.dimensions_override?.dimension_9;
  };

  const onChangeDimensions = (
    dimension: DimensionMappingSpec,
    index: number
  ) => {
    onChange({
      ...check,
      dimensions_override: {
        ...check?.dimensions_override,
        [`dimension_${index + 1}`]: dimension
      }
    });
  };

  const handleChange = (obj: any) => {
    onChange({
      ...check,
      ...obj
    });
  };

  return (
    <div className="my-4">
      <div className="bg-white px-4 py-6 border border-gray-200 relative">
        <IconButton
          className="absolute right-4 top-4 bg-gray-50 hover:bg-gray-100 text-gray-700"
          onClick={onClose}
        >
          <SvgIcon name="close" />
        </IconButton>
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        <div className="pt-5">
          {activeTab === 'dimension' && (
            <div>
              {Array(9)
                .fill(0)
                .map((item, index) => (
                  <DimensionItem
                    idx={index}
                    key={index}
                    dimension={getDimension(index)}
                    onChange={(dimension) =>
                      onChangeDimensions(dimension, index)
                    }
                  />
                ))}
            </div>
          )}
          {activeTab === 'schedule' && (
            <ScheduleTab
              schedule={check?.schedule_override}
              onChange={(value) => handleChange({ schedule_override: value })}
            />
          )}
          {activeTab === 'time' && (
            <TimeSeriesView
              timeSeries={check?.time_series_override}
              setTimeSeries={(times) =>
                handleChange({ time_series_override: times })
              }
            />
          )}
          {activeTab === 'comments' && (
            <div className="max-w-160 overflow-auto">
              <CommentsView
                comments={check?.comments || []}
                onChange={(comments) => handleChange({ comments })}
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
