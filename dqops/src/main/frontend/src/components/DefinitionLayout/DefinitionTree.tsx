import React, { useMemo } from 'react';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  addFirstLevelTab,
  getSensorFolderTree,
  toggleSensorFolderTree
} from '../../redux/actions/sensor.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { IRootState } from '../../redux/reducers';
import {
  CheckContainerModel,
  CheckSpecFolderBasicModel,
  CheckSpecModel,
  QualityCategoryModel,
  RuleBasicFolderModel,
  RuleBasicModel,
  SensorBasicFolderModel,
  SensorBasicModel,
  CheckSpecBasicModel
} from '../../api';
import SvgIcon from '../SvgIcon';
import {
  getRuleFolderTree,
  toggleRuleFolderTree
} from '../../redux/actions/rule.actions';
import clsx from 'clsx';
import { ROUTES } from '../../shared/routes';
import SensorContextMenu from './SensorContextMenu';
import RuleContextMenu from './RuleContextMenu';
import { ChecksApi, RulesApi, SensorsApi, SettingsApi } from '../../services/apiClient';
import { getdataQualityChecksFolderTree, toggledataQualityChecksFolderTree } from '../../redux/actions/dataQualityChecks';
import DataQualityContextMenu from './DataQualityContextMenu';
import Select, { Option } from '../Select';
import CreateCheckDialog from './CreateChecksDialog';
import CheckContextMenu from './CheckContextMenu';
import Button from '../Button';

export interface RuleSensorCheckName{
  checkName: string,
  rule: string, 
  sensor: string
  toUpdateVar: boolean
}

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const { sensorFolderTree, sensorState } = useSelector(
    (state: IRootState) => state.sensor
  );
  const { ruleFolderTree, ruleState } = useSelector(
    (state: IRootState) => state.rule || {}
  );

const {checksFolderTree, dataQualityChecksState} = useSelector(
  (state: IRootState) => state.dataQualityChecks || {}
  )
  const [selected, setSelected] = useState('');

  const [allSensors, setAllSensors] = useState<SensorBasicModel[]>()
  const [allRules, setAllRules] = useState<RuleBasicModel[]>()
  const [isOpen, setIsOpen] = useState(false)
  const [selectedCheck, setSelectedCheck] = useState<RuleSensorCheckName>({checkName: "", sensor: "", rule: "", toUpdateVar: false})
  const [toUpdate, setToUpdate] = useState(false)

  const onChangeSelected = (obj: Partial<RuleSensorCheckName>) : void => {
    setSelectedCheck({
      ...selectedCheck, ...obj
    })
  }


  useEffect(() => {
    dispatch(getSensorFolderTree());
    dispatch(getRuleFolderTree());
    dispatch(getdataQualityChecksFolderTree())
    getAllSensors()
    getAllRules()
  }, []);

  const memoizedData = useMemo(() => {
    return {
      sensors: allSensors,
      rules: allRules,
    };
  }, [allSensors, allRules]);

const onChangeSensor = (value: string)=> {
  setSelectedCheck({...selectedCheck, sensor: value})
}

const onChangeRule = (value: string)=> {
  setSelectedCheck({...selectedCheck, rule: value})
}

