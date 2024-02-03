import React, { useState } from 'react';
import { useActionDispatch } from '../hooks/useActionDispatch';
import {
  addFirstLevelTab,
  openRuleFolderTree,
  openSensorFolderTree,
  opendataQualityChecksFolderTree,
  toggleFirstLevelFolder,
  toggleRuleFolderTree,
  toggleSensorFolderTree,
  toggledataQualityChecksFolderTree
} from '../redux/actions/definition.actions';
import {
  SensorListModel,
  RuleListModel,
  CheckDefinitionListModel
} from '../api';
import { ROUTES } from '../shared/routes';
import { urlencodeEncoder } from '../utils';
import { INestTab } from '../redux/reducers/source.reducer';

const DefinitionContext = React.createContext({} as any);

function DefinitionProvider(props: any) {
  const dispatch = useActionDispatch();
  const [sidebarWidth, setSidebarWidth] = useState(310);
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

  const openSensorFirstLevelTab = (sensor: SensorListModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SENSOR_DETAIL(urlencodeEncoder(sensor.sensor_name) ?? ''),
        value: ROUTES.SENSOR_DETAIL_VALUE(
          urlencodeEncoder(sensor.sensor_name) ?? ''
        ),
        state: {
          full_sensor_name: urlencodeEncoder(sensor.full_sensor_name)
        },
        label: urlencodeEncoder(sensor.sensor_name)
      })
    );
  };

  const openRuleFirstLevelTab = (rule: RuleListModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.RULE_DETAIL(urlencodeEncoder(rule.rule_name) ?? ''),
        value: ROUTES.RULE_DETAIL_VALUE(urlencodeEncoder(rule.rule_name) ?? ''),
        state: {
          full_rule_name: urlencodeEncoder(rule.full_rule_name)
        },
        label: urlencodeEncoder(rule.rule_name)
      })
    );
  };

  const openCheckFirstLevelTab = (check: CheckDefinitionListModel) => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.CHECK_DETAIL(urlencodeEncoder(check.check_name) ?? ''),
        value: ROUTES.CHECK_DETAIL_VALUE(
          urlencodeEncoder(check.check_name) ?? ''
        ),
        state: {
          full_check_name: urlencodeEncoder(check.full_check_name),
          custom: check.custom
        },
        label: urlencodeEncoder(check.check_name)
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
        label: 'All users'
      })
    );
  };

  const openDefaultSchedulesFirstLevelTab = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SCHEDULES_DEFAULT_DETAIL(),
        value: ROUTES.SCHEDULES_DEFAULT_DETAIL_VALUE(),
        label: 'Default schedules'
      })
    );
  };

  const openDefaultWebhooksFirstLevelTab = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.WEBHOOKS_DEFAULT_DETAIL(),
        value: ROUTES.WEBHOOKS_DEFAULT_DETAIL_VALUE(),
        label: 'Default webhooks'
      })
    );
  };

  const openSharedCredentialsFirstLevelTab = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.SHARED_CREDENTIALS_LIST_DETAIL(),
        value: ROUTES.SHARED_CREDENTIALS_LIST_DETAIL_VALUE(),
        label: 'Shared credentials'
      })
    );
  };

  const openDataDictionaryFirstLevelTab = () => {
    dispatch(
      addFirstLevelTab({
        url: ROUTES.DATA_DICTIONARY_LIST_DETAIL(),
        value: ROUTES.DATA_DICTIONARY_LIST_VALUE(),
        label: 'Data dictionaries'
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

  const toggleTree = (tabs: INestTab[]) => {
    const configuration = [
      { category: 'Sensors', isOpen: false },
      { category: 'Rules', isOpen: false },
      { category: 'Data quality checks', isOpen: false },
      { category: 'Default checks configuration', isOpen: false }
    ];
    if (tabs && tabs.length !== 0) {
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
            tabs[i].state.full_check_name as string
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
    } else {
      dispatch(toggleFirstLevelFolder(configuration));
    }
  };

  const nodes = [
    {
      onClick: openAllUsersFirstLevelTab,
      icon: 'userprofile',
      text: 'Manage users'
    },
    {
      onClick: openDefaultSchedulesFirstLevelTab,
      icon: 'clock',
      text: 'Default schedules'
    },
    {
      onClick: openDefaultWebhooksFirstLevelTab,
      icon: 'webhooks',
      text: 'Default webhooks'
    },
    {
      onClick: openSharedCredentialsFirstLevelTab,
      icon: 'definitionsrules',
      text: 'Shared credentials'
    },
    {
      onClick: openDataDictionaryFirstLevelTab,
      icon: 'datadictionary',
      text: 'Data Dictionary'
    }
  ];

  return (
    <DefinitionContext.Provider
      value={{
        sidebarWidth,
        setSidebarWidth,
        toggleTree,
        openCheckDefaultFirstLevelTab,
        openCheckFirstLevelTab,
        openDataDictionaryFirstLevelTab,
        openRuleFirstLevelTab,
        opendataQualityChecksFolderTree,
        openSharedCredentialsFirstLevelTab,

        openSensorFirstLevelTab,
        toggleDataQualityChecksFolder,
        toggleFirstLevelFolder,
        toggleRuleFolder,
        toggleRuleFolderTree,
        toggleSensorFolder,
        toggleSensorFolderTree,
        toggleFolderRecursively,
        nodes
      }}
      {...props}
    />
  );
}

function useDefinition() {
  const context = React.useContext(DefinitionContext);

  if (context === undefined) {
    throw new Error('useDefinition must be used within a DefinitionProvider');
  }
  return context;
}

export { DefinitionProvider, useDefinition };
