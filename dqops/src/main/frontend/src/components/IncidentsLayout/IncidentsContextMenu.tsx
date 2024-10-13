import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { DeleteStoredDataQueueJobParameters } from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { IRootState } from '../../redux/reducers';
import { JobApiClient } from '../../services/apiClient';
import DeleteStoredDataExtendedPopUp from '../CustomTree/DeleteStoredDataExtendedPopUp';
import SvgIcon from '../SvgIcon';
import { getConnections } from '../../redux/actions/incidents.actions';

export default function IncidentsContextMenu({
  connection
}: {
  connection: string;
}) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const dispatch = useActionDispatch();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const [open, setOpen] = useState(false);
  const openPopover = (e: MouseEvent) => {
    setOpen(!open);
    e.stopPropagation();
  };
  const deleteData = (params: DeleteStoredDataQueueJobParameters) => {
    JobApiClient.deleteStoredData(undefined, undefined, undefined, {
      ...params
    }).then(() => {
      dispatch(getConnections());
    });
    setOpen(false);
  };

  return (
    <Popover placement="bottom-end" open={open} handler={setOpen}>
      <PopoverHandler onClick={openPopover}>
        <div className="text-gray-700 !absolute right-0 w-4 h-4 rounded-full flex items-center justify-center bg-white">
          <SvgIcon name="options" className="w-4 h-4 text-gray-500 mt-1.5" />
        </div>
      </PopoverHandler>
      <PopoverContent
        className="z-50 min-w-50 max-w-50 border-gray-500 p-2"
        onClick={(e) => e.stopPropagation()}
      >
        <div
          className="text-gray-900 cursor-pointer hover:bg-gray-100 px-4 py-2 rounded"
          onClick={() => {
            userProfile.can_delete_data
              ? setDeleteDataDialogOpened(true)
              : undefined;
          }}
        >
          Delete data
        </div>
      </PopoverContent>
      {deleteDataDialogOpened && (
        <DeleteStoredDataExtendedPopUp
          open={deleteDataDialogOpened}
          onClose={() => setDeleteDataDialogOpened(false)}
          onDelete={(params: DeleteStoredDataQueueJobParameters) => {
            setDeleteDataDialogOpened(false);
            deleteData(params);
          }}
          nodeId={connection}
          isIncident
        />
      )}
    </Popover>
  );
}
