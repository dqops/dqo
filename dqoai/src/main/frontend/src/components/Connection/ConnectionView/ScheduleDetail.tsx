import React, { useEffect } from 'react';
import {
  getConnectionSchedule,
  setIsUpdatedSchedule,
  setUpdatedSchedule,
  updateConnectionSchedule
} from '../../../redux/actions/connection.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import ConnectionActionGroup from './ConnectionActionGroup';
import { useParams } from "react-router-dom";
import ScheduleView from "../../ScheduleView";

const ScheduleDetail = () => {
  const { connection }: { connection: string } = useParams();
  const dispatch = useActionDispatch();
  const { updatedSchedule, isUpdatedSchedule } = useSelector(
    (state: IRootState) => state.connection
  );

  const { isUpdating } = useSelector((state: IRootState) => state.connection);

  const handleChange = (obj: any) => {
    dispatch(setIsUpdatedSchedule(true));
    dispatch(
      setUpdatedSchedule({
        ...updatedSchedule,
        ...obj
      })
    );
  };

  useEffect(() => {
    if (!updatedSchedule) {
      dispatch(getConnectionSchedule(connection));
    }
  }, [connection]);

  const onUpdate = async () => {
    if (!updatedSchedule) {
      return;
    }
    await dispatch(updateConnectionSchedule(connection, updatedSchedule));
    await dispatch(getConnectionSchedule(connection));
    dispatch(setIsUpdatedSchedule(false));
  };

  return (
    <div className="p-4">
      <ConnectionActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedSchedule}
        isUpdating={isUpdating}
      />
      <ScheduleView handleChange={handleChange} schedule={updatedSchedule} />
    </div>
  );
};

export default ScheduleDetail;
