import React, { useEffect, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Input from '../../components/Input';
import Button from '../../components/Button';
import { IncidentModel } from "../../api";

interface AddIssueUrlDialogProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (value: string) => void;
  incident?: IncidentModel;
}

const AddIssueUrlDialog = ({
  open,
  onClose,
  onSubmit,
  incident
}: AddIssueUrlDialogProps) => {
  const [url, setUrl] = useState("");

  const handleSubmit = async () => {
    onSubmit(url);
    onClose();
  };

  useEffect(() => {
    setUrl(incident?.issueUrl || "");
  }, [incident]);

  return (
    <div>
      <Dialog open={open} handler={onClose} className="min-w-150 max-w-150">
        <DialogBody className="pt-10 pb-2 px-8">
          <div>
            <h1 className="text-center mb-4 text-gray-700 text-2xl">Link an issue URL to this incident</h1>
            <div className="flex gap-x-3 items-center justify-center mt-10 mb-4">
              <div className="text-sm text-gray-700 text-right">Issue URL</div>
              <Input
                className="!w-80"
                value={url}
                onChange={(e) => setUrl(e.target.value)}
              />
            </div>
          </div>
        </DialogBody>
        <DialogFooter className="flex justify-center space-x-6 px-8 pb-8">
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
