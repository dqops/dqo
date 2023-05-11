import React, { useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Input from '../../components/Input';
import Button from '../../components/Button';

interface AddIssueUrlDialogProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (value: string) => void;
}

const AddIssueUrlDialog = ({
  open,
  onClose,
  onSubmit,
}: AddIssueUrlDialogProps) => {
  const [name, setName] = useState("");

  const handleSubmit = async () => {
    onSubmit(name);
    onClose();
  };

  return (
    <div>
      <Dialog open={open} handler={onClose} className="min-w-150 max-w-150">
        <DialogBody className="pt-6 pb-2 px-8">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">Link an issue URL to this error</h1>
          <div>
            <Input
              label="Issue URL"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </DialogBody>
        <DialogFooter className="justify-end space-x-6 px-8 pb-8">
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
            label="Save"
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default AddIssueUrlDialog;
