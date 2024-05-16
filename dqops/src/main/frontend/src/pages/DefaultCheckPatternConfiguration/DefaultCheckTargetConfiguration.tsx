import React from 'react';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel,
  TargetColumnPatternSpec,
  TargetColumnPatternSpecDataTypeCategoryEnum,
  TargetTablePatternSpec
} from '../../api';
import Checkbox from '../../components/Checkbox';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import Input from '../../components/Input';
import Select from '../../components/Select';

type TTarget =
  | DefaultColumnChecksPatternListModel
  | DefaultTableChecksPatternListModel;

type TDefaultCheckTargetConfigurationProps = {
  type: 'table' | 'column';
  target: TTarget;
  onChangeTarget: (
    updatedTarget: Partial<TTarget> | Partial<TTargetSpec>
  ) => void;
  create?: boolean;
};

type TTargetSpec = TargetColumnPatternSpec | TargetTablePatternSpec;

export default function DefaultCheckTargetConfiguration({
  type,
  target,
  onChangeTarget,
  create
}: TDefaultCheckTargetConfigurationProps) {
  const targetSpecKey = type === 'column' ? 'target_column' : 'target_table';

  return (
    <div>
      <div className="flex justify-between text-black text-sm">
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-30">Pattern name</span>
          {create === true ? (
            <Input
              value={target?.pattern_name}
              onChange={(e) => onChangeTarget({ pattern_name: e.target.value })}
            />
          ) : (
            <span className="font-bold">{target?.pattern_name}</span>
          )}
        </div>
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25"> Priority</span>
          <Input
            value={target?.priority}
            onChange={(e) =>
              onChangeTarget({
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
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-30">Description</span>
          <Input
            value={target?.description}
            onChange={(e) => onChangeTarget({ description: e.target.value })}
          />
        </div>
        <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
          <span className="w-25">Disabled</span>
          <Checkbox
            checked={target?.disabled}
            onChange={(value) =>
              onChangeTarget({
                disabled: value
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
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)?.connection
                }
                className="w-full"
                onChange={(e) => onChangeTarget({ connection: e.target.value })}
              />
            </div>
          </div>
          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Table priority</span>
            <div className="w-full">
              <Input
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)
                    ?.table_priority
                }
                onChange={(e) =>
                  onChangeTarget({
                    table_priority:
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
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.table}
                onChange={(e) => onChangeTarget({ table: e.target.value })}
                className="w-11/12"
              />
            </div>
          </div>
          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25">Stage</span>
            <div className="w-full">
              <Input
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.stage}
                onChange={(e) => onChangeTarget({ stage: e.target.value })}
              />
            </div>
          </div>
        </div>
        <div className="flex justify-between  text-black  ">
          <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Schema</span>
            <div className="w-full">
              <Input
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)?.schema
                }
                className="w-full"
                onChange={(e) => onChangeTarget({ schema: e.target.value })}
              />
            </div>
          </div>

          <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
            <span className="w-25"> Label</span>
            <div className="w-full">
              <Input
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.label}
                onChange={(e) => onChangeTarget({ label: e.target.value })}
              />
            </div>
          </div>
        </div>

        {type === 'column' && (
          <>
            <div className="flex justify-between  text-black  ">
              <div className="w-1/2 ml-2 flex items-center gap-x-4 py-2">
                <span className="w-25"> Column</span>
                <div className="w-full">
                  <Input
                    value={
                      (target?.[targetSpecKey as keyof TTarget] as any)?.[
                        'column' as keyof TTargetSpec
                      ]
                    }
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
                    value={
                      (target?.[targetSpecKey as keyof TTarget] as any)?.[
                        'data_type' as keyof TTargetSpec
                      ]
                    }
                    onChange={(e) =>
                      onChangeTarget({
                        ['data_type' as keyof TTargetSpec]: e.target.value
                      })
                    }
                  />
                </div>
              </div>
            </div>
            <div className="flex justify-between  text-black  ">
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
                      (target?.[targetSpecKey as keyof TTarget] as any)?.[
                        'data_type_category' as keyof TTargetSpec
                      ]
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
            </div>
          </>
        )}
      </SectionWrapper>
    </div>
  );
}
