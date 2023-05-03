import React, { ChangeEvent, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';
import Input from "../Input";
import { SensorBasicFolderModel } from "../../api";
import { useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { updateSensorFolderTree } from "../../redux/actions/sensor.actions";
import { useActionDispatch } from "../../hooks/useActionDispatch";

interface AddFolderDialogProps {
  open: boolean;
  onClose: () => void;
  path?: string[];
  folder?: SensorBasicFolderModel;
}

const AddFolderDialog = ({
  open,
  onClose,
  path,
  folder
}: AddFolderDialogProps) => {
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const { sensorFolderTree } = useSelector((state: IRootState) => state.sensor);
  const dispatch = useActionDispatch();

  const handleSubmit = async () => {
    if (!name) {
      setError("Name is required.");
      return;
    }
    if (name.includes("/") || name.includes("\\")) {
      setError("Name has invalid character.");
      return;
    }

    if (Object.keys(folder?.folders || {}).includes(name)) {
      setError('Name is already existed.');
      return;
    }

    if (!sensorFolderTree) {
      return;
    }

    const newSensorFolderTree: SensorBasicFolderModel = structuredClone(sensorFolderTree);
    let data = newSensorFolderTree;

    for(const key of path || []) {
      data = data.folders?.[key] as SensorBasicFolderModel;
    }

    data.folders = {
      ...data.folders,
      [name]: {}
    }

    dispatch(updateSensorFolderTree(newSensorFolderTree));
    setName('');
    onClose();
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
    setError('');
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8">
          <h1 className="mb-2">New folder name:</h1>
          
          <div>
            <Input
              value={name}
              onChange={onChangeName}
              error={!!error}
              helperText={error}
            />
          </div>
        </DialogBody>
        <DialogFooter>
          <div className="flex justify-end space-x-6 px-4 mb-4">
            <Button
              color="primary"
              variant="outlined"
              className="px-8"
              onClick={onClose}
              label="Cancel"
            />
            <Button
              color="primary"
              className="px-8"
              onClick={handleSubmit}
              label="Create folder"
            />
          </div>
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default AddFolderDialog;
