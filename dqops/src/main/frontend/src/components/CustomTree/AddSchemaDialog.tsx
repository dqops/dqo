import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import { useTree } from "../../contexts/treeContext";
import { CustomTreeNode } from "../../shared/interfaces";
import { useDecodedParams } from '../../utils';
import { findTreeNode } from "../../utils/tree";
import Button from '../Button';
import Input from '../Input';

interface AddSchemaDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddSchemaDialog = ({
  open,
  onClose,
  node
}: AddSchemaDialogProps) => {
  const [name, setName] = useState("");
  const { addSchema, treeData } = useTree();
  const { connection }: { connection: string } = useDecodedParams()

  const handleSubmit = async () => {
    if (node) {
      addSchema(node, name);
    } else {
      const connectionNode = findTreeNode(treeData, connection);
      if (connectionNode) {
        addSchema(connectionNode, name);
      }
    }
    onClose();
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-6 pb-2 px-8">
        <div className="flex flex-col">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">Add Schema</h1>
          <div>
            <Input
              label="Schema Name"
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
        />
      </DialogFooter>
    </Dialog>
  );
};

export default AddSchemaDialog;
