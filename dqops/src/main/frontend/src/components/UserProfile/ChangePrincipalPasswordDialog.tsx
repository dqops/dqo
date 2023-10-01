import React, { useEffect, useState } from 'react';
import { Dialog, DialogBody, DialogFooter, Input } from '@material-tailwind/react'; 
import Button from '../../components/Button';

interface AddColumnDialogProps {
  open: boolean;
  onClose: () => void;
  handleSubmit: (password: string) => Promise<void>
}

const ChangePrincipalPasswordDialog = ({ open, onClose, handleSubmit }: AddColumnDialogProps) => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
const [message, setMessage] = useState<string>()

    useEffect(() => {
        if(password.length!==0 && password.length<=8){
            setMessage("Password must be at least 8 characters long")
        }
        else if(password.length!==0 && confirmPassword.length!==0 && confirmPassword !== password){
            setMessage("The passwords do not match")
        }
        else if(password.length!==0 && !password.match(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)/)){
            setMessage("Password must contain small letters, capital letters and digits")
        }else{
            setMessage(undefined)
        }
    }, [password, confirmPassword])


  
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
              type='password'
            />
          </div>
          <div className="mb-6">
            <Input
              label="Confirm Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              type='password'
            />
          </div>
          {message ?  <div className='text-red-500'>{message}</div> : null}
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
          disabled={!!(message ||  confirmPassword.length ===0 || password.length ===0)}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default ChangePrincipalPasswordDialog;
