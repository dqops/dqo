import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { LabelModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setIsUpdatedLabels } from '../../../redux/actions/connection.actions';
import {
  getTableLabels,
  setUpdatedLabels,
  updateTableLabels
} from '../../../redux/actions/table.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { LabelsApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import LabelsSectionWrapper from '../../LabelsSectionWrapper/LabelsSectionWrapper';
import LabelsView from '../LabelsView';
import ActionGroup from './TableActionGroup';
type TLabel = LabelModel & { clicked: boolean };
const TableLabelsView = () => {
  const {
    checkTypes,
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const { labels, isUpdating, isUpdatedLabels } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const [globalLabels, setGlobalLabels] = useState<TLabel[]>([]);
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getTableLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName
      )
    );
  }, [checkTypes, firstLevelActiveTab, connectionName, schemaName, tableName]);

  const onUpdate = async () => {
    await dispatch(
      updateTableLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        Array.from(labels).filter((x) => String(x).length !== 0) as any
      )
    );
    await dispatch(
      getTableLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        false
      )
    );
  };

  const handleChange = (value: string[]) => {
    dispatch(setUpdatedLabels(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, true));
  };
  const onChangeLabels = (index: number) => {
    const arr = [...globalLabels];
    arr[index] = { ...arr[index], clicked: !arr[index].clicked };

    if (labels.includes(arr[index].label)) {
      const filteredArr = labels.filter((x: string) => x !== arr[index].label);
      handleChange(filteredArr);
    } else {
      handleChange([...labels, arr[index].label]);
    }
    setGlobalLabels(arr);
  };
  useEffect(() => {
    const getGlobalLabels = async () => {
      await LabelsApiClient.getAllLabelsForTables().then((res) => {
        const array: TLabel[] = res.data.map((item) => {
          const isClicked = labels?.includes(item.label);
          return { ...item, clicked: isClicked };
        });
        setGlobalLabels(array);
      });
    };
    if (!labels || labels?.length === 0 || globalLabels.length === 0) {
      getGlobalLabels();
    }
  }, [checkTypes, connectionName, schemaName, tableName, labels, globalLabels]);

  return (
    <div className="px-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <div className="flex">
        <div className="mt-4 mx-2 w-1/2">
          <LabelsSectionWrapper
            labels={globalLabels}
            onChangeLabels={onChangeLabels}
            className="w-full py-4"
          />
        </div>
        <LabelsView labels={labels} onChange={handleChange} />
      </div>
    </div>
  );
};

export default TableLabelsView;
