import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../../components/Button';
import Input from '../../components/Input';
import {
  DefaultColumnCheckPatternsApiClient,
  DefaultTableCheckPatternsApiClient
} from '../../services/apiClient';
interface CopyCheckPatternDialogProps {
  type: 'column' | 'table';
  sourceTableName: string;
  open: boolean;
  setOpen: (v: boolean) => void;
}

export default function CopyCheckPatternDialog({
  type,
  sourceTableName,
  open,
  setOpen
}: CopyCheckPatternDialogProps) {
  const [tableName, setTableName] = useState('');
  const handleSubmit = () => {
    if (type === 'column') {
      DefaultColumnCheckPatternsApiClient.copyFromDefaultColumnChecksPattern(
        tableName,
        sourceTableName
      );
    }
    if (type === 'table') {
      DefaultTableCheckPatternsApiClient.copyFromDefaultTableChecksPattern(
        tableName,
        sourceTableName
      );
    }
    setOpen(false);
  };
  const onClose = () => {
    setOpen(false);
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-4 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal flex pb-4 items-center justify-center">
          Copy {sourceTableName} to new {type} pattern
        </div>
        <Input
          label={`${type === 'column' ? 'Column' : 'Table'} pattern name`}
          placeholder={`Copy of ${sourceTableName}`}
          className="w-full"
          value={tableName}
          onChange={(e) => setTableName(e.target.value)}
        />
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
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
  );
}
