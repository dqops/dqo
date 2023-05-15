import React, { useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';
import Input from '../Input';
import { ColumnApiClient } from "../../services/apiClient";
import { CustomTreeNode } from "../../shared/interfaces";
import { useTree } from "../../contexts/treeContext";
import { useParams } from "react-router-dom";

interface AddColumnDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddColumnDialog = ({
  open,
  onClose,
  node
}: AddColumnDialogProps) => {
  const [name, setName] = useState("");
  const [sqlExpression, setSqlExpression] = useState("");
  const [loading, setLoading] = useState(false);
  const { refreshNode } = useTree();
  const { connection, table, schema }: { connection: string, schema: string, table: string } = useParams()

  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
        const args = node.id.toString().split('.');
        await ColumnApiClient.createColumn(args[0], args[1], args[2], name);
        refreshNode(node);
      } else {
        await ColumnApiClient.createColumn(connection, schema, table, name);
      }
      onClose();
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-6 pb-2 px-8">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">Add Column</h1>
          <div>
            <Input
              label="Column Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div>
            <Input
              label="SQL Expression for a calculated column"
              value={sqlExpression}
              onChange={(e) => setSqlExpression(e.target.value)}
            />
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
    </div>
  );
};

export default AddColumnDialog;
