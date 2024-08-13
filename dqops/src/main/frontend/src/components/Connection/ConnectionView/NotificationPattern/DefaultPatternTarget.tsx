import React from 'react';
import {
  FilteredNotificationModel,
  NotificationFilterSpec
} from '../../../../api';
import Checkbox from '../../../Checkbox';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Input from '../../../Input';
import TextArea from '../../../TextArea';

type TDefaultCheckTargetConfigurationProps = {
  pattern: FilteredNotificationModel;
  create?: boolean;
  onChange: (val: Partial<FilteredNotificationModel>) => void;
  onChangePatternFilter: (val: Partial<NotificationFilterSpec>) => void;
};

export default function DefaultPatternTarget({
  pattern,
  create,
  onChange,
  onChangePatternFilter
}: TDefaultCheckTargetConfigurationProps) {
  return (
    <div>
      <div className="flex justify-between text-black text-sm">
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-30">Pattern name</span>
          {create === true ? (
            <Input
              value={pattern.name}
              onChange={(e) => onChange({ name: e.target.value })}
            />
          ) : (
            <span className="font-bold">{pattern.name}</span>
          )}
        </div>
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25"> Priority</span>
          <Input
            value={pattern.priority}
            onChange={(e) =>
              onChange({
                priority:
                  !isNaN(Number(e.target.value)) && e.target.value !== ''
                    ? Number(e.target.value)
                    : undefined
              })
            }
          />
        </div>
      </div>
      <div className="flex justify-between pb-6 text-black text-sm">
        <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
          <span className="w-30">Description</span>
          <div className="w-full">
            <TextArea
              value={pattern?.description}
              onChange={(e) => onChange({ description: e.target.value })}
            />
          </div>
        </div>
        <div className="w-[20%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25">Disabled</span>
          <Checkbox
            checked={pattern?.disabled}
            onChange={(value) =>
              onChange({
                disabled: value
              })
            }
          />
        </div>
        <div className="w-[20%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25">Process additional notification filters</span>
          <Checkbox
            checked={pattern?.process_additional_filters}
            onChange={(value) =>
              onChange({
                process_additional_filters: value
              })
            }
          />
        </div>
        <div className="w-[20%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25">Do not create incidents</span>
          <Checkbox
            checked={pattern?.do_not_create_incidents}
            onChange={(value) =>
              onChange({
                do_not_create_incidents: value
              })
            }
          />
        </div>
      </div>
      <SectionWrapper title="Target filters" className="cursor-default">
        <div className="flex justify-between  text-black  ">
          <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2 ">
            <span className="w-25 ">Connection</span>
            <div className="w-full">
              <Input
                value={pattern?.filter?.connection}
                className="w-full"
                onChange={(e) =>
                  onChangePatternFilter({ connection: e.target.value })
                }
              />
            </div>
          </div>
          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Table priority</span>
            <div className="w-full">
              <Input
                value={pattern.filter?.tablePriority}
                onChange={(e) =>
                  onChangePatternFilter({
                    tablePriority:
                      !isNaN(Number(e.target.value)) && e.target.value !== ''
                        ? Number(e.target.value)
                        : undefined
                  })
                }
              />
            </div>
          </div>
        </div>
        <div className="flex justify-between  text-black  ">
          <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Table</span>
            <div className="w-full">
              <Input
                value={pattern?.filter?.table}
                onChange={(e) =>
                  onChangePatternFilter({ table: e.target.value })
                }
                className="w-11/12"
              />
            </div>
          </div>
          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25">Stage</span>
            <div className="w-full">
              {/* FIX THAT (HSOULD BE STAGE PROPERTY) */}
              <Input
                value={pattern?.filter?.checkType}
                onChange={(e) =>
                  onChangePatternFilter({ checkType: e.target.value })
                }
              />
            </div>
          </div>
        </div>
        <div className="flex justify-between  text-black  ">
          <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Schema</span>
            <div className="w-full">
              <Input
                value={pattern?.filter?.schema}
                className="w-full"
                onChange={(e) =>
                  onChangePatternFilter({ schema: e.target.value })
                }
              />
            </div>
          </div>

          {/* <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Label</span>
            <div className="w-full">
              <Input
                value={pattern?.filter.}
                onChange={(e) => onChangeTarget({ label: e.target.value })}
              />
            </div>
          </div> */}
        </div>

        <>
          {/* <div className="flex justify-between  text-black  ">
            <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Column</span>
              <div className="w-full">
                <Input
                  value={pattern?.filter?.['column' as keyof TTargetSpec]}
                  onChange={(e) =>
                    onChangeTarget({
                      ['column' as keyof TTargetSpec]: e.target.value
                    })
                  }
                />
              </div>
            </div>
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Data type</span>
              <div className="w-full">
                <Input
                  value={pattern?.filter?.['data_type' as keyof TTargetSpec]}
                  onChange={(e) =>
                    onChangeTarget({
                      ['data_type' as keyof TTargetSpec]: e.target.value
                    })
                  }
                />
              </div>
            </div>
          </div> */}
          {/* <div className="flex justify-between  text-black  ">
              <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
                <span className="w-25"> Data type category</span>
                <div className="w-full">
                  <Select
                    options={[
                      { label: '', value: '' },
                      ...Object.keys(
                        TargetColumnPatternSpecDataTypeCategoryEnum
                      ).map((x) => ({
                        label: x,
                        value: x
                      }))
                    ]}
                    value={
                      pattern?.filter?
                    }
                    onChange={(value) =>
                      onChangeTarget({
                        ['data_type_category' as keyof TTargetSpec]: value
                      })
                    }
                    className="w-49"
                  />
                </div>
              </div> 
            </div> */}
        </>
      </SectionWrapper>
    </div>
  );
}
