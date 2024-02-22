import React, { useEffect, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';
import Input from '../Input';
import {
  ConnectionApiClient,
  JobApiClient,
  TableApiClient
} from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { useTree } from '../../contexts/treeContext';
import { useHistory, useParams } from 'react-router-dom';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../shared/routes';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  FileFormatSpec,
  DuckdbParametersSpecSourceFilesTypeEnum
} from '../../api';
import FileFormatConfiguration from '../FileFormatConfiguration/FileFormatConfiguration';
import { TConfiguration } from '../../components/FileFormatConfiguration/TConfiguration';
import SectionWrapper from '../Dashboard/SectionWrapper';
import FilePath from '../FileFormatConfiguration/FilePath';

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
  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecSourceFilesTypeEnum>(
      DuckdbParametersSpecSourceFilesTypeEnum.csv
    );
  const [configuration, setConfiguration] = useState<TConfiguration>({});
  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
  };
  const cleanConfiguration = () => {
    setConfiguration({});
  };

  const { connection, schema }: { connection: string; schema: string } =
    useParams();
  const dispatch = useActionDispatch();
  const history = useHistory();

  const args = node ? node.id.toString().split('.') : [];

  const handleSubmit = async () => {
    try {
      setLoading(true);
      if (node) {
        await TableApiClient.createTable(args[0], args[1], name, {
          file_format:
            connectionModel.provider_type ===
            ConnectionSpecProviderTypeEnum.duckdb
              ? {
                  [fileFormatType as keyof FileFormatSpec]: configuration,
                  file_paths: paths.slice(0, -1)
                }
              : undefined
        }).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: args[0],
            schemaName: args[1],
            tableNames: [name]
          })
        );
        refreshNode(node);
      } else {
        await TableApiClient.createTable(connection, schema, name, {
          file_format:
            connectionModel.provider_type ===
            ConnectionSpecProviderTypeEnum.duckdb
              ? {
                  [fileFormatType as keyof FileFormatSpec]: configuration,
                  file_paths: paths.slice(0, -1)
                }
              : undefined
        }).then(() =>
          JobApiClient.importTables(undefined, false, undefined, {
            connectionName: connection,
            schemaName: schema,
            tableNames: [name]
          })
        );
      }
      dispatch(
        addFirstLevelTab(CheckTypes.SOURCES, {
          url: ROUTES.TABLE_LEVEL_PAGE(
            CheckTypes.SOURCES,
            connection,
            schema,
            name,
            'detail'
          ),
          value: ROUTES.TABLE_LEVEL_VALUE(
            CheckTypes.SOURCES,
            connection,
            schema,
            name
          ),
          state: {},
          label: name
        })
      );
      history.push(
        ROUTES.TABLE_LEVEL_PAGE(
          CheckTypes.SOURCES,
          connection,
          schema,
          name,
          'detail'
        )
      );
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

  const onAddPath = () => setPaths((prev) => [...prev, '']);
  const onChangePath = (value: string) => {
    const copiedPaths = [...paths];
    copiedPaths[paths.length - 1] = value;
    setPaths(copiedPaths);
  };
  const onDeletePath = (index: number) =>
    setPaths((prev) => prev.filter((x, i) => i !== index));

  const onChangeFile = (val: DuckdbParametersSpecSourceFilesTypeEnum) =>
    setFileFormatType(val);

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-4 pb-2 px-8">
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
        {connectionModel.provider_type ===
          ConnectionSpecProviderTypeEnum.duckdb && (
          <FileFormatConfiguration
            fileFormatType={fileFormatType}
            onChangeFile={onChangeFile}
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
            cleanConfiguration={cleanConfiguration}
            freezeFileType={true}
          >
            <FilePath
              paths={paths}
              onAddPath={onAddPath}
              onChangePath={onChangePath}
              onDeletePath={onDeletePath}
            />
          </FileFormatConfiguration>
        )}
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
