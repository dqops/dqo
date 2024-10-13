import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import { DataCatalogSynchronizationApiClient } from '../../services/apiClient';
import Button from '../Button';
import Input from '../Input';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  nodeId: string;
}

const PushToDataCatalogDialog = ({
  open,
  onClose,
  nodeId
}: ConfirmDialogProps) => {
  const [connection, setConnection] = useState<string>(
    nodeId.split('.')[0] ?? ''
  );
  const [schema, setSchema] = useState<string>(nodeId.split('.')[1] ?? '');
  const [table, setTable] = useState<string>(nodeId.split('.')[2] ?? '');

  const handleSubmit = async () => {
    const addPrefix = (key: string) => {
      if (key.includes('*') || key.length === 0) {
        return key;
      } else {
        return `*${key}*`;
      }
    };
    DataCatalogSynchronizationApiClient.pushDataQualityStatusToDataCatalog(
      addPrefix(connection),
      addPrefix(schema),
      addPrefix(table)
    );
    onClose();
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal break-words">
          Push to data catalog
        </div>
        <div className="flex flex-col gap-y-4">
          <Input
            label="Connection"
            value={connection}
            onChange={(e) => setConnection(e.target.value)}
          />
          <Input
            label="Schema"
            value={schema}
            onChange={(e) => setSchema(e.target.value)}
          />
          <Input
            label="Table"
            value={table}
            onChange={(e) => setTable(e.target.value)}
          />
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onClose}
          label={'Cancel'}
        />
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label={'Push'}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default PushToDataCatalogDialog;
