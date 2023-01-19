import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import {
  getConnectionLabels,
  setIsUpdatedLabels,
  setLabels,
  updateConnectionLabels
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import LabelsView from '../LabelsView';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";

const ConnectionLabelsView = () => {
  const { connection }: { connection: string } = useParams();
  const { isUpdating, labels, isUpdatedLabels, connectionBasic } = useSelector(
    (state: IRootState) => state.connection
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (!labels || connectionBasic?.connection_name !== connection) {
      dispatch(getConnectionLabels(connection));
    }
  }, [connection]);

  const onUpdate = async () => {
    await dispatch(updateConnectionLabels(connection, labels || []));
    await dispatch(getConnectionLabels(connection));
  };

  const handleChange = (value: string[]) => {
    dispatch(setLabels(value));
    dispatch(setIsUpdatedLabels(true));
  };

  return (
    <div>
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
