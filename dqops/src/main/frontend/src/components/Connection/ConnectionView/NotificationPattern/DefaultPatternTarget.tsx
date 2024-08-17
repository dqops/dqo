import React from 'react';
import {
  FilteredNotificationModel,
  NotificationFilterSpec
} from '../../../../api';
import Checkbox from '../../../Checkbox';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Input from '../../../Input';
import Select from '../../../Select';
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
      <div className="flex items-center text-black text-sm">
        <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
          <span>Pattern name</span>
          {create === true ? (
            <Input
              value={pattern.name}
              onChange={(e) => onChange({ name: e.target.value })}
              className={
                !pattern.name || pattern.name.length === 0
                  ? 'border border-red-500'
                  : ''
              }
            />
          ) : (
            <span className="font-bold">{pattern.name}</span>
          )}
        </div>
        <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
          <span> Priority</span>
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
      <div className="flex items-center pb-6 text-black text-sm">
        <div className="ml-2 flex items-center gap-x-4 py-2 w-1/2">
          <span>Description</span>
          <div className="min-w-60">
            <TextArea
              value={pattern?.description}
              onChange={(e) => onChange({ description: e.target.value })}
            />
          </div>
        </div>
        <div className="flex items-center gap-x-2 py-2 mr-8 min-w-10">
          <span>Disabled</span>
          <Checkbox
            checked={pattern?.disabled}
            onChange={(value) =>
              onChange({
                disabled: value
              })
            }
          />
        </div>
        <div className=" ml-2 flex items-center gap-x-2 py-2 mr-8 ">
          <span className="!min-w-56">
            Process additional notification filters
          </span>
          <Checkbox
            checked={pattern?.process_additional_filters}
            onChange={(value) =>
              onChange({
                process_additional_filters: value
              })
            }
          />
        </div>
        <div className="] ml-2 flex items-center gap-x-2 py-2">
          <span className="!min-w-38">Do not create incidents</span>
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
            <span className="w-25">Quality dimension</span>
            <div className="w-full">
              {/* FIX THAT (HSOULD BE STAGE PROPERTY) */}
              <Input
                value={pattern?.filter?.qualityDimension}
                onChange={(e) =>
                  onChangePatternFilter({ qualityDimension: e.target.value })
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

          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Check category </span>
            <div className="w-full">
              <Input
                value={pattern?.filter?.checkCategory}
                onChange={(e) =>
                  onChangePatternFilter({ checkCategory: e.target.value })
                }
              />
            </div>
          </div>
        </div>

        <>
          <div className="flex justify-between  text-black  ">
            <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Check name</span>
              <div className="w-full">
                <Input
                  value={pattern?.filter?.checkName}
                  onChange={(e) =>
                    onChangePatternFilter({
                      checkName: e.target.value
                    })
                  }
                />
              </div>
            </div>
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Check type</span>
              <div className="w-full">
                <Input
                  value={pattern?.filter?.checkType}
                  onChange={(e) =>
                    onChangePatternFilter({
                      checkType: e.target.value
                    })
                  }
                />
              </div>
            </div>
          </div>
          <div className="flex justify-between  text-black  ">
            <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Highest severity</span>
              <div className="w-full">
                <Select
                  options={[
                    { label: '', value: undefined },
                    { label: 'Warning', value: 1 },
                    { label: 'Error', value: 2 },
                    { label: 'Fatal', value: 3 }
                  ]}
                  value={pattern?.filter?.highestSeverity}
                  onChange={(value) =>
                    onChangePatternFilter({
                      highestSeverity: value
                    })
                  }
                  className="w-49"
                />
              </div>
            </div>
          </div>
        </>
      </SectionWrapper>
    </div>
  );
}
