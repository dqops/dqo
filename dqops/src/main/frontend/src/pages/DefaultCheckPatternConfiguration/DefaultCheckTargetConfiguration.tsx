import React, { useState } from 'react';
import Input from '../../components/Input';
import {
  DefaultColumnChecksPatternListModel,
  DefaultTableChecksPatternListModel,
  TargetColumnPatternSpec,
  TargetTablePatternSpec
} from '../../api';
import SectionWrapper from '../../components/Dashboard/SectionWrapper';
import SvgIcon from '../../components/SvgIcon';
import { sortBy } from 'lodash';

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

  const [additionalParams, setAdditionalParams] = useState(false);

  return (
    <div>
      <div className="flex justify-between pb-6 text-black  ">
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
              onChangeTarget({ priority: Number(e.target.value) })
            }
          />
        </div>
      </div>
      {additionalParams === false ? (
        <div
          className="flex items-center text-black mb-4 cursor-default"
          onClick={() => setAdditionalParams(true)}
        >
          <SvgIcon name="chevron-right" className="w-5 h-5" />
          <span className="font-bold">Target parameters spec</span>
        </div>
      ) : (
        <SectionWrapper
          title="Target parameters spec"
          onClick={() => setAdditionalParams(false)}
          svgIcon={true}
          className="cursor-default"
        >
          <div className="flex justify-between  text-black  ">
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25">Connection</span>
              <Input
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)?.connection
                }
                onChange={(e) => onChangeTarget({ connection: e.target.value })}
              />
            </div>
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Schema</span>
              <Input
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)?.schema
                }
                onChange={(e) => onChangeTarget({ schema: e.target.value })}
              />
            </div>
          </div>
          <div className="flex justify-between  text-black  ">
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Table</span>
              <Input
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.table}
                onChange={(e) => onChangeTarget({ table: e.target.value })}
              />
            </div>
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25">Stage</span>
              <Input
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.stage}
                onChange={(e) => onChangeTarget({ stage: e.target.value })}
              />
            </div>
          </div>
          <div className="flex justify-between  text-black  ">
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Table priority</span>
              <Input
                value={
                  (target?.[targetSpecKey as keyof TTarget] as any)
                    ?.table_priority
                }
                onChange={(e) =>
                  onChangeTarget({
                    table_priority: Number(e.target.value)
                  })
                }
              />
            </div>
            <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
              <span className="w-25"> Label</span>
              <Input
                value={(target?.[targetSpecKey as keyof TTarget] as any)?.label}
                onChange={(e) => onChangeTarget({ label: e.target.value })}
              />
            </div>
          </div>

          {type === 'column' && (
            <>
              <div className="flex justify-between  text-black  ">
                <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
                  <span className="w-25"> Column</span>
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
                <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
                  <span className="w-25"> Data type</span>
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
              <div className="flex justify-between  text-black  ">
                <div className="w-[45%] ml-2 flex items-center gap-x-4 py-2">
                  <span className="w-25"> Data type category</span>
                  <Input
                    value={
                      (target?.[targetSpecKey as keyof TTarget] as any)?.[
                        'data_type_category' as keyof TTargetSpec
                      ]
                    }
                    onChange={(e) =>
                      onChangeTarget({
                        ['data_type_category' as keyof TTargetSpec]:
                          e.target.value
                      })
                    }
                  />
                </div>
              </div>
            </>
          )}
        </SectionWrapper>
      )}
    </div>
  );
}
