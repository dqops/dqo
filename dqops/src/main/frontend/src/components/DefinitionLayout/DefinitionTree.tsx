import React from 'react';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  addFirstLevelTab,
  getSensorFolderTree,
  toggleSensorFolderTree
} from '../../redux/actions/definition.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { IRootState } from '../../redux/reducers';
import {
  CheckSpecFolderBasicModel,
  RuleBasicFolderModel,
  RuleBasicModel,
  SensorBasicFolderModel,
  SensorBasicModel,
  CheckSpecBasicModel
} from '../../api';
import SvgIcon from '../SvgIcon';
import {
  getRuleFolderTree,
  toggleRuleFolderTree,
  toggleFirstLevelFolder
} from '../../redux/actions/definition.actions';
import clsx from 'clsx';
import { ROUTES } from '../../shared/routes';
import SensorContextMenu from './SensorContextMenu';
import RuleContextMenu from './RuleContextMenu';
import {
  getdataQualityChecksFolderTree,
  toggledataQualityChecksFolderTree
} from '../../redux/actions/definition.actions';
import DataQualityContextMenu from './DataQualityContextMenu';

const defaultChecks = [
  'Profiling checks',
  'Recurring daily',
  'Recurring monthly'
];

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const { sensorFolderTree, sensorState, definitionFirstLevelFolder } =
    useSelector((state: IRootState) => state.definition);
  const { ruleFolderTree, ruleState } = useSelector(
    (state: IRootState) => state.definition || {}
  );

  const { checksFolderTree, dataQualityChecksState } = useSelector(
    (state: IRootState) => state.definition || {}
  );
  const [selected, setSelected] = useState('');

  // const [rootTree, setRootTree] = useState<Array<TreeRootInterface>>();

  useEffect(() => {
    dispatch(getSensorFolderTree());
    dispatch(getRuleFolderTree());
    dispatch(getdataQualityChecksFolderTree());
    // setRootTree(treeRootElements.map((x) => ({ category: x, isOpen: false })));
  }, []);

  const toggleSensorFolder = (key: string) => {
    dispatch(toggleSensorFolderTree(key));
  };

  const toggleRuleFolder = (key: string) => {
    dispatch(toggleRuleFolderTree(key));
  };

  const toggleDataQualityChecksFolder = (key: string) => {
    dispatch(toggledataQualityChecksFolderTree(key));
  };

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

  useEffect(() => {
    if (
      definitionFirstLevelFolder === undefined ||
      definitionFirstLevelFolder.length === 0
    ) {
      dispatch(
        toggleFirstLevelFolder([
          { category: 'Sensors', isOpen: false },
          { category: 'Rules', isOpen: false },
          { category: 'Data quality checks', isOpen: false },
          { category: 'Default checks configuration', isOpen: false }
        ])
      );
    }
  }, []);

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
                    name={
                      dataQualityChecksState[key] ? 'folder' : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  {folder?.folders?.[key].folders === undefined && (
                    <DataQualityContextMenu
                      folder={folder?.folders?.[key] || {}}
                      path={[...(path || []), key]}
                    />
                  )}
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
          {folder.checks &&
            folder?.checks.map((check) => (
              <div key={check.check_name}>
                <div
                  className={clsx(
                    'cursor-pointer flex space-x-1.5 items-center mb-1 h-5  hover:bg-gray-300',
                    check.custom ? 'font-bold' : '',
                    selected == check.check_name ? 'bg-gray-300' : ''
                  )}
                  onClick={() => {
                    openCheckFirstLevelTab(check);
                  }}
                >
                  <SvgIcon
                    name="definitionssensors"
                    className="w-4 h-4 min-w-4 shrink-0"
                  />
                  <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
                    {check.check_name}
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>
    );
  };

  return (
    <div className="fixed left-0 top-16 bottom-0 overflow-y-auto w-80 shadow border-r border-gray-300 p-4 pt-6 bg-white">
      {definitionFirstLevelFolder?.map((x, index) => (
        <div
          key={index}
          className="mt-2 mb-2 text-sm font-regular cursor-pointer"
        >
          <div
            className="flex items-center mb-2"
            onClick={() => {
              const updatedRootTree = [...definitionFirstLevelFolder];
              updatedRootTree[index].isOpen = !updatedRootTree[index].isOpen;
              dispatch(toggleFirstLevelFolder(updatedRootTree));
            }}
          >
            <SvgIcon
              name={x.isOpen ? 'folder' : 'closed-folder'}
              className="w-4 h-4 min-w-4"
            />
            {x.category}
          </div>
          {x.category === 'Sensors' && x.isOpen === true && (
            <div className="ml-2">
              {renderSensorFolderTree(sensorFolderTree, [])}
            </div>
          )}
          {x.category === 'Rules' && x.isOpen === true && (
            <div className="ml-2">
              {renderRuleFolderTree(ruleFolderTree, [])}
            </div>
          )}
          {x.category === 'Data quality checks' && x.isOpen === true && (
            <div className="ml-2">
              {renderChecksFolderTree(checksFolderTree, [])}
            </div>
          )}
          {x.category === 'Default checks configuration' &&
            x.isOpen === true && (
              <div>
                {defaultChecks.map((x, index) => (
                  <div key={index}>
                    <div
                      className={clsx(
                        'cursor-pointer flex space-x-1.5 items-center mb-1 h-5 ml-2  hover:bg-gray-300'
                        // check.custom ? 'font-bold' : '',
                        // selected == check.check_name ? 'bg-gray-300' : ''
                      )}
                      // onClick={() => {
                      //   openCheckFirstLevelTab(check);
                      // }}
                    >
                      <SvgIcon
                        name="definitionssensors"
                        className="w-4 h-4 min-w-4 shrink-0"
                      />
                      <div className="text-[13px] leading-1.5 whitespace-nowrap flex items-center justify-between">
                        {x}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
        </div>
      ))}
    </div>
  );
};

export default DefinitionTree;
