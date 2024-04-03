import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useTree } from '../../contexts/treeContext';
import { IRootState } from '../../redux/reducers';
import { ColumnApiClient } from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { urlencodeEncoder, useDecodedParams } from '../../utils';
import Button from '../Button';
import Input from '../Input';
import TextArea from '../TextArea';

interface AddColumnDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddColumnDialog = ({ open, onClose, node }: AddColumnDialogProps) => {
  const [name, setName] = useState('');
  const [sqlExpression, setSqlExpression] = useState('');
  const [loading, setLoading] = useState(false);
  const { refreshNode } = useTree();
  const {
    connection,
    table,
    schema
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const onCloseCleanPrevState = () => {
    onClose();
    setName('');
    setSqlExpression('');
  };

  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
        const args = node.id.toString().split('.');
        await ColumnApiClient.createColumn(urlencodeEncoder(args[0]), urlencodeEncoder(args[1]), urlencodeEncoder(args[2]), name, {
          sql_expression: sqlExpression
        });
        refreshNode(node);
      } else {
        await ColumnApiClient.createColumn(connection, schema, table, name, {
          sql_expression: sqlExpression
        });
      }
      onCloseCleanPrevState();
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} handler={onCloseCleanPrevState} size="xs">
      <DialogBody className="pt-6 pb-2 px-8">
        <div className="flex flex-col">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">
            Add Column
          </h1>
          <div className="mb-6">
            <Input
              label="Column Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div>
            <TextArea
              label="SQL expression for a calculated column"
              value={sqlExpression}
              onChange={(e) => setSqlExpression(e.target.value)}
              className='min-h-25'
            />
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onCloseCleanPrevState}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label="Save"
          loading={loading}
          disabled={userProfile.can_manage_data_sources !== true}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default AddColumnDialog;
