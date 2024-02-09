import React, { useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';
import Input from '../Input';
import { JobApiClient, TableApiClient } from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { useTree } from '../../contexts/treeContext';
import { useParams } from 'react-router-dom';

interface AddTableDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddTableDialog = ({ open, onClose, node }: AddTableDialogProps) => {
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);
  const { refreshNode } = useTree();
  const { connection, schema }: { connection: string; schema: string } =
    useParams();

  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
        const args = node.id.toString().split('.');
        await TableApiClient.createTable(args[0], args[1], name).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: args[0],
            schemaName: args[1],
            tableNames: [name]
          })
        );
        refreshNode(node);
      } else {
        await TableApiClient.createTable(connection, schema, name).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: connection,
            schemaName: schema,
            tableNames: [name]
          })
        );
      }
      onClose();
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-6 pb-2 px-8">
        <div className="flex flex-col">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">Add Table</h1>
          <div>
            <Input
              label="Table Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
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
          onClick={handleSubmit}
          label="Save"
          loading={loading}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default AddTableDialog;
