import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
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
import { CheckTypes } from '../../../shared/routes';
import LabelsView from '../LabelsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useDecodedParams } from '../../../utils';

const ConnectionLabelsView = () => {
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useDecodedParams();
  const { isUpdating, labels, isUpdatedLabels, connectionBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
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
  };

  const handleChange = (value: string[]) => {
    dispatch(setLabels(checkTypes, firstLevelActiveTab, value));
    dispatch(setIsUpdatedLabels(checkTypes, firstLevelActiveTab, true));
  };

  return (
    <div className="px-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedLabels}
        isUpdating={isUpdating}
      />
      <LabelsView labels={labels || []} onChange={handleChange} />
    </div>
  );
};

export default ConnectionLabelsView;
