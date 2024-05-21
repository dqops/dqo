import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { LabelModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getConnectionLabels,
  setIsUpdatedLabels,
  setLabels,
  updateConnectionLabels
} from '../../../redux/actions/connection.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { LabelsApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import LabelsSectionWrapper from '../../LabelsSectionWrapper/LabelsSectionWrapper';
import LabelsView from '../LabelsView';
import ConnectionActionGroup from './ConnectionActionGroup';
type TLabel = LabelModel & { clicked: boolean };
const ConnectionLabelsView = () => {
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useDecodedParams();
  const { isUpdating, labels, isUpdatedLabels, connectionBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const [globalLabels, setGlobalLabels] = useState<TLabel[]>([]);

  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    if (!labels) {
      dispatch(
        getConnectionLabels(checkTypes, firstLevelActiveTab, connection)
      );
    }
  }, [connection, checkTypes, firstLevelActiveTab]);

  const onUpdate = async () => {
    await dispatch(
      updateConnectionLabels(
        checkTypes,
        firstLevelActiveTab,
        connection,
        labels || []
      )
    );
    await dispatch(
      getConnectionLabels(checkTypes, firstLevelActiveTab, connection, false)
    );
    getGlobalLabels();
  };

  const handleChange = (value: string[]) => {
    dispatch(setLabels(checkTypes, firstLevelActiveTab, value));
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
    await LabelsApiClient.getAllLabelsForConnections().then((res) => {
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
  }, [checkTypes, connection, labels, globalLabels]);

  return (
    <div className="px-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <div className="flex">
        <div className="mt-4 mx-2 max-w-150  min-w-40">
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

export default ConnectionLabelsView;
