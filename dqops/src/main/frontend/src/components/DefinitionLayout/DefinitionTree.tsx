import React from 'react';
import { useEffect} from 'react';
import { useSelector } from 'react-redux';
import {
  addFirstLevelTab,
  getSensorFolderTree,
  toggleSensorFolderTree,
  openRuleFolderTree,
  getRuleFolderTree,
  toggleRuleFolderTree,
  toggleFirstLevelFolder,
  openSensorFolderTree,
  getdataQualityChecksFolderTree,
  toggledataQualityChecksFolderTree,
  opendataQualityChecksFolderTree,
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
import clsx from 'clsx';
import { ROUTES } from '../../shared/routes';
import SensorContextMenu from './SensorContextMenu';
import RuleContextMenu from './RuleContextMenu';
import DataQualityContextMenu from './DataQualityContextMenu';

const defaultChecks = [
  'Profiling checks',
  'Monitoring daily',
  'Monitoring monthly'
];

export const DefinitionTree = () => {
  const dispatch = useActionDispatch();
  const {
    sensorFolderTree,
    sensorState,
    definitionFirstLevelFolder,
    checksFolderTree,
    dataQualityChecksState,
    ruleFolderTree,
    ruleState,
    tabs,
    activeTab,
    refreshChecksTreeIndicator,
    refreshRulesTreeIndicator ,
    refreshSensorsTreeIndicator
  } = useSelector((state: IRootState) => state.definition);

  useEffect(() => {
    dispatch(getSensorFolderTree());
  }, [refreshSensorsTreeIndicator]);

  useEffect(() => {
    dispatch(getRuleFolderTree());
  }, [refreshRulesTreeIndicator]);

  useEffect(() => {
    dispatch(getdataQualityChecksFolderTree());
  }, [refreshChecksTreeIndicator]);

  const toggleSensorFolder = (key: string) => {
    dispatch(toggleSensorFolderTree(key));
  };

  const openSensorFolder = (key: string) => {
    dispatch(openSensorFolderTree(key));
  };

  const toggleRuleFolder = (key: string) => {
    dispatch(toggleRuleFolderTree(key));
  };

  const openRuleFolder = (key: string) => {
    dispatch(openRuleFolderTree(key));
  };

  const toggleDataQualityChecksFolder = (fullPath: string) => {
    dispatch(toggledataQualityChecksFolderTree(fullPath));
  };
  const openDataQualityChecksFolder = (fullPath: string) => {
    dispatch(opendataQualityChecksFolderTree(fullPath));
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
          full_check_name: check.full_check_name,
          custom: check.custom
        },
        label: check.check_name
      })
    );
  };

  const openCheckDefaultFirstLevelTab = (defaultCheck: string) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DEFAULT_DETAIL(defaultCheck.replace(/\s/g, '_')),
        value: ROUTES.CHECK_DEFAULT_DETAIL_VALUE(
          defaultCheck.replace(/\s/g, '_')
        ),
        state: {
          type: defaultCheck
        },
        label: defaultCheck
      })
    );
  };

  const openAllUsersFirstLevelTab = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.USERS_LIST_DETAIL(),
        value: ROUTES.USERS_LIST_DETAIL_VALUE(),
        label: "All users"
      })
    );
  };


  const toggleFolderRecursively = (
    elements: string[],
    index = 0,
    type: string
  ) => {
    if (index >= elements.length - 1) {
      return;
    }
    const path = elements.slice(0, index + 1).join('/');
    if (index === 0) {
      if (type === 'checks') {
        openDataQualityChecksFolder('undefined/' + path);
      } else if (type === 'rules') {
        openRuleFolder('undefined/' + path);
      } else {
        openSensorFolder('undefined/' + path);
      }
    } else {
      if (type === 'checks') {
        openDataQualityChecksFolder(path);
      } else if (type === 'rules') {
        openRuleFolder(path);
      } else {
        openSensorFolder(path);
      }
    }
    toggleFolderRecursively(elements, index + 1, type);
  };

  useEffect(() => {
    const configuration = [
      { category: 'Sensors', isOpen: false },
      { category: 'Rules', isOpen: false },
      { category: 'Data quality checks', isOpen: false },
      { category: 'Default checks configuration', isOpen: false }
    ];
    if(tabs && tabs.length !== 0){
      for (let i = 0; i < tabs.length; i++) {
        if (tabs[i].url?.includes('default_checks')) {
          configuration[3].isOpen = true;
      } else if (tabs[i]?.url?.includes('sensors')) {
        configuration[0].isOpen = true;
        const arrayOfElemsToToggle = (
          tabs[i].state.full_sensor_name as string
        )?.split('/');
        if (arrayOfElemsToToggle) {
          toggleFolderRecursively(arrayOfElemsToToggle, 0, 'sensors');
        }
      } else if (tabs[i]?.url?.includes('checks')) {
        configuration[2].isOpen = true;
        const arrayOfElemsToToggle = (
          tabs[i].state.fullCheckName as string
        )?.split('/');
        if (arrayOfElemsToToggle) {
          toggleFolderRecursively(arrayOfElemsToToggle, 0, 'checks');
        }
      } else if (tabs[i]?.url?.includes('rules')) {
        configuration[1].isOpen = true;
        const arrayOfElemsToToggle = (
          tabs[i].state.full_rule_name as string
        )?.split('/');
        if (arrayOfElemsToToggle) {
          toggleFolderRecursively(arrayOfElemsToToggle, 0, 'rules');
        }
      }
      dispatch(toggleFirstLevelFolder(configuration));
    }
  }else{
    dispatch(toggleFirstLevelFolder(configuration));
  }
  }, []);

  const renderSensorFolderTree = (
    folder?: SensorBasicFolderModel,
    path?: string[],
    previousFolder?: string
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
                  onClick={() =>
                    toggleSensorFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      sensorState[previousFolder + '/' + key] === true
                        ? 'folder'
                        : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <SensorContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {sensorState[previousFolder + '/' + key] === true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderSensorFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
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
                activeTab?.split('/').at(activeTab?.split('/').length - 1) ===
                  sensor.sensor_name
                  ? 'bg-gray-300'
                  : ''
              )}
              onClick={() => {
                openSensorFirstLevelTab(sensor);
              }}
            >
              <SvgIcon
                name="definitionssensors"
                className="w-4 h-4 min-w-4 shrink-0"
              />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">
                {sensor.sensor_name}
              </div>
              <SensorContextMenu
                singleSensor={true}
                sensor={sensor}
              />
            </div>
          ))}
        </div>
      </div>
    );
  };

  const renderRuleFolderTree = (
    folder?: RuleBasicFolderModel,
    path?: string[],
    previousFolder?: string
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
                  onClick={() =>
                    toggleRuleFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      ruleState[previousFolder + '/' + key] === true
                        ? 'folder'
                        : 'closed-folder'
                    }
                    className="w-4 h-4 min-w-4"
                  />
                  <div className="text-[13px] leading-1.5 truncate">{key}</div>
                  <RuleContextMenu
                    folder={folder?.folders?.[key] || {}}
                    path={[...(path || []), key]}
                  />
                </div>
                {ruleState[previousFolder + '/' + key] === true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderRuleFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
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
                activeTab?.split('/').at(activeTab?.split('/').length - 1) ===
                  rule.rule_name
                  ? 'bg-gray-300'
                  : ''
              )}
              onClick={() => {
                openRuleFirstLevelTab(rule);
              }}
            >
              <SvgIcon
                name="definitionsrules"
                className="w-4 h-4 min-w-4 shrink-0"
              />
              <div className="text-[13px] leading-1.5 whitespace-nowrap">
                {rule.rule_name}
              </div>
              <RuleContextMenu
                singleRule={true}
                rule={rule}
              />
            </div>
          ))}
        </div>
      </div>
    );
  };

  const renderChecksFolderTree = (
    folder?: CheckSpecFolderBasicModel,
    path?: string[],
    previousFolder?: string
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
                  onClick={() =>
                    toggleDataQualityChecksFolder(
                      previousFolder !== 'undefined'
                        ? previousFolder + '/' + key
                        : key
                    )
                  }
                >
                  <SvgIcon
                    name={
                      dataQualityChecksState[previousFolder + '/' + key] ===
                      true
                        ? 'folder'
                        : 'closed-folder'
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
                {dataQualityChecksState[previousFolder + '/' + key] ===
                  true && (
                  <div className="ml-2">
                    {folder?.folders &&
                      renderChecksFolderTree(
                        folder?.folders[key],
                        [...(path || []), key],
                        previousFolder ? previousFolder + '/' + key : key
                      )}
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
                    activeTab
                      ?.split('/')
                      .at(activeTab?.split('/').length - 1) === check.check_name
                      ? 'bg-gray-300'
                      : ''
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
                  <DataQualityContextMenu
                    singleCheck={true}
                    check={check}
                  />
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
                        'cursor-pointer flex space-x-1.5 items-center mb-1 h-5 ml-2  hover:bg-gray-300',
                        activeTab
                          ?.split('/')
                          .at(activeTab?.split('/').length - 1)
                          ?.replace('_', ' ') === x
                          ? 'bg-gray-300'
                          : ''
                        // check.custom ? 'font-bold' : '',
                        // selected == check.check_name ? 'bg-gray-300' : ''
                      )}
                      onClick={() => {
                        openCheckDefaultFirstLevelTab(x);
                      }}
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
      <div onClick={openAllUsersFirstLevelTab} 
        className='cursor-pointer flex space-x-1 items-center mb-1 h-5  hover:bg-gray-300' >
        <SvgIcon
           name="userprofile"
            className="w-4 h-4 min-w-4 "
        />
        <div className="text-[14.5px] leading-1.5 whitespace-nowrap flex items-center justify-between">
          Manage users
        </div>
      </div>
    </div>
  );
};

export default DefinitionTree;
