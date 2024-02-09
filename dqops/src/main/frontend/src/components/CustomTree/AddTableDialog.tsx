import React, { useEffect, useState } from 'react';
import {
  Dialog,
  DialogBody,
  DialogFooter,
  IconButton
} from '@material-tailwind/react';
import Button from '../Button';
import Input from '../Input';
import {
  ConnectionApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { useTree } from '../../contexts/treeContext';
import { useParams } from 'react-router-dom';
import { ConnectionModel, ConnectionSpecProviderTypeEnum } from '../../api';
import SvgIcon from '../SvgIcon';

interface AddTableDialogProps {
  open: boolean;
  onClose: () => void;
  node?: CustomTreeNode;
}

const AddTableDialog = ({ open, onClose, node }: AddTableDialogProps) => {
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);
  const [connectionModel, setConnectionModel] = useState<ConnectionModel>({});
  const { refreshNode } = useTree();
  const [paths, setPaths] = useState<Array<string>>(['']);
  const { connection, schema }: { connection: string; schema: string } =
    useParams();

  const args = node ? node.id.toString().split('.') : [];
  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
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

  useEffect(() => {
    const getConnectionBasic = async () => {
      await ConnectionApiClient.getConnectionBasic(args[0]).then((res) =>
        setConnectionModel(res.data)
      );
    };
    if (node) {
      getConnectionBasic();
    }
  }, [open]);

  const onChangePath = (value: string) => {
    const copiedPaths = [...paths];
    copiedPaths[paths.length - 1] = value;
    setPaths(copiedPaths);
  };

  const onAdd = () => setPaths((prev) => [...prev, '']);

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
        {/* {connectionModel.provider_type ===
        ConnectionSpecProviderTypeEnum.duckdb ? ( */}
        <div className="text-sm">
          {paths.slice(0, paths.length - 1).map((x, index) => (
            <div key={index} className="text-black py-1">
              {x}
            </div>
          ))}
          <div className="flex items-center w-full">
            <div className="pr-4 min-w-40 py-2 w-11/12">
              <Input
                className="focus:!ring-0 focus:!border"
                value={paths.length ? paths[paths.length - 1] : ''}
                onChange={(e) => onChangePath(e.target.value)}
              />
            </div>
            <div className="px-8 max-w-34 min-w-34 py-2">
              <div className="flex justify-center">
                <IconButton size="sm" className="bg-teal-500" onClick={onAdd}>
                  <SvgIcon name="add" className="w-4" />
                </IconButton>
              </div>
            </div>
          </div>
        </div>
        {/* // ) : (
        //   <></>
        // )} */}
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
