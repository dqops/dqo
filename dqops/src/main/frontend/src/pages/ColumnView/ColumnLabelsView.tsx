import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { LabelModel } from '../../api';
import LabelsView from '../../components/Connection/LabelsView';
import LabelsSectionWrapper from '../../components/LabelsSectionWrapper/LabelsSectionWrapper';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import {
  getColumnLabels,
  setUpdatedLabels,
  updateColumnLabels
} from '../../redux/actions/column.actions';
import { setIsUpdatedLabels } from '../../redux/actions/connection.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { LabelsApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ColumnActionGroup from './ColumnActionGroup';

interface IColumnLabelsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const ColumnLabelsView = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnLabelsViewProps) => {
  type TLabel = LabelModel & { clicked: boolean };
  const [globalLabels, setGlobalLabels] = useState<TLabel[]>([]);
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { labels, isUpdating, isUpdatedLabels } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getColumnLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    columnName,
    tableName
  ]);

  const onUpdate = async () => {
    await dispatch(
      updateColumnLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        labels
      )
    );
    await dispatch(
      getColumnLabels(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        false
      )
    );
    getGlobalLabels();
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, false));
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

  const getGlobalLabels = async () => {
    await LabelsApiClient.getAllLabelsForColumns().then((res) => {
      const array: TLabel[] = res.data.map((item) => {
        const isClicked = labels?.includes(item.label);
        return { ...item, clicked: isClicked };
      });
      setGlobalLabels(array);
    });
  };
  useEffect(() => {
    if (!labels || labels?.length === 0 || globalLabels.length === 0) {
      getGlobalLabels();
    }
  }, [
    checkTypes,
    connectionName,
    schemaName,
    tableName,
    columnName,
    labels,
    globalLabels
  ]);

  return (
    <div className="px-4">
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <div className="flex">
        <div className="mt-4 mx-2 max-w-150 min-w-40">
          <LabelsSectionWrapper
            labels={globalLabels}
            onChangeLabels={onChangeLabels}
            className="w-full py-4"
          />
        </div>
        <LabelsView labels={labels || []} onChange={handleChange} />
      </div>
    </div>
  );
};

export default ColumnLabelsView;
