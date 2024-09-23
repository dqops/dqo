import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../../components/Button';
import Input from '../../components/Input';
import Loader from '../../components/Loader';
import { DataDomainApiClient } from '../../services/apiClient';

export default function DataDomainCreateDialog({
  open,
  onClose
}: {
  open: boolean;
  onClose: () => void;
}) {
  const [dataDomainName, setDataDomainName] = React.useState<string>('');
  const [error, setError] = useState(false);
  const [showSpinner, setShowSpinner] = useState(false);

  const handleSubmit = () => {
    setShowSpinner(true);
    DataDomainApiClient.createDataDomain(dataDomainName)
      .then(() => {
        onClose();
      })
      .catch(() => {
        setError(true);
      })
      .finally(() => {
        setShowSpinner(false);
      });
  };

  const handleClose = () => {
    onClose();
    setDataDomainName('');
    setError(false);
    setShowSpinner(false);
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-4 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal break-words">
          Create a new data domain
        </div>
        <div className="mt-4 text-sm">
          <Input
            type="text"
            className="w-full border border-gray-300 rounded-md px-4 py-2"
            placeholder="Enter data domain name"
            value={dataDomainName}
            onChange={(e) => setDataDomainName(e.target.value)}
            label="Data domain name"
          />
        </div>
        {error && (
          <div className="text-red-500 mt-4 text-sm">
            Invalid data domain name{' '}
          </div>
        )}
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={handleClose}
          label={'Cancel'}
        />

        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label={'Save'}
        />
        {showSpinner && (
          <div className="w-6 h-6 mb-2">
            <Loader isFull={false} className="w-8 h-8 fill-green-700" />
          </div>
        )}
      </DialogFooter>
    </Dialog>
  );
}
