import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  getConnectionLabels,
  setIsUpdatedLabels,
  setLabels,
  updateConnectionLabels
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import LabelsView from '../LabelsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';

const ConnectionLabelsView = () => {
  const {
    connection,
    checkTypes
  }: { connection: string; checkTypes: CheckTypes } = useParams();
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