const onChangeNameOfCheck = (value: string)=> {
  setSelectedCheck({...selectedCheck, checkName: value})
}
  const getAllSensors =async () => {
    await SensorsApi.getAllSensors().then((res) => setAllSensors(res.data))
  }

  const getAllRules =async () => {
    await RulesApi.getAllRules().then((res) => setAllRules(res.data))
  }

  const createCheck = async (fullCheckName: string, body ?: CheckSpecModel) => {
    await ChecksApi.createCheck(fullCheckName, body)
  }

  const updateCheck = async () => {
    await ChecksApi.updateCheck("custom/" + selectedCheck.checkName,
    {check_name: selectedCheck.checkName, rule_name: selectedCheck.rule, sensor_name: selectedCheck.sensor})
  }

  const deleteCheck =async (checkName: string) => {
    await ChecksApi.deleteCheck(checkName)
  }

  const toggleSensorFolder = (key: string) => {
    dispatch(toggleSensorFolderTree(key));
  };

  const toggleRuleFolder = (key: string) => {
    dispatch(toggleRuleFolderTree(key));
  };

  const toggleDataQualityChecksFolder = (key: string) => {
    dispatch(toggledataQualityChecksFolderTree(key))
  }

  const openSensorFirstLevelTab = (sensor: SensorBasicModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL(sensor.sensor_name ?? ''),
        value: ROUTES.SENSOR_DETAIL_VALUE(sensor.sensor_name ?? ''),
        state: {
          full_sensor_name: sensor.full_sensor_name
        },
        label: sensor.sensor_name
      })
    );
  };

  const openRuleFirstLevelTab = (rule: RuleBasicModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(rule.rule_name ?? ''),
        value: ROUTES.RULE_DETAIL_VALUE(rule.rule_name ?? ''),
        state: {
          full_rule_name: rule.full_rule_name
        },
        label: rule.rule_name
      })
    );
  };

  const openCheckFirstLevelTab = (check: CheckSpecBasicModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(check.check_name ?? ''),
        value: ROUTES.CHECK_DETAIL_VALUE(check.check_name ?? ''),
        state: {
          fullCheckName: check.full_check_name,
          custom: check.custom
        },
        label: check.check_name
      })
    );
  };

  console.log(selectedCheck)

  const renderSensorFolderTree = (
    folder?: SensorBasicFolderModel,
    path?: string[]
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() => toggleSensorFolder(key)}
                >
                  <SvgIcon
                    name={sensorState[key] ? 'folder' : 'closed-folder'}
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <SensorContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {sensorState[key] && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderSensorFolderTree(folder?.folders[key], [
                        ...(path || []),
                        key
                      ])}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2">
          {folder.sensors?.map((sensor) => (
            <div
              key={sensor.full_sensor_name}
              className={clsx(
                'cursor-pointer flex space-x-1.5 items-center mb-1 h-5  hover:bg-gray-300',
                sensor.custom ? 'font-bold' : '',
                selected == sensor.sensor_name ? 'bg-gray-300' : ''
              )}
              onClick={() => {
                openSensorFirstLevelTab(sensor),
                  setSelected(sensor.sensor_name ? sensor.sensor_name : '');
              }}
            >
              <SvgIcon
                name="definitionssensors"
                className="w-4 h-4 min-w-4 shrink-0"
              />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">
                {sensor.sensor_name}
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const renderRuleFolderTree = (
    folder?: RuleBasicFolderModel,
    path?: string[]
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() => toggleRuleFolder(key)}
                >
                  <SvgIcon
                    name={ruleState[key] ? 'folder' : 'closed-folder'}
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <RuleContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {ruleState[key] && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderRuleFolderTree(folder?.folders[key], [
                        ...(path || []),
                        key
                      ])}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2 ">
          {folder.rules?.map((rule) => (
            <div
              key={rule.full_rule_name}
              className={clsx(
                'cursor-pointer flex space-x-1.5 items-center mb-1 h-5 hover:bg-gray-300',
                rule.custom ? 'font-bold ' : '',
                selected == rule.rule_name ? 'bg-gray-300' : ''
              )}
              onClick={() => {
                openRuleFirstLevelTab(rule),
                  setSelected(rule.rule_name ? rule.rule_name : '');
              }}
            >
              <SvgIcon
                name="definitionsrules"
                className="w-4 h-4 min-w-4 shrink-0"
              />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">
                {rule.rule_name}
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const renderChecksFolderTree = (
    folder?: CheckSpecFolderBasicModel,
    path?: string[]
  ) => {
    if (!folder) return null;

    return (
      <div className="text-sm">
        {folder.folders &&
          Object.keys(folder.folders).map((key, index) => {
            return (
              <div key={index}>
                <div
                  className="flex space-x-1.5 items-center mb-1 h-5 cursor-pointer hover:bg-gray-300"
                  onClick={() => toggleDataQualityChecksFolder(key)}
                >
                  <SvgIcon
                    name={dataQualityChecksState[key] ? 'folder' : 'closed-folder'}
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <DataQualityContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {dataQualityChecksState[key] && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderChecksFolderTree(folder?.folders[key], [
                        ...(path || []),
                        key
                      ])}
                  </div>
                )}
              </div>
            );
          })}
        <div className="ml-2">
        {folder.checks && folder?.checks.map((check) => (
          <div key={check.check_name}>
            <div  
              className={clsx(
                'cursor-pointer flex space-x-1.5 items-center mb-1 h-5  hover:bg-gray-300',
                check.custom ? 'font-bold' : '',
                selected == check.check_name ? 'bg-gray-300' : ''
              )}
              onClick={() => {
                openCheckFirstLevelTab(check)
              }}
            >
              <SvgIcon
                name="definitionssensors"
                className="w-4 h-4 min-w-4 shrink-0"
              />
              <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
                {check.check_name}
                {/* {check.custom === true && 
                <CheckContextMenu
                 onChangeSelected={onChangeSelected} 
                 checkName={check.check_name ?? ""} 
                 deleteCheck={deleteCheck}
                 />} */}
              </div>
            </div>
            {/* {selectedCheck.checkName === check.check_name && 
            <div >
            <Select placeholder='Sensor' options={memoizedData.rules && memoizedData.rules.map((x) => ({
              label: x.rule_name ?? "", 
              value: x.rule_name ?? ""
            })) || []}
            value={selectedCheck.sensor}
            onChange={(selectedOption) => { 
              onChangeNameOfCheck(check.check_name || "");
              onChangeSensor(String(selectedOption));
            }}
            className='mt-2 mb-2'
            />
            <Select placeholder='Rule'  
            options={memoizedData.sensors && memoizedData.sensors.map((x) => ({
            label: x.sensor_name ?? "", 
            value: x.sensor_name ?? ""
            })) || []}
            onChange={(selectedOption) => {
              onChangeNameOfCheck(check.check_name || "");
              onChangeRule(String(selectedOption));
            }}
            value={selectedCheck.rule}
            />
            {
            (selectedCheck.rule.length !==0 || selectedCheck.sensor.length !==0) &&
            <Button className='mt-2 mb-2' color='primary' label='Update check' onClick={updateCheck}></Button>}
            </div>
            } */}
            </div>
          ))}
        </div>
      </div>
    );
  };

  

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      <div className="mb-4">
        <div className="text-sm text-gray-700 font-semibold mb-2">Sensors:</div>
        {renderSensorFolderTree(sensorFolderTree, [])}
      </div>

      <div className='mb-4'>
        <div className="text-sm text-gray-700 font-semibold mb-2">Rules:</div>
        {renderRuleFolderTree(ruleFolderTree, [])}
      </div>

      <div>
        <div className="text-sm text-gray-700 font-semibold mb-2">Data Quality Checks: </div>
        {renderChecksFolderTree(checksFolderTree, [])}
      </div>
      <CreateCheckDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        onConfirm={createCheck}
      />
    </div>
  );
};

export default DefinitionTree;
