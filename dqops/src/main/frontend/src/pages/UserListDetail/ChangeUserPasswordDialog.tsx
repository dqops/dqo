import React, { useEffect, useState } from 'react';
import { Dialog, DialogBody, DialogFooter, Input } from '@material-tailwind/react'; 
import Button from '../../components/Button';

interface AddColumnDialogProps {
  open: boolean;
  onClose: () => void;
  handleSubmit: (password: string) => Promise<void>
}
const generatePassword = (length : number) => {
  const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  return Array.from({ length }, () => charset[Math.floor(Math.random() * charset.length)]).join('');
}

const ChangeUserPasswordDialog = ({ open, onClose, handleSubmit }: AddColumnDialogProps) => {
  const [password, setPassword] = useState('');

  useEffect(()=> {
    setPassword(generatePassword(8))
  },[open])
  
  return (
    <Dialog open={open} handler={onClose} size="xs">
      <DialogBody className="pt-6 pb-2 px-8">
        <div className="flex flex-col">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">
            Change Password
          </h1>
          <div className="mb-6">
            <Input
              label="New Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
        </div>
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
          onClick={() => handleSubmit(password)}
          label="Save"
        />
      </DialogFooter>
    </Dialog>
  );
};

export default ChangeUserPasswordDialog;
